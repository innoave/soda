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

    import syntax._
    import DemoMessages._

    Greeting("Frank") //needed to avoid unused import warning, just for this test

    """Greeting(25)""" shouldNot typeCheck
    """Greeting("Frank")""" should compile

    """ProductsInShoppingCart("Frank", "7")""" shouldNot typeCheck
    """ProductsInShoppingCart("Frank", 7)""" should compile

  }

  "Applying the wrong number of arguments to a message" should "not compile with type error" in {

    import syntax._
    import DemoMessages._

    Greeting("Frank") //needed to avoid unused import warning, just for this test

    """HelloWorld("Frank")""" shouldNot typeCheck
    """HelloWorld()""" should compile

    """Greeting()""" shouldNot typeCheck
    """Greeting("Paul", "Hello")""" shouldNot typeCheck
    """Greeting("Paul")""" should compile

    """ProductsInShoppingCart("Frank")""" shouldNot typeCheck
    """ProductsInShoppingCart("Frank", 3, "total")""" shouldNot typeCheck
    """ProductsInShoppingCart("Frank", 1)""" should compile

  }

  "A message with localized type as argument" should "render as local text in different languages" in {

    import syntax._
    import Locale._
    import DemoMessages._
    import DemoTypes._
    import DemoTypesLocalizer._

    GreetingPlayCard(Card(Diamonds, King)) in EN shouldBe LocalText("Greetings to King of Diamonds")
    GreetingPlayCard(Card(Diamonds, King)) in EN_GB shouldBe LocalText("Dear King of Diamonds")
    GreetingPlayCard(Card(Diamonds, King)) in DE shouldBe LocalText("Guten Tag Karo König")
    GreetingPlayCard(Card(Diamonds, King)) in DE_AT shouldBe LocalText("Meine Verehrung Karo König")

  }

}
