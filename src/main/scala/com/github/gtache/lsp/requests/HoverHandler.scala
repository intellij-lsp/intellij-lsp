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
package com.github.gtache.lsp.requests

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.options.MutableDataSet
import org.eclipse.lsp4j.Hover
import org.eclipse.lsp4j.jsonrpc.validation.NonNull

/**
  * Object used to process Hover responses
  */
object HoverHandler {

  /**
    * Returns the hover string corresponding to an Hover response
    *
    * @param hover The Hover
    * @return The string response
    */
  def getHoverString(@NonNull hover: Hover): String = {
    import scala.collection.JavaConverters._
    if (hover != null && hover.getContents != null) {
      val hoverContents = hover.getContents
      if (hoverContents.isLeft) {
        val contents = hoverContents.getLeft.asScala
        if (contents == null || contents.isEmpty) "" else {
          val stuff = contents.map(c => {
            if (c.isLeft) c.getLeft else if (c.isRight) {
              val options = new MutableDataSet()
              val parser = Parser.builder(options).build()
              val renderer = HtmlRenderer.builder(options).build()
              val markedString = c.getRight
              val string = if (markedString.getLanguage != null && !markedString.getLanguage.isEmpty)
                s"""```${markedString.getLanguage} ${markedString.getValue} ```""" else markedString.getValue
              if (!string.isEmpty) "<html>" + renderer.render(parser.parse(string)) + "</html>" else ""
            } else ""
          }).filter(s => !s.isEmpty)
          if (stuff.isEmpty) {
            ""
          } else {
            stuff.reduce((a, b) => a + "\n\n" + b)
          }
        }
      } else if (hoverContents.isRight) {
        hoverContents.getRight.getValue //TODO
      } else ""
    } else ""
  }

}
