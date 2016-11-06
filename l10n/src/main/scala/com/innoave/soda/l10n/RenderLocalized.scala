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

trait RenderLocalized {

  protected def messageFormatFor(pattern: String, locale: Locale): MessageFormat =
    new MessageFormat(pattern, locale)

  protected def patternFor(key: String, locale: Locale, bundleName: BundleName): String

  final def renderLocalized[T](localized: Localized[T])(implicit locale: Locale): LocaleText =
    localized.value match {
      case v: LocalizedP[_, _]  =>
        LocaleText(messageFormatFor(patternFor(v.key, locale, v.bundleName), locale).format(renderLocalized(v).string))
      case v: Localized[_] =>
        LocaleText(messageFormatFor(patternFor(v.key, locale, v.bundleName), locale).format(renderLocalized(v).string))
      case v =>
        LocaleText(messageFormatFor(patternFor(localized.key, locale, localized.bundleName), locale).format(v))
    }

  final def renderLocalized[T, A <: Product](localized: LocalizedP[T, A])(implicit locale: Locale): LocaleText = {
    val as = localized.args.productIterator.map({
      case a: LocalizedP[_, _] =>
        messageFormatFor(patternFor(a.key, locale, a.bundleName), locale).format(renderLocalized(a).string)
      case a: Localized[_] =>
        messageFormatFor(patternFor(a.key, locale, a.bundleName), locale).format(renderLocalized(a).string)
      case a => a
    })
    localized.value match {
      case v: LocalizedP[_, _]  =>
        val valuePlusArgs = Iterator(renderLocalized(v).string) ++ as
        LocaleText(messageFormatFor(patternFor(v.key, locale, v.bundleName), locale).format(valuePlusArgs.toArray))
      case v: Localized[_]  =>
        val valuePlusArgs = Iterator(renderLocalized(v).string) ++ as
        LocaleText(messageFormatFor(patternFor(v.key, locale, v.bundleName), locale).format(valuePlusArgs.toArray))
      case v =>
        val valuePlusArgs = Iterator(v) ++ as
        LocaleText(messageFormatFor(patternFor(localized.key, locale, localized.bundleName), locale).format(valuePlusArgs.toArray))
    }
  }

  final def renderLocalized(message: Message0)(implicit locale: Locale): LocaleText =
    LocaleText(patternFor(message.key, locale, message.bundleName))

  final def renderLocalized[T1](message: Message1[T1], arg1: T1)(implicit locale: Locale): LocaleText =
    LocaleText(messageFormatFor(patternFor(message.key, locale, message.bundleName), locale).format(arg1))

  final def renderLocalized[T1, T2](message: Message2[T1, T2], arg1: T1, arg2: T2)(implicit locale: Locale): LocaleText =
    LocaleText(messageFormatFor(patternFor(message.key, locale, message.bundleName), locale).format(arg1, arg2))

  final def renderLocalized[T1, T2, T3](message: Message3[T1, T2, T3], arg1: T1, arg2: T2, arg3: T3)(implicit locale: Locale): LocaleText =
    LocaleText(messageFormatFor(patternFor(message.key, locale, message.bundleName), locale).format(arg1, arg2, arg3))

}
