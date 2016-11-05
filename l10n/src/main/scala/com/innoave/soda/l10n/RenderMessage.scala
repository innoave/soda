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

trait RenderMessage {

  def apply(message: Message0)(implicit locale: Locale): LocaleText

  def apply[T1](message: Message1[T1], arg1: T1)(implicit locale: Locale): LocaleText

  def apply[T1, T2](message: Message2[T1, T2], arg1: T1, arg2: T2)(implicit locale: Locale): LocaleText

  def apply[T1, T2, T3](message: Message3[T1, T2, T3], arg1: T1, arg2: T2, arg3: T3)(implicit locale: Locale): LocaleText

}

object RenderMessage {

  def render(message: Message0)(implicit locale: Locale): String =
    MessageBundle.read(message.bundleName, locale).raw(message)

  def render[T1](message: Message1[T1], arg1: T1)(implicit locale: Locale): String =
    MessageBundle.read(message.bundleName, locale).formatted(message, arg1)

  def render[T1, T2](message: Message2[T1, T2], arg1: T1, arg2: T2)(implicit locale: Locale): String =
    MessageBundle.read(message.bundleName, locale).formatted(message, arg1, arg2)

  def render[T1, T2, T3](message: Message3[T1, T2, T3], arg1: T1, arg2: T2, arg3: T3)(implicit locale: Locale): String =
    MessageBundle.read(message.bundleName, locale).formatted(message, arg1, arg2, arg3)

}
