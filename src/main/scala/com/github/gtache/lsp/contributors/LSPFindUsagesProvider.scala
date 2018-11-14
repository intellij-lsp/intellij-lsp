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
package com.github.gtache.lsp.contributors

import com.github.gtache.lsp.contributors.psi.LSPPsiElement
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.{PsiElement, PsiFile, PsiNamedElement}

/**
  * A findUsagesProvider for LSP (ALT+F7)
  */
class LSPFindUsagesProvider extends FindUsagesProvider {

  private val LOG: Logger = Logger.getInstance(classOf[LSPFindUsagesProvider])

  override def getHelpId(psiElement: PsiElement): String = null

  override def canFindUsagesFor(psiElement: PsiElement): Boolean = {
    psiElement match {
      case p: PsiFile => true
      case l: LSPPsiElement => true
      case _ => false
    }
  }

  override def getWordsScanner: WordsScanner = null

  override def getNodeText(element: PsiElement, useFullName: Boolean): String = element.getText

  override def getDescriptiveName(element: PsiElement): String = element match {
    case e: PsiNamedElement => e.getName
    case _ => ""
  }

  override def getType(element: PsiElement): String = element match {
    case _ => ""
  }
}
