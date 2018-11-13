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
package com.github.gtache.lsp.editor.listeners

import com.github.gtache.lsp.editor.EditorEventManager
import com.github.gtache.lsp.utils.FileUtils
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentSynchronizationVetoer

/**
  * This class is used to reject save requests
  * It is used for willSaveWaitUntil to allow time to apply the edits
  */
//TODO check called before willSave
class LSPFileDocumentSynchronizationVetoer extends FileDocumentSynchronizationVetoer {
  override def maySaveDocument(document: Document, isSaveExplicit: Boolean): Boolean = {
    EditorEventManager.forUri(FileUtils.documentToUri(document)) match {
      case Some(m) =>
        if (m.needSave) {
          m.needSave = false
          super.maySaveDocument(document, isSaveExplicit)
        }
        else if (m.wrapper.isWillSaveWaitUntil) false
        else super.maySaveDocument(document, isSaveExplicit)
      case None =>
        super.maySaveDocument(document, isSaveExplicit)
    }
  }
}
