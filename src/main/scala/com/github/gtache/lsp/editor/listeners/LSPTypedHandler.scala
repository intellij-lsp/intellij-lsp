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
package com.github.gtache.lsp.editor.listeners

import com.github.gtache.lsp.editor.EditorEventManager
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate.Result
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

/**
  * This class notifies an EditorEventManager that a character has been typed in the editor
  */
class LSPTypedHandler extends TypedHandlerDelegate {
  override def charTyped(c: Char, project: Project, editor: Editor, file: PsiFile): Result = {
    EditorEventManager.forEditor(editor).foreach(m => m.characterTyped(c))
    Result.CONTINUE
  }

}
