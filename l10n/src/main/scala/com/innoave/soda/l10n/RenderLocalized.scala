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

  final def renderLocalized[T](localized: Localized[T])(implicit locale: Locale): LocaleText =
    localized.value match {
      case v: LocalizedP[_, _]  =>
        LocaleText(messageFormatFor(resourceBundleFor(v.bundleName(), locale).stringFor(v), locale).format(renderLocalized(v).value))
      case v: Localized[_] =>
        LocaleText(messageFormatFor(resourceBundleFor(v.bundleName(), locale).stringFor(v), locale).format(renderLocalized(v).value))
      case v =>
        LocaleText(messageFormatFor(resourceBundleFor(localized.bundleName(), locale).stringFor(localized), locale).format())
    }

  final def renderLocalized[T, A <: Product](localized: LocalizedP[T, A])(implicit locale: Locale): LocaleText = {
    val args = localized.args.productIterator.map({
      case a: LocalizedP[_, _] =>
        messageFormatFor(resourceBundleFor(a.bundleName(), locale).stringFor(a), locale).format(renderLocalized(a).value)
      case a: Localized[_] =>
        messageFormatFor(resourceBundleFor(a.bundleName(), locale).stringFor(a), locale).format(renderLocalized(a).value)
      case a => a
    })
    localized.value match {
      case v: LocalizedP[_, _]  =>
        LocaleText(messageFormatFor(resourceBundleFor(v.bundleName(), locale).stringFor(v), locale).format(args.toArray))
      case v: Localized[_]  =>
        LocaleText(messageFormatFor(resourceBundleFor(v.bundleName(), locale).stringFor(v), locale).format(args.toArray))
      case v =>
        LocaleText(messageFormatFor(resourceBundleFor(localized.bundleName(), locale).stringFor(localized), locale).format(args.toArray))
    }
  }

}
