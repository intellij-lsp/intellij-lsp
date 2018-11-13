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
package com.github.gtache.lsp.client.languageserver.serverdefinition

object RawCommandServerDefinition extends UserConfigurableServerDefinitionObject {
  /**
    * Transforms an array of string into the corresponding UserConfigurableServerDefinition
    *
    * @param arr The array
    * @return The server definition
    */
  override def fromArray(arr: Array[String]): CommandServerDefinition = {
    if (arr.head == typ) {
      val arrTail = arr.tail
      if (arrTail.length > 1) {
        RawCommandServerDefinition(arrTail.head, arrTail.tail)
      } else {
        null
      }
    } else {
      null
    }
  }

  override def typ = "rawCommand"

  override def getPresentableTyp = "Raw command"
}

/**
  * A class representing a raw command to launch a languageserver
  *
  * @param ext     The extension
  * @param command The command to run
  */
case class RawCommandServerDefinition(ext: String, command: Array[String]) extends CommandServerDefinition {

  import RawCommandServerDefinition.typ

  /**
    * @return The array corresponding to the server definition
    */
  override def toArray: Array[String] = Array(typ, ext) ++ command

  override def toString: String = typ + " : " + command.mkString(" ")

  override def equals(obj: scala.Any): Boolean = obj match {
    case RawCommandServerDefinition(ext1, commands1) =>
      ext == ext1 && command.toSeq == commands1.toSeq
    case _ => false
  }

  override def hashCode(): Int = ext.hashCode + 3 * command.hashCode()

}
