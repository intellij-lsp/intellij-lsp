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

/**
  * Trait for companion objects of the UserConfigurableServerDefinition classes
  */
trait UserConfigurableServerDefinitionObject {
  /**
    * @return the type of the server definition
    */
  def typ: String

  /**
    * @return the type of the server definition in a nicer way
    */
  def getPresentableTyp: String

  /**
    * Transforms an array of string into the corresponding UserConfigurableServerDefinition
    *
    * @param arr The array
    * @return The server definition
    */
  def fromArray(arr: Array[String]): UserConfigurableServerDefinition
}
