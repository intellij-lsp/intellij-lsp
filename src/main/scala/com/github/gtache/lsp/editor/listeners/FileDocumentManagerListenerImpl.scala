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

import com.github.gtache.lsp.requests.FileEventManager
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManagerListener
import com.intellij.openapi.vfs.VirtualFile

/**
  * A FileDocumentManagerListener implementation which listens to beforeDocumentSaving / beforeAllDocumentsSaving
  */
object FileDocumentManagerListenerImpl extends FileDocumentManagerListener {
  override def beforeDocumentSaving(document: Document): Unit = {
    FileEventManager.willSave(document)
  }

  override def unsavedDocumentsDropped(): Unit = {}

  override def beforeAllDocumentsSaving(): Unit = FileEventManager.willSaveAllDocuments()

  override def beforeFileContentReload(virtualFile: VirtualFile, document: Document): Unit = {}

  override def fileWithNoDocumentChanged(virtualFile: VirtualFile): Unit = {}

  override def fileContentReloaded(virtualFile: VirtualFile, document: Document): Unit = {}

  override def fileContentLoaded(virtualFile: VirtualFile, document: Document): Unit = {}
}
