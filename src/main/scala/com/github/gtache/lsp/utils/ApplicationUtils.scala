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
package com.github.gtache.lsp.utils

import java.util.concurrent.{Callable, Future}

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.util.Computable

/**
  * Various methods to write thread related instructions more concisely
  */
object ApplicationUtils {

  def invokeLater(runnable: Runnable): Unit = {
    ApplicationManager.getApplication.invokeLater(runnable)
  }

  def pool(runnable: Runnable): Unit = {
    ApplicationManager.getApplication.executeOnPooledThread(runnable)
  }

  def callablePool[T](callable: Callable[T]): Future[T] = {
    ApplicationManager.getApplication.executeOnPooledThread(callable)
  }

  def computableReadAction[T](computable: Computable[T]): T = {
    ApplicationManager.getApplication.runReadAction(computable)
  }

  def writeAction(runnable: Runnable): Unit = {
    ApplicationManager.getApplication.runWriteAction(runnable)
  }

  def computableWriteAction[T](computable: Computable[T]): T = {
    ApplicationManager.getApplication.runWriteAction(computable)
  }
}
