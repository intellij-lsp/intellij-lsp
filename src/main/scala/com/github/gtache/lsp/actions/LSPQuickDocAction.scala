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
package com.github.gtache.lsp.actions

import com.github.gtache.lsp.editor.EditorEventManager
import com.intellij.codeInsight.documentation.actions.ShowQuickDocInfoAction
import com.intellij.lang.LanguageDocumentation
import com.intellij.openapi.actionSystem.{AnActionEvent, CommonDataKeys}
import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileTypes.PlainTextLanguage
import com.intellij.openapi.project.DumbAware
import com.intellij.psi.PsiManager

/**
  * Action overriding QuickDoc (CTRL+Q)
  */
class LSPQuickDocAction extends ShowQuickDocInfoAction with DumbAware {
  private val LOG: Logger = Logger.getInstance(classOf[LSPQuickDocAction])

  override def actionPerformed(e: AnActionEvent): Unit = {
    val editor = e.getData(CommonDataKeys.EDITOR)
    val file = FileDocumentManager.getInstance().getFile(editor.getDocument)
    val language = PsiManager.getInstance(editor.getProject).findFile(file).getLanguage
    //Hack for IntelliJ 2018 TODO proper way
    if (LanguageDocumentation.INSTANCE.allForLanguage(language).isEmpty || (ApplicationInfo.getInstance().getMajorVersion.toInt > 2017) && PlainTextLanguage.INSTANCE == language) {
      EditorEventManager.forEditor(editor) match {
        case Some(manager) => manager.quickDoc(editor)
        case None => super.actionPerformed(e)
      }
    } else super.actionPerformed(e)
  }
}
