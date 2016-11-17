/*
 * Copyright (c) 2016, Innoave.com
 * All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL INNOAVE.COM OR ITS CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.innoave.soda.l10n

import com.innoave.soda.l10n.resource.ResourceBundle

trait RenderLocalized {

  protected def messageFormatFor(pattern: String, locale: Locale): MessageFormat

  protected def resourceBundleFor(bundleName: BundleName, locale: Locale): ResourceBundle

  final def renderLocalized[T](localized: Localized[T])(implicit locale: Locale): LocaleText = {
    val pattern = resourceBundleFor(localized.bundleName(), locale).stringFor(localized)
    LocaleText(messageFormatFor(pattern, locale).format())
  }

  final def renderLocalized[T, A <: Product](localized: LocalizedP[T, A])(implicit locale: Locale): LocaleText = {
    val args = localized.args.productIterator.map({
      case a: LocalizedP[_, _] => renderLocalized(a).value
      case a: Localized[_] => renderLocalized(a).value
      case a: LocaleText => a.value
      case a => a
    })
    val pattern = resourceBundleFor(localized.bundleName(), locale).stringFor(localized)
    LocaleText(messageFormatFor(pattern, locale).format(args.toArray))
  }

}
