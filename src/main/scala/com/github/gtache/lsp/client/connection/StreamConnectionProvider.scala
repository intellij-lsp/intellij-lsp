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
/* Adapted from lsp4e */
package com.github.gtache.lsp.client.connection

import java.io.{IOException, InputStream, OutputStream}
import java.net.URI

import org.eclipse.lsp4j.jsonrpc.messages.Message
import org.eclipse.lsp4j.services.LanguageServer

/**
  * A class representing a stream connection
  */
trait StreamConnectionProvider {
  @throws[IOException]
  def start(): Unit

  def getInputStream: InputStream

  def getOutputStream: OutputStream

  /**
    * User provided initialization options.
    */
  def getInitializationOptions(rootUri: URI): Any = null

  def stop(): Unit

  /**
    * Allows to hook custom behavior on messages.
    *
    * @param message        a message
    * @param languageServer the language server receiving/sending the message.
    * @param rootURI
    */
  def handleMessage(message: Message, languageServer: LanguageServer, rootURI: URI): Unit = {
  }
}
