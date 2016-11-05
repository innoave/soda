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

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class MessageKeyNamingStrategySpec extends FlatSpec with Matchers {

  "MessageKeyNamingStrategy" should "be DotSeparatedKeyNames by default" in {

    KeyNamingStrategy.default shouldBe DotSeparatedKeyNames

  }

  "NamesAsKeys" should "give name strings as keys" in {

    NamesAsKeys.keyFor(0, "HelloWorld") shouldBe "HelloWorld"
    NamesAsKeys.keyFor(0, "anotherMessage") shouldBe "anotherMessage"

  }

  it should "throw an IllegalArgumentException on empty name" in {

    the [IllegalArgumentException] thrownBy {
      NamesAsKeys.keyFor(0, "")
    } should have message "Name must not be empty"

  }

  "DotSeparatedKeyNames" should "convert the name to dot separated lower case strings" in {

    DotSeparatedKeyNames.keyFor(0, "HelloWorld") shouldBe "hello.world"
    DotSeparatedKeyNames.keyFor(0, "anotherMessage") shouldBe "another.message"
    DotSeparatedKeyNames.keyFor(0, "SomeMoreMessage2processWithThisStrategy") shouldBe "some.more.message2process.with.this.strategy"
    DotSeparatedKeyNames.keyFor(0, "WindowTitle100") shouldBe "window.title100"
    DotSeparatedKeyNames.keyFor(0, "textAreaLabel951") shouldBe "text.area.label951"

  }

  it should "throw an IllegalArgumentException on empty name" in {

    the [IllegalArgumentException] thrownBy {
      DotSeparatedKeyNames.keyFor(0, "")
    } should have message "Name must not be empty"

  }

}
