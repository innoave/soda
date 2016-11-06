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

trait DefineLocalized { thisdefine =>

  val bundleName: BundleName = new BundleName("localized")
  val keyNamingStrategy: KeyNamingStrategy = KeyNamingStrategy.default

  private def keyFor[T](value: T): String =
    keyNamingStrategy.keyFor(0, KeyNamingStrategy.simpleTypeName(value.getClass))

  protected def localized[T](value: T): Localized[T] = new LocalizedWrapper(value)

  protected def localized[T, A <: Product](value: T, args: A): LocalizedP[T, A] =
    new LocalizedPWrapper(value, args)

  final class LocalizedWrapper[T] private[DefineLocalized](
      override val value: T
      ) extends Localized[T] {
    override val bundleName = thisdefine.bundleName
    override val key = keyFor(value)
  }

  final class LocalizedPWrapper[T, A <: Product] private[DefineLocalized](
      override val value: T,
      override val args: A
      ) extends LocalizedP[T, A] {
    override val bundleName = thisdefine.bundleName
    override val key = keyFor(value)
  }

}
