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
package com.github.gtache.lsp.contributors

import com.github.gtache.lsp.contributors.psi.LSPPsiElement
import com.github.gtache.lsp.editor.EditorEventManager
import com.intellij.codeInspection.{LocalQuickFix, ProblemDescriptor}
import com.intellij.openapi.project.Project
import org.eclipse.lsp4j.Command

/**
  * The Quickfix for LSP
  *
  * @param uri     The file in which the commands will be applied
  * @param command The command to run
  */
class LSPQuickFix(uri: String, command: Command) extends LocalQuickFix {
  override def applyFix(project: Project, descriptor: ProblemDescriptor): Unit = {
    descriptor.getPsiElement match {
      case element: LSPPsiElement =>
        EditorEventManager.forUri(uri).foreach(m => m.executeCommands(Array(command)))
      case _ =>
    }
  }

  override def getFamilyName: String = "LSP Fixes"

  override def getName: String = {
    command.getTitle
  }

}
