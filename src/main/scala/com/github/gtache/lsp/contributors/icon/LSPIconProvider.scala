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
package com.github.gtache.lsp.contributors.icon

import com.github.gtache.lsp.client.languageserver.ServerStatus
import com.github.gtache.lsp.client.languageserver.serverdefinition.LanguageServerDefinition
import com.intellij.openapi.extensions.ExtensionPointName
import javax.swing.Icon
import org.eclipse.lsp4j.{CompletionItemKind, SymbolKind}

object LSPIconProvider {
  val EP_NAME: ExtensionPointName[LSPIconProvider] = ExtensionPointName.create("com.github.gtache.lsp.contributors.icon.LSPIconProvider")

  def getDefaultCompletionIcon(kind: CompletionItemKind): Icon = LSPDefaultIconProvider.getCompletionIcon(kind)

  def getDefaultStatusIcons: Map[ServerStatus, Icon] = LSPDefaultIconProvider.getStatusIcons

  def getDefaultSymbolIcon(kind: SymbolKind): Icon = LSPDefaultIconProvider.getSymbolIcon(kind)
}

trait LSPIconProvider {

  def getCompletionIcon(kind: CompletionItemKind): Icon = LSPDefaultIconProvider.getCompletionIcon(kind)

  def getStatusIcons: Map[ServerStatus, Icon] = LSPDefaultIconProvider.getStatusIcons

  def getSymbolIcon(kind: SymbolKind): Icon = LSPDefaultIconProvider.getSymbolIcon(kind)

  def isSpecificFor(serverDefinition: LanguageServerDefinition): Boolean
}
