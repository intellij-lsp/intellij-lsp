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
package com.github.gtache.lsp.requests

/**
  * An object containing the Timeout for the various requests
  */
object Timeout {

  import Timeouts._

  private var timeouts: Map[Timeouts, Int] = Timeouts.values().map(t => t -> t.getDefaultTimeout).toMap


  def getTimeoutsJava: java.util.Map[Timeouts, Integer] = {
    import scala.collection.JavaConverters._
    timeouts.map(t => (t._1, t._2.asInstanceOf[Integer])).asJava
  }

  def setTimeouts(timeouts: Map[Timeouts, Int]): Unit = {
    this.timeouts = timeouts
  }

  def setTimeouts(timeouts: java.util.Map[Timeouts, Integer]): Unit = {
    import scala.collection.JavaConverters._
    this.timeouts = timeouts.asScala.map(entry => (entry._1, entry._2.toInt)).toMap
  }

  def CODEACTION_TIMEOUT: Int = timeouts(CODEACTION)

  def CODELENS_TIMEOUT: Int = timeouts(CODELENS)

  def COMPLETION_TIMEOUT: Int = timeouts(COMPLETION)

  def DEFINITION_TIMEOUT: Int = timeouts(DEFINITION)

  def DOC_HIGHLIGHT_TIMEOUT: Int = timeouts(DOC_HIGHLIGHT)

  def EXECUTE_COMMAND_TIMEOUT: Int = timeouts(EXECUTE_COMMAND)

  def FORMATTING_TIMEOUT: Int = timeouts(FORMATTING)

  def HOVER_TIMEOUT: Int = timeouts(HOVER)

  def INIT_TIMEOUT: Int = timeouts(INIT)

  def REFERENCES_TIMEOUT: Int = timeouts(REFERENCES)

  def SIGNATURE_TIMEOUT: Int = timeouts(SIGNATURE)

  def SHUTDOWN_TIMEOUT: Int = timeouts(SHUTDOWN)

  def SYMBOLS_TIMEOUT: Int = timeouts(SYMBOLS)

  def WILLSAVE_TIMEOUT: Int = timeouts(WILLSAVE)
}
