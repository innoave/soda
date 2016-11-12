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
  // default l10n implementation based on
  // `resource.JavaUtf8ResourceBundle` and `format.JavaMessageFormat`
  import ResourceBundleRenderLocalized._

  final def render[T](localized: Localized[T]): Localized[T] = localized

  final def render[T, A <: Product](localized: LocalizedP[T, A]): LocalizedP[T, A] = localized

  implicit final class RenderLocalizedOps[T](val wrappedLocalized: Localized[T]) extends AnyVal {
    final def asLocaleText(implicit locale: Locale): LocaleText =
      renderLocalized(wrappedLocalized)(locale)
    final def in(locale: Locale): LocaleText =
      renderLocalized(wrappedLocalized)(locale)
  }

  implicit final class RenderLocalizedPOps[T, A <: Product](val wrappedLocalized: LocalizedP[T, A]) extends AnyVal {
    final def asLocaleText(implicit locale: Locale): LocaleText =
      renderLocalized(wrappedLocalized)(locale)
    final def in(locale: Locale): LocaleText =
      renderLocalized(wrappedLocalized)(locale)
  }

  implicit final class Message0Ops(val wrappedMessage: Message) extends AnyVal {
    final def apply(): Localized[Message] =
      LocalizedValue(wrappedMessage, wrappedMessage.key(), wrappedMessage.bundleName())
  }

  implicit final class Message1Ops[A1](
      val wrappedMessage: MessageP[Tuple1[A1]]
      ) extends AnyVal {
    final def apply(arg1: A1): LocalizedP[Message, Tuple1[A1]] =
      LocalizedPValue(wrappedMessage, Tuple1(arg1), wrappedMessage.key(), wrappedMessage.bundleName())
  }

  implicit final class Message2Ops[A1, A2](
      val wrappedMessage: MessageP[Tuple2[A1, A2]]
      ) extends AnyVal {
    final def apply(arg1: A1, arg2: A2): LocalizedP[Message, Tuple2[A1, A2]] =
      LocalizedPValue(wrappedMessage, (arg1, arg2), wrappedMessage.key(), wrappedMessage.bundleName())
  }

  implicit final class Message3Ops[A1, A2, A3](
      val wrappedMessage: MessageP[Tuple3[A1, A2, A3]]
      ) extends AnyVal {
    final def apply(
        arg1: A1, arg2: A2, arg3: A3
        ): LocalizedP[Message, Tuple3[A1, A2, A3]] =
      LocalizedPValue(wrappedMessage, (arg1, arg2, arg3), wrappedMessage.key(), wrappedMessage.bundleName())
  }

  implicit final class Message4Ops[A1, A2, A3, A4](
      val wrappedMessage: MessageP[Tuple4[A1, A2, A3, A4]]
      ) extends AnyVal {
    final def apply(
        arg1: A1, arg2: A2, arg3: A3, arg4: A4
        ): LocalizedP[Message, Tuple4[A1, A2, A3, A4]] =
      LocalizedPValue(wrappedMessage, (arg1, arg2, arg3, arg4), wrappedMessage.key(), wrappedMessage.bundleName())
  }

  implicit final class Message5Ops[A1, A2, A3, A4, A5](
      val wrappedMessage: MessageP[Tuple5[A1, A2, A3, A4, A5]]
      ) extends AnyVal {
    final def apply(
        arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5
        ): LocalizedP[Message, Tuple5[A1, A2, A3, A4, A5]] =
      LocalizedPValue(wrappedMessage, (arg1, arg2, arg3, arg4, arg5), wrappedMessage.key(), wrappedMessage.bundleName())
  }

  implicit final class Message6Ops[A1, A2, A3, A4, A5, A6](
      val wrappedMessage: MessageP[Tuple6[A1, A2, A3, A4, A5, A6]]
      ) extends AnyVal {
    final def apply(
        arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5, arg6: A6
        ): LocalizedP[Message, Tuple6[A1, A2, A3, A4, A5, A6]] =
      LocalizedPValue(wrappedMessage, (arg1, arg2, arg3, arg4, arg5, arg6), wrappedMessage.key(), wrappedMessage.bundleName())
  }

  implicit final class Message7Ops[A1, A2, A3, A4, A5, A6, A7](
      val wrappedMessage: MessageP[Tuple7[A1, A2, A3, A4, A5, A6, A7]]
      ) extends AnyVal {
    final def apply(
        arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5, arg6: A6, arg7: A7
        ): LocalizedP[Message, Tuple7[A1, A2, A3, A4, A5, A6, A7]] =
      LocalizedPValue(wrappedMessage, (arg1, arg2, arg3, arg4, arg5, arg6, arg7), wrappedMessage.key(), wrappedMessage.bundleName())
  }

  implicit final class Message8Ops[A1, A2, A3, A4, A5, A6, A7, A8](
      val wrappedMessage: MessageP[Tuple8[A1, A2, A3, A4, A5, A6, A7, A8]]
      ) extends AnyVal {
    final def apply(
        arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5, arg6: A6, arg7: A7, arg8: A8
        ): LocalizedP[Message, Tuple8[A1, A2, A3, A4, A5, A6, A7, A8]] =
      LocalizedPValue(wrappedMessage, (arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8), wrappedMessage.key(), wrappedMessage.bundleName())
  }

  implicit final class Message9Ops[A1, A2, A3, A4, A5, A6, A7, A8, A9](
      val wrappedMessage: MessageP[Tuple9[A1, A2, A3, A4, A5, A6, A7, A8, A9]]
      ) extends AnyVal {
    final def apply(
        arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5, arg6: A6, arg7: A7, arg8: A8, arg9: A9
        ): LocalizedP[Message, Tuple9[A1, A2, A3, A4, A5, A6, A7, A8, A9]] =
      LocalizedPValue(wrappedMessage, (arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9), wrappedMessage.key(), wrappedMessage.bundleName())
  }

}
