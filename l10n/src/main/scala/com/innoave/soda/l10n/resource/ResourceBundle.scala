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

import java.util.{ResourceBundle => JResourceBundle}
import scala.util.control.NonFatal
import com.innoave.soda.l10n.Message
import com.innoave.soda.l10n.DefineMessage
import java.util.{ResourceBundle => JResourceBundle}

class ResourceBundle(delegate: JResourceBundle) {

  def stringFor(key: String): String =
    _stringFor(key)

  def stringFor(message: Message): String =
    _stringFor(message.key)

  private def _stringFor(key: String): String =
    try {
      delegate.getString(key)
    } catch {
      case NonFatal(error) =>
        s"!!!$key!!!"
    }

}

object ResourceBundle {

  def stubFor(messages: DefineMessage): String =
    messages.values.map(_.key).mkString("=\n")

}
