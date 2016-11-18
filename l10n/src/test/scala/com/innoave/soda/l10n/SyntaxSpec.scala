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

class SyntaxSpec extends FlatSpec with Matchers {

  "Applying an argument of wrong type to a message" should "not compile with type error" in {

    import DemoMessages._

    """greeting(25)""" shouldNot typeCheck

    """productsInShoppingCart("Frank", "7")""" shouldNot typeCheck

  }

  "Applying the wrong number of arguments to a message" should "not compile with type error" in {

    import DemoMessages._

    """helloWorld("Frank")""" shouldNot typeCheck

    """greeting()""" shouldNot typeCheck

    """productsInShoppingCart("Frank")""" shouldNot typeCheck

    """productsInShoppingCart("Frank", 3, "total")""" shouldNot typeCheck

  }

  "A message with localized type as argument" should "render as local text in different languages" in {

    import syntax._
    import Locale._
    import DemoMessages._
    import DemoTypes._
    import DemoTypesLocalizer._

    greetingsToCard(Card(Diamonds, King)) in EN shouldBe LocalText("Greetings to King of Diamonds")
    greetingsToCard(Card(Diamonds, King)) in EN_GB shouldBe LocalText("Dear King of Diamonds")
    greetingsToCard(Card(Diamonds, King)) in DE shouldBe LocalText("Guten Tag Karo König")
    greetingsToCard(Card(Diamonds, King)) in DE_AT shouldBe LocalText("Meine Verehrung Karo König")

  }

}
