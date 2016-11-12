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
package com.innoave.soda.l10n.resource

import com.innoave.soda.l10n.BundleName
import com.innoave.soda.l10n.DefineMessage
import com.innoave.soda.l10n.KeyNamingStrategy
import com.innoave.soda.l10n.Locale
import com.innoave.soda.l10n.Message

trait ResourceBundle {

  def bundleName: BundleName

  def locale: Locale

  def stringFor(key: String): String

  def stringFor(message: Message): String

}

object ResourceBundle {

  def stubFor(messages: DefineMessage): String =
    s"""#
      |# ${KeyNamingStrategy.simpleTypeName(messages.getClass)} : Message definitions
      |#
      |""".stripMargin +
    messages.values.map(m => m.key + "=\n").mkString

}
