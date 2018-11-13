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
package com.github.gtache.lsp.client.languageserver.serverdefinition

import com.github.gtache.lsp.client.connection.{ProcessStreamConnectionProvider, StreamConnectionProvider}

object CommandServerDefinition extends UserConfigurableServerDefinitionObject {
  override def fromArray(arr: Array[String]): UserConfigurableServerDefinition = {
    val raw = RawCommandServerDefinition.fromArray(arr)
    if (raw == null) {
      val exe = ExeLanguageServerDefinition.fromArray(arr)
      exe
    } else {
      raw
    }
  }

  override def getPresentableTyp: String = "Command"

  override def typ = "command"
}

/**
  * A base trait for every command-line server definition
  */
trait CommandServerDefinition extends UserConfigurableServerDefinition {

  def command: Array[String]

  override def createConnectionProvider(workingDir: String): StreamConnectionProvider = {
    new ProcessStreamConnectionProvider(command, workingDir)
  }
}
