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
import org.scalatest.BeforeAndAfterAll

class RenderMessageSpec extends FlatSpec with Matchers with BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    //set default Locale to language for which there is no resource file available
    //only needed for tests!!!
    Locale.default = Locale("XX")
  }

  "RenderLocalized" should "render the test message with no parameters in different languages" in {

    import syntax._
    import Locale._
    import DemoMessages._

    implicit val implicitLocale = EN
    helloWorld().asLocaleText shouldBe LocaleText("Hello World!")

    helloWorld().asLocaleText(EN_GB) shouldBe LocaleText("Good day World!")

    helloWorld().in(DE) shouldBe LocaleText("Hallo Welt!")

    helloWorld() in DE_AT shouldBe LocaleText("Servus Welt!")

    helloWorld() in(Locale("MM")) shouldBe LocaleText("Hello World!")

  }

  it should "render the test message with 1 parameter in different languages" in {

    import syntax._
    import Locale._
    import DemoMessages._

    implicit val implicitLocale = EN
    greeting("Frank").asLocaleText shouldBe LocaleText("Greetings to Frank")

    greeting("Frank").in(EN_GB) shouldBe LocaleText("Dear Frank")

    greeting("Frank") in DE shouldBe LocaleText("Guten Tag Frank")

    greeting("Frank") in(DE_AT) shouldBe LocaleText("Grüß Gott Frank")

    greeting("Frank").asLocaleText(Locale("MM")) shouldBe LocaleText("Hello Frank")

  }

  it should "render the test message with 2 parameter in different languages" in {

    import syntax._
    import Locale._
    import DemoMessages._

    implicit val implicitLocale = EN
    productsInShoppingCart("Paul", 0).asLocaleText shouldBe LocaleText("Paul has no items in the cart.")

    productsInShoppingCart("Paul", 0) in EN_GB shouldBe LocaleText("Paul has no products in the shopping cart.")

    productsInShoppingCart("Paul", 0).in(DE) shouldBe LocaleText("Paul hat keine Produkte im Einkaufskorb.")

    productsInShoppingCart("Paul", 0) in(DE_AT) shouldBe LocaleText("Paul hat keine Produkte im Einkaufskörberl.")

    productsInShoppingCart("Paul", 0).in(Locale("MM")) shouldBe LocaleText("Paul has no products in the cart.")

  }

  it should "render the test message with choice type for different values" in {

    import syntax._
    import DemoMessages._
    implicit val implicitLocale = Locale.EN

    productsInShoppingCart("Paul", 0).asLocaleText shouldBe LocaleText("Paul has no items in the cart.")

    productsInShoppingCart("Paul", 1).asLocaleText shouldBe LocaleText("Paul has one item in the cart.")

    productsInShoppingCart("Paul", 2).asLocaleText shouldBe LocaleText("Paul has 2 items in the cart.")

    productsInShoppingCart("Paul", 3).asLocaleText shouldBe LocaleText("Paul has 3 items in the cart.")

  }

}
