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
package com.github.gtache.lsp.contributors.inspection

import java.awt.GridBagLayout

import com.intellij.codeInspection.InspectionProfileEntry
import javax.swing.JPanel

/**
  * The Options panel for the LSP inspection
  *
  * @param label
  * @param owner
  */
class LSPInspectionPanel(label: String, owner: InspectionProfileEntry) extends JPanel(new GridBagLayout) {
}
