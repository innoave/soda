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

import com.innoave.soda.l10n.resource.ResourceBundleRenderLocalized

package object syntax {
  // default l10n implementation based on `resource.ResourceBundle`
  import ResourceBundleRenderLocalized._

  final def rl[T, A <: Product](localized: LocalizedP[T, A])(implicit locale: Locale): LocaleText =
    renderLocalized(localized)(locale)

  final def localized[T, A <: Product](localized: LocalizedP[T, A]): LocalizedP[T, A] = localized

  implicit final class RenderLocalizedPOps[T, A <: Product](val wrappedLocalized: LocalizedP[T, A]) extends AnyVal {
    final def text(implicit locale: Locale): LocaleText =
      renderLocalized(wrappedLocalized)(locale)
  }

  final def rl[T](localized: Localized[T])(implicit locale: Locale): LocaleText =
    renderLocalized(localized)(locale)

  final def localized[T](localized: Localized[T]): Localized[T] = localized

  implicit final class RenderLocalizedOps[T](val wrappedLocalized: Localized[T]) extends AnyVal {
    final def text(implicit locale: Locale): LocaleText =
      renderLocalized(wrappedLocalized)(locale)
  }

  final def rl(message: Message0)(implicit locale: Locale): LocaleText =
    renderLocalized(message)

  final def rl[A1](message: MessageP[Tuple1[A1]], arg1: A1)
      (implicit locale: Locale): LocaleText = renderLocalized(message, Tuple1(arg1))

  final def rl[A1, A2](message: MessageP[Tuple2[A1, A2]], arg1: A1, arg2: A2)
      (implicit locale: Locale): LocaleText = renderLocalized(message, (arg1, arg2))

  final def rl[A1, A2, A3](message: MessageP[Tuple3[A1, A2, A3]], arg1: A1, arg2: A2, arg3: A3)
      (implicit locale: Locale): LocaleText = renderLocalized(message, (arg1, arg2, arg3))

  final def rl[A1, A2, A3, A4](message: MessageP[Tuple4[A1, A2, A3, A4]], arg1: A1, arg2: A2, arg3: A3, arg4: A4)
      (implicit locale: Locale): LocaleText = renderLocalized(message, (arg1, arg2, arg3, arg4))

  final def rl[A1, A2, A3, A4, A5](message: MessageP[Tuple5[A1, A2, A3, A4, A5]], arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5)
      (implicit locale: Locale): LocaleText = renderLocalized(message, (arg1, arg2, arg3, arg4, arg5))

  final def rl[A1, A2, A3, A4, A5, A6](message: MessageP[Tuple6[A1, A2, A3, A4, A5, A6]], arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5, arg6: A6)
      (implicit locale: Locale): LocaleText = renderLocalized(message, (arg1, arg2, arg3, arg4, arg5, arg6))

  final def rl[A1, A2, A3, A4, A5, A6, A7](message: MessageP[Tuple7[A1, A2, A3, A4, A5, A6, A7]], arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5, arg6: A6, arg7: A7)
      (implicit locale: Locale): LocaleText = renderLocalized(message, (arg1, arg2, arg3, arg4, arg5, arg6, arg7))

  final def rl[A1, A2, A3, A4, A5, A6, A7, A8](message: MessageP[Tuple8[A1, A2, A3, A4, A5, A6, A7, A8]], arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5, arg6: A6, arg7: A7, arg8: A8)
      (implicit locale: Locale): LocaleText = renderLocalized(message, (arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8))

  final def rl[A1, A2, A3, A4, A5, A6, A7, A8, A9](message: MessageP[Tuple9[A1, A2, A3, A4, A5, A6, A7, A8, A9]], arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5, arg6: A6, arg7: A7, arg8: A8, arg9: A9)
      (implicit locale: Locale): LocaleText = renderLocalized(message, (arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9))

}
