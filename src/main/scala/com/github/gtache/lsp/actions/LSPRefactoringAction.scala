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
package com.github.gtache.lsp.actions

import com.github.gtache.lsp.editor.EditorEventManager
import com.intellij.openapi.actionSystem.{AnActionEvent, CommonDataKeys}
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.{Messages, NonEmptyInputValidator}

/**
  * Action called when the user presses SHIFT+ALT+F6 to rename a symbol
  */
class LSPRefactoringAction extends DumbAwareAction {
  private val LOG: Logger = Logger.getInstance(classOf[LSPRefactoringAction])

  override def actionPerformed(e: AnActionEvent): Unit = {
    val editor = e.getData(CommonDataKeys.EDITOR)
    EditorEventManager.forEditor(editor) match {
      case Some(manager) =>
        val renameTo = Messages.showInputDialog(e.getProject, "Enter new name: ", "Rename", Messages.getQuestionIcon, "", new NonEmptyInputValidator())
        if (renameTo != null && renameTo != "") manager.rename(renameTo)
      case None =>
    }
  }
}
