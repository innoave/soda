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
import ResourceBundleRenderMessage.render

class RenderMessageSpec extends FlatSpec with Matchers {

  "RenderMessage" should "render the test message with no parameters in different languages" in {

    import Locale._
    import TestMessages._

    render(helloWorld)(EN) shouldBe LocaleText("Hello World!")

    render(helloWorld)(EN_GB) shouldBe LocaleText("Good day World!")

    render(helloWorld)(DE) shouldBe LocaleText("Hallo Welt!")

    render(helloWorld)(DE_AT) shouldBe LocaleText("Servus Welt!")

  }

  it should "render the test message with 1 parameter in different languages" in {

    import Locale._
    import TestMessages._

    render(greeting, "Frank")(EN) shouldBe LocaleText("Greetings to Frank")

    render(greeting, "Frank")(EN_GB) shouldBe LocaleText("Dear Frank")

    render(greeting, "Frank")(DE) shouldBe LocaleText("Guten Tag Frank")

    render(greeting, "Frank")(DE_AT) shouldBe LocaleText("Grüß Gott Frank")

  }

  it should "render the test message with 2 parameter in different languages" in {

    import Locale._
    import TestMessages._

    render(productsInShoppingCart, "Paul", 0)(EN) shouldBe LocaleText("Paul has no items in the cart.")

    render(productsInShoppingCart, "Paul", 0)(EN_GB) shouldBe LocaleText("Paul has no products in the shopping cart.")

    render(productsInShoppingCart, "Paul", 0)(DE) shouldBe LocaleText("Paul hat keine Produkte im Einkaufskorb.")

    render(productsInShoppingCart, "Paul", 0)(DE_AT) shouldBe LocaleText("Paul hat keine Produkte im Einkaufskörberl.")

  }

  it should "render the test message with choice type for different values" in {

    import TestMessages._
    implicit val locale = Locale.EN

    render(productsInShoppingCart, "Paul", 0) shouldBe LocaleText("Paul has no items in the cart.")

    render(productsInShoppingCart, "Paul", 1) shouldBe LocaleText("Paul has one item in the cart.")

    render(productsInShoppingCart, "Paul", 2) shouldBe LocaleText("Paul has 2 items in the cart.")

    render(productsInShoppingCart, "Paul", 3) shouldBe LocaleText("Paul has 3 items in the cart.")

  }

}
