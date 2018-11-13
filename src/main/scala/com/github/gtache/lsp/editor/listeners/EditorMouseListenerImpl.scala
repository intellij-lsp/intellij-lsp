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

import com.intellij.openapi.editor.event.{EditorMouseEvent, EditorMouseListener}

/**
  * An EditorMouseListener implementation which just listens to mouseExited and mouseEntered
  */
class EditorMouseListenerImpl extends EditorMouseListener with LSPListener {

  override def mouseExited(e: EditorMouseEvent): Unit = {
    if (checkManager()) manager.mouseExited()
  }

  override def mousePressed(e: EditorMouseEvent): Unit = {
  }

  override def mouseReleased(e: EditorMouseEvent): Unit = {
  }

  override def mouseEntered(e: EditorMouseEvent): Unit = {
    if (checkManager()) manager.mouseEntered()
  }

  override def mouseClicked(e: EditorMouseEvent): Unit = {
    if (checkManager()) manager.mouseClicked(e)
  }
}
