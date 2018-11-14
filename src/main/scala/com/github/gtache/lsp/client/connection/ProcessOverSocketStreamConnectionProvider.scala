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
import java.net.{ServerSocket, Socket}
import java.util.Objects

import com.intellij.openapi.diagnostic.Logger

/**
  * Unused at the moment
  *
  * @param commands
  * @param workingDir
  * @param port
  */
class ProcessOverSocketStreamConnectionProvider(commands: Seq[String], workingDir: String, port: Int = 0) extends ProcessStreamConnectionProvider(commands, workingDir) {
  private val LOG = Logger.getInstance(classOf[ProcessOverSocketStreamConnectionProvider])
  private var socket: Socket = _
  private var inputStream: InputStream = _
  private var outputStream: OutputStream = _

  @throws[IOException]
  override def start(): Unit = {
    val serverSocket = new ServerSocket(port)
    val socketThread = new Thread(() => {
      try
        socket = serverSocket.accept
      catch {
        case e: IOException =>
          LOG.error(e)
      } finally try
        serverSocket.close()
      catch {
        case e: IOException =>
          LOG.error(e)
      }
    })
    socketThread.start()
    super.start()
    try {
      socketThread.join(5000)
    }
    catch {
      case e: InterruptedException =>
        LOG.error(e)
    }
    if (socket == null) throw new IOException("Unable to make socket connection: " + toString) //$NON-NLS-1$
    inputStream = socket.getInputStream
    outputStream = socket.getOutputStream
  }

  override def getInputStream: InputStream = inputStream

  override def getOutputStream: OutputStream = outputStream

  override def stop(): Unit = {
    super.stop()
    if (socket != null) try
      socket.close()
    catch {
      case e: IOException =>
        LOG.error(e)
    }
  }

  override def hashCode: Int = {
    val result = super.hashCode
    result ^ Objects.hashCode(this.port)
  }
}
