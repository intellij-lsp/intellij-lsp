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

import com.github.gtache.lsp.requests.FileEventManager
import com.intellij.openapi.vfs._

/**
  * Object listening to file system changes
  */
object VFSListener extends VirtualFileListener {
  /**
    * Fired when a virtual file is renamed from within IDEA, or its writable status is changed.
    * For files renamed externally, {@link #fileCreated} and {@link #fileDeleted} events will be fired.
    *
    * @param event the event object containing information about the change.
    */
  override def propertyChanged(event: VirtualFilePropertyEvent): Unit = {
    if (event.getPropertyName == VirtualFile.PROP_NAME) FileEventManager.fileRenamed(event.getOldValue.asInstanceOf[String], event.getNewValue.asInstanceOf[String])
  }

  /**
    * Fired when the contents of a virtual file is changed.
    *
    * @param event the event object containing information about the change.
    */
  override def contentsChanged(event: VirtualFileEvent): Unit = {
    FileEventManager.fileChanged(event.getFile)
  }

  /**
    * Fired when a virtual file is deleted.
    *
    * @param event the event object containing information about the change.
    */
  override def fileDeleted(event: VirtualFileEvent): Unit = {
    FileEventManager.fileDeleted(event.getFile)
  }

  /**
    * Fired when a virtual file is moved from within IDEA.
    *
    * @param event the event object containing information about the change.
    */
  override def fileMoved(event: VirtualFileMoveEvent): Unit = {
    FileEventManager.fileMoved(event.getFile)
  }

  /**
    * Fired when a virtual file is copied from within IDEA.
    *
    * @param event the event object containing information about the change.
    */
  override def fileCopied(event: VirtualFileCopyEvent): Unit = {
    fileCreated(event)
  }

  /**
    * Fired when a virtual file is created. This event is not fired for files discovered during initial VFS initialization.
    *
    * @param event the event object containing information about the change.
    */
  override def fileCreated(event: VirtualFileEvent): Unit = {
    FileEventManager.fileCreated(event.getFile)
  }

  /**
    * Fired before the change of a name or writable status of a file is processed.
    *
    * @param event the event object containing information about the change.
    */
  override def beforePropertyChange(event: VirtualFilePropertyEvent): Unit = {
  }

  /**
    * Fired before the change of contents of a file is processed.
    *
    * @param event the event object containing information about the change.
    */
  override def beforeContentsChange(event: VirtualFileEvent): Unit = {
  }

  /**
    * Fired before the deletion of a file is processed.
    *
    * @param event the event object containing information about the change.
    */
  override def beforeFileDeletion(event: VirtualFileEvent): Unit = {
  }

  /**
    * Fired before the movement of a file is processed.
    *
    * @param event the event object containing information about the change.
    */
  override def beforeFileMovement(event: VirtualFileMoveEvent): Unit = {
  }
}
