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
package com.github.gtache.lsp.editor

import com.intellij.openapi.editor.markup.RangeHighlighter
import org.eclipse.lsp4j.Diagnostic

/**
  * A class representing a Diagnostic Range
  * The diagnostic is sent from the server, the rangeHighlighter is created from the severity and range of the diagnostic
  *
  * @param rangeHighlighter The rangeHighlighter of the diagnostic
  * @param diagnostic       The diagnostic
  */
case class DiagnosticRangeHighlighter(rangeHighlighter: RangeHighlighter, diagnostic: Diagnostic) {

}
