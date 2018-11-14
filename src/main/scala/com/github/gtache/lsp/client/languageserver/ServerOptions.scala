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
package com.github.gtache.lsp.client.languageserver

import org.eclipse.lsp4j._

/**
  * Class containing the options of the language server
  *
  * @param syncKind                        The type of synchronization
  * @param completionOptions               The completion options
  * @param signatureHelpOptions            The signatureHelp options
  * @param codeLensOptions                 The codeLens options
  * @param documentOnTypeFormattingOptions The onTypeFormatting options
  * @param documentLinkOptions             The link options
  * @param executeCommandOptions           The execute options
  */
case class ServerOptions(syncKind: TextDocumentSyncKind, completionOptions: CompletionOptions, signatureHelpOptions: SignatureHelpOptions, codeLensOptions: CodeLensOptions, documentOnTypeFormattingOptions: DocumentOnTypeFormattingOptions, documentLinkOptions: DocumentLinkOptions, executeCommandOptions: ExecuteCommandOptions) {

}
