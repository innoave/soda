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

  val bundleName: BundleName = new BundleName(toString)
  val keyNamingStrategy: KeyNamingStrategy = KeyNamingStrategy.default

  override def toString: String = KeyNamingStrategy.simpleTypeName(getClass)

  private def keyFor[T](value: T): String =
    keyNamingStrategy.keyFor(0, KeyNamingStrategy.simpleTypeName(value.getClass))

  final protected def localized[T](value: T): Localized[T] =
    new LocalizedValue(value, keyFor(value), bundleName)

  final protected def localized[T, A <: Product](value: T, args: A): LocalizedP[T, A] =
    new LocalizedPValue(value, args, keyFor(value), bundleName)

}
