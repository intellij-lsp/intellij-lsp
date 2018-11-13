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
package com.github.gtache.lsp.utils

import java.util
import java.util.ResourceBundle

import com.intellij.openapi.diagnostic.Logger

import scala.annotation.varargs

/**
  * Object containing some useful methods for the plugin
  */
object Utils {

  val bundle: ResourceBundle = ResourceBundle.getBundle("com.github.gtache.lsp.LSPBundle")
  val lineSeparator: String = System.getProperty("line.separator")

  private val LOG: Logger = Logger.getInstance(Utils.getClass)

  /**
    * Transforms an array into a string (using mkString, useful for Java)
    *
    * @param arr The array
    * @param sep A separator
    * @return The result of mkString
    */
  def arrayToString(arr: Array[Any], sep: String = ""): String = {
    arr.mkString(sep)
  }

  /**
    * Concatenate multiple arrays
    *
    * @param arr The arrays
    * @return The concatenated arrays
    */
  @varargs def concatenateArrays(arr: Array[Any]*): Array[Any] = {
    arr.flatten.toArray
  }

  def stringToList(str: String, sep: String = lineSeparator): util.List[String] = {
    import scala.collection.JavaConverters._
    str.split(sep).toIndexedSeq.asJava
  }


}
