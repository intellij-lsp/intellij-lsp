/**
 *     Copyright 2017 Guillaume Tâche
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package com.github.gtache.lsp.contributors.rename

import java.util

import com.github.gtache.lsp.editor.EditorEventManager
import com.intellij.openapi.editor.Editor
import com.intellij.psi.search.SearchScope
import com.intellij.psi.{PsiElement, PsiNamedElement, PsiReference}
import com.intellij.refactoring.rename.inplace.MemberInplaceRenamer

class LSPInplaceRenamer(elementToRename: PsiNamedElement, substituted: PsiElement, editor: Editor)(initialName: String = elementToRename.getName, oldName: String = elementToRename.getName) extends MemberInplaceRenamer(elementToRename, substituted, editor, initialName, oldName) {

  override def collectRefs(referencesSearchScope: SearchScope): util.Collection[PsiReference] = {
    import scala.collection.JavaConverters._
    EditorEventManager.forEditor(editor) match {
      case Some(m) =>
        val (references, toClose) = m.references(editor.getCaretModel.getCurrentCaret.getOffset, getOriginalElement = true)
        LSPRenameProcessor.addEditors(toClose)
        references.map(f => f.getReference).toList.asJava
      case None => List().asJava
    }
  }
}
