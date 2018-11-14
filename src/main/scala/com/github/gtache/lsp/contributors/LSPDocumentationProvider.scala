/**
 *     Copyright 2017-2018 Guillaume Tâche
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
package com.github.gtache.lsp.contributors

import com.github.gtache.lsp.contributors.psi.LSPPsiElement
import com.github.gtache.lsp.editor.EditorEventManager
import com.github.gtache.lsp.utils.FileUtils
import com.intellij.lang.documentation.DocumentationProvider
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.{PsiElement, PsiFile, PsiManager}

/**
  * A documentation provider for LSP (is called when CTRL is pushed while staying on a token)
  */
class LSPDocumentationProvider extends DocumentationProvider {
  private val LOG: Logger = Logger.getInstance(classOf[LSPDocumentationProvider])


  override def getUrlFor(element: PsiElement, originalElement: PsiElement): java.util.List[String] = {
    null
  }

  override def getDocumentationElementForLookupItem(psiManager: PsiManager, obj: scala.Any, element: PsiElement): PsiElement = {
    null
  }

  override def getDocumentationElementForLink(psiManager: PsiManager, link: String, context: PsiElement): PsiElement = {
    null
  }

  override def generateDoc(element: PsiElement, originalElement: PsiElement): String = {
    getQuickNavigateInfo(element, originalElement)
  }

  override def getQuickNavigateInfo(element: PsiElement, originalElement: PsiElement): String = {
    element match {
      case l: LSPPsiElement =>
        EditorEventManager.forUri(FileUtils.VFSToURI(l.getContainingFile.getVirtualFile)).fold("")(m => m.requestDoc(m.editor, l.getTextOffset))
      case p: PsiFile =>
        val editor = FileUtils.editorFromPsiFile(p)
        EditorEventManager.forEditor(editor).fold("")(m => m.requestDoc(editor, editor.getCaretModel.getCurrentCaret.getOffset))
      case _ => ""
    }
  }
}