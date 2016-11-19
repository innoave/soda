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
import java.util.Date
import java.util.TimeZone
import java.util.Calendar

class RenderMessageSpec extends FlatSpec with Matchers with BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    //set default Locale to language for which there is no resource file available
    //only needed for tests!!!
    Locale.default = Locale("XX")
  }

  def date(year: Int, month: Int, day: Int, hour: Int, minute: Int): Date = {
    val cal = Calendar.getInstance()
    cal.setTimeZone(TimeZone.getTimeZone("UTC"))
    cal.set(Calendar.YEAR, year)
    cal.set(Calendar.MONTH, month - 1)
    cal.set(Calendar.DAY_OF_MONTH, day)
    cal.set(Calendar.HOUR_OF_DAY, hour)
    cal.set(Calendar.MINUTE, minute)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    cal.getTime()
  }



  "RenderLocalized" should "render the test message with no parameters in different languages" in {

    import syntax._
    import Locale._
    import DemoMessages._

    implicit val implicitLocale = EN
    HelloWorld().asLocalText shouldBe LocalText("Hello World!")

    HelloWorld().asLocalText(EN_GB) shouldBe LocalText("Good day World!")

    HelloWorld().in(DE) shouldBe LocalText("Hallo Welt!")

    HelloWorld() in DE_AT shouldBe LocalText("Servus Welt!")

    HelloWorld() in(Locale("MM")) shouldBe LocalText("Hello World!")

  }

  it should "render the test message with 1 parameter in different languages" in {

    import syntax._
    import Locale._
    import DemoMessages._

    implicit val implicitLocale = EN
    Greeting("Frank").asLocalText shouldBe LocalText("Greetings to Frank")

    Greeting("Frank").in(EN_GB) shouldBe LocalText("Dear Frank")

    Greeting("Frank") in DE shouldBe LocalText("Guten Tag Frank")

    Greeting("Frank") in(DE_AT) shouldBe LocalText("Grüß Gott Frank")

    Greeting("Frank").asLocalText(Locale("MM")) shouldBe LocalText("Hello Frank")

  }

  it should "render the test message with 2 parameter in different languages" in {

    import syntax._
    import Locale._
    import DemoMessages._

    implicit val implicitLocale = EN
    ProductsInShoppingCart("Paul", 0).asLocalText shouldBe LocalText("Paul has no items in the cart.")

    ProductsInShoppingCart("Paul", 0) in EN_GB shouldBe LocalText("Paul has no products in the shopping cart.")

    ProductsInShoppingCart("Paul", 0).in(DE) shouldBe LocalText("Paul hat keine Produkte im Einkaufskorb.")

    ProductsInShoppingCart("Paul", 0) in(DE_AT) shouldBe LocalText("Paul hat keine Produkte im Einkaufskörberl.")

    ProductsInShoppingCart("Paul", 0).in(Locale("MM")) shouldBe LocalText("Paul has no products in the cart.")

  }

  it should "render the test message with choice type for different values" in {

    import syntax._
    import DemoMessages._

    implicit val implicitLocale = Locale.EN
    ProductsInShoppingCart("Paul", 0).asLocalText shouldBe LocalText("Paul has no items in the cart.")

    ProductsInShoppingCart("Paul", 1).asLocalText shouldBe LocalText("Paul has one item in the cart.")

    ProductsInShoppingCart("Paul", 2).asLocalText shouldBe LocalText("Paul has 2 items in the cart.")

    ProductsInShoppingCart("Paul", 3).asLocalText shouldBe LocalText("Paul has 3 items in the cart.")

  }

  "Messages with no arguments" should "render in the language for the given Locale" in {

    import syntax._
    import Locale._
    import AllPossibleMessageVariations._

    MyMessage0()        in EN shouldBe LocalText("My message without arguments")
    MyMessage0()        in DE shouldBe LocalText("Meine Nachricht ohne Argumente")

    CustomKeyMessage0() in EN shouldBe LocalText("Custom key message without arguments")
    CustomKeyMessage0() in DE shouldBe LocalText("Eigener Key Nachricht ohne Argumente")

    AllCustomMessage0() in EN shouldBe LocalText("All custom message without arguments")
    AllCustomMessage0() in DE shouldBe LocalText("Total angepasste Nachricht ohne Argumente")

  }

  "Messages with 1 argument" should "render in the language for the given Locale" in {

    import syntax._
    import Locale._
    import AllPossibleMessageVariations._

    MyMessage1(10L)        in EN shouldBe LocalText("My message with 1 argument: [ 10 ]")
    MyMessage1(10L)        in DE shouldBe LocalText("Meine Nachricht mit 1 Argument: [ 10 ]")

    CustomKeyMessage1(10L) in EN shouldBe LocalText("Custom key message with 1 argument: [ 10 ]")
    CustomKeyMessage1(10L) in DE shouldBe LocalText("Eigener Key Nachricht mit 1 Argument: [ 10 ]")

    AllCustomMessage1(10L) in EN shouldBe LocalText("All custom message with 1 argument: [ 10 ]")
    AllCustomMessage1(10L) in DE shouldBe LocalText("Total angepasste Nachricht mit 1 Argument: [ 10 ]")

  }

  "Messages with 2 argument" should "render in the language for the given Locale" in {

    import syntax._
    import Locale._
    import AllPossibleMessageVariations._

    MyMessage2("Lion", "King")        in EN shouldBe LocalText("My message with 2 arguments: [ Lion, King ]")
    MyMessage2("Lion", "King")        in DE shouldBe LocalText("Meine Nachricht mit 2 Argumenten: [ Lion, King ]")

    CustomKeyMessage2("Lion", "King") in EN shouldBe LocalText("Custom key message with 2 arguments: [ Lion, King ]")
    CustomKeyMessage2("Lion", "King") in DE shouldBe LocalText("Eigener Key Nachricht mit 2 Argumenten: [ Lion, King ]")

    AllCustomMessage2("Lion", "King") in EN shouldBe LocalText("All custom message with 2 arguments: [ Lion, King ]")
    AllCustomMessage2("Lion", "King") in DE shouldBe LocalText("Total angepasste Nachricht mit 2 Argumenten: [ Lion, King ]")

  }

  "Messages with 3 argument" should "render in the language for the given Locale" in {

    import syntax._
    import Locale._
    import AllPossibleMessageVariations._

    MyMessage3(30, BigDecimal(31.23), "total")        in EN shouldBe LocalText("My message with 3 arguments: [ 30, 31.23, total ]")
    MyMessage3(30, BigDecimal(31.23), "total")        in DE shouldBe LocalText("Meine Nachricht mit 3 Argumenten: [ 30, 31,23, total ]")

    CustomKeyMessage3(30, BigDecimal(31.23), "total") in EN shouldBe LocalText("Custom key message with 3 arguments: [ 30, 31.23, total ]")
    CustomKeyMessage3(30, BigDecimal(31.23), "total") in DE shouldBe LocalText("Eigener Key Nachricht mit 3 Argumenten: [ 30, 31,23, total ]")

    AllCustomMessage3(30, BigDecimal(31.23), "total") in EN shouldBe LocalText("All custom message with 3 arguments: [ 30, 31.23, total ]")
    AllCustomMessage3(30, BigDecimal(31.23), "total") in DE shouldBe LocalText("Total angepasste Nachricht mit 3 Argumenten: [ 30, 31,23, total ]")

  }

  "Messages with 4 argument" should "render in the language for the given Locale" in {

    import syntax._
    import Locale._
    import AllPossibleMessageVariations._

    MyMessage4("Pos", 40, date(2016, 11, 18, 0, 0), "midnight")        in EN shouldBe LocalText("My message with 4 arguments: [ Pos, 40, Nov 18, 2016, midnight ]")
    MyMessage4("Pos", 40, date(2016, 11, 18, 0, 0), "midnight")        in DE shouldBe LocalText("Meine Nachricht mit 4 Argumenten: [ Pos, 40, 18.11.2016, midnight ]")

    CustomKeyMessage4("Pos", 40, date(2016, 11, 18, 0, 0), "midnight") in EN shouldBe LocalText("Custom key message with 4 arguments: [ Pos, 40, Nov 18, 2016, midnight ]")
    CustomKeyMessage4("Pos", 40, date(2016, 11, 18, 0, 0), "midnight") in DE shouldBe LocalText("Eigener Key Nachricht mit 4 Argumenten: [ Pos, 40, 18.11.2016, midnight ]")

    AllCustomMessage4("Pos", 40, date(2016, 11, 18, 0, 0), "midnight") in EN shouldBe LocalText("All custom message with 4 arguments: [ Pos, 40, Nov 18, 2016, midnight ]")
    AllCustomMessage4("Pos", 40, date(2016, 11, 18, 0, 0), "midnight") in DE shouldBe LocalText("Total angepasste Nachricht mit 4 Argumenten: [ Pos, 40, 18.11.2016, midnight ]")

  }

  "Messages with 5 argument" should "render in the language for the given Locale" in {

    import syntax._
    import Locale._
    import AllPossibleMessageVariations._

    MyMessage5(50, 51, 52, 53, 54)        in EN shouldBe LocalText("My message with 5 arguments: [ 50, 51, 52, 53, 54 ]")
    MyMessage5(50, 51, 52, 53, 54)        in DE shouldBe LocalText("Meine Nachricht mit 5 Argumenten: [ 50, 51, 52, 53, 54 ]")

    CustomKeyMessage5(50, 51, 52, 53, 54) in EN shouldBe LocalText("Custom key message with 5 arguments: [ 50, 51, 52, 53, 54 ]")
    CustomKeyMessage5(50, 51, 52, 53, 54) in DE shouldBe LocalText("Eigener Key Nachricht mit 5 Argumenten: [ 50, 51, 52, 53, 54 ]")

    AllCustomMessage5(50, 51, 52, 53, 54) in EN shouldBe LocalText("All custom message with 5 arguments: [ 50, 51, 52, 53, 54 ]")
    AllCustomMessage5(50, 51, 52, 53, 54) in DE shouldBe LocalText("Total angepasste Nachricht mit 5 Argumenten: [ 50, 51, 52, 53, 54 ]")

  }

  "Messages with 6 argument" should "render in the language for the given Locale" in {

    import syntax._
    import Locale._
    import AllPossibleMessageVariations._

    MyMessage6(60L, "record", 61, 62, "first", "last")        in EN shouldBe LocalText("My message with 6 arguments: [ 60, record, 61, 62, first, last ]")
    MyMessage6(60L, "record", 61, 62, "first", "last")        in DE shouldBe LocalText("Meine Nachricht mit 6 Argumenten: [ 60, record, 61, 62, first, last ]")

    CustomKeyMessage6(60L, "record", 61, 62, "first", "last") in EN shouldBe LocalText("Custom key message with 6 arguments: [ 60, record, 61, 62, first, last ]")
    CustomKeyMessage6(60L, "record", 61, 62, "first", "last") in DE shouldBe LocalText("Eigener Key Nachricht mit 6 Argumenten: [ 60, record, 61, 62, first, last ]")

    AllCustomMessage6(60L, "record", 61, 62, "first", "last") in EN shouldBe LocalText("All custom message with 6 arguments: [ 60, record, 61, 62, first, last ]")
    AllCustomMessage6(60L, "record", 61, 62, "first", "last") in DE shouldBe LocalText("Total angepasste Nachricht mit 6 Argumenten: [ 60, record, 61, 62, first, last ]")

  }

  "Messages with 7 argument" should "render in the language for the given Locale" in {

    import syntax._
    import Locale._
    import AllPossibleMessageVariations._

    MyMessage7(date(2016, 11, 17, 12, 59), 70L, "points", 71, 72, "round", "one")        in EN shouldBe LocalText("My message with 7 arguments: [ Nov 17, 2016, 70, points, 71, 72, round, one ]")
    MyMessage7(date(2016, 11, 17, 12, 59), 70L, "points", 71, 72, "round", "one")        in DE shouldBe LocalText("Meine Nachricht mit 7 Argumenten: [ 17.11.2016, 70, points, 71, 72, round, one ]")

    CustomKeyMessage7(date(2016, 11, 17, 12, 59), 70L, "points", 71, 72, "round", "one") in EN shouldBe LocalText("Custom key message with 7 arguments: [ Nov 17, 2016, 70, points, 71, 72, round, one ]")
    CustomKeyMessage7(date(2016, 11, 17, 12, 59), 70L, "points", 71, 72, "round", "one") in DE shouldBe LocalText("Eigener Key Nachricht mit 7 Argumenten: [ 17.11.2016, 70, points, 71, 72, round, one ]")

    AllCustomMessage7(date(2016, 11, 17, 12, 59), 70L, "points", 71, 72, "round", "one") in EN shouldBe LocalText("All custom message with 7 arguments: [ Nov 17, 2016, 70, points, 71, 72, round, one ]")
    AllCustomMessage7(date(2016, 11, 17, 12, 59), 70L, "points", 71, 72, "round", "one") in DE shouldBe LocalText("Total angepasste Nachricht mit 7 Argumenten: [ 17.11.2016, 70, points, 71, 72, round, one ]")

  }

  "Messages with 8 argument" should "render in the language for the given Locale" in {

    import syntax._
    import Locale._
    import AllPossibleMessageVariations._

    MyMessage8(80L, 81L, 82L, 83L, 84L, 85L, 86L, 87L)        in EN shouldBe LocalText("My message with 8 arguments: [ 80, 81, 82, 83, 84, 85, 86, 87 ]")
    MyMessage8(80L, 81L, 82L, 83L, 84L, 85L, 86L, 87L)        in DE shouldBe LocalText("Meine Nachricht mit 8 Argumenten: [ 80, 81, 82, 83, 84, 85, 86, 87 ]")

    CustomKeyMessage8(80L, 81L, 82L, 83L, 84L, 85L, 86L, 87L) in EN shouldBe LocalText("Custom key message with 8 arguments: [ 80, 81, 82, 83, 84, 85, 86, 87 ]")
    CustomKeyMessage8(80L, 81L, 82L, 83L, 84L, 85L, 86L, 87L) in DE shouldBe LocalText("Eigener Key Nachricht mit 8 Argumenten: [ 80, 81, 82, 83, 84, 85, 86, 87 ]")

    AllCustomMessage8(80L, 81L, 82L, 83L, 84L, 85L, 86L, 87L) in EN shouldBe LocalText("All custom message with 8 arguments: [ 80, 81, 82, 83, 84, 85, 86, 87 ]")
    AllCustomMessage8(80L, 81L, 82L, 83L, 84L, 85L, 86L, 87L) in DE shouldBe LocalText("Total angepasste Nachricht mit 8 Argumenten: [ 80, 81, 82, 83, 84, 85, 86, 87 ]")

  }

  "Messages with 9 argument" should "render in the language for the given Locale" in {

    import syntax._
    import Locale._
    import AllPossibleMessageVariations._

    MyMessage9(90, 91, 92, 93, 94, 95, 96, 97, 98)        in EN shouldBe LocalText("My message with 9 arguments: [ 90, 91, 92, 93, 94, 95, 96, 97, 98 ]")
    MyMessage9(90, 91, 92, 93, 94, 95, 96, 97, 98)        in DE shouldBe LocalText("Meine Nachricht mit 9 Argumenten: [ 90, 91, 92, 93, 94, 95, 96, 97, 98 ]")

    CustomKeyMessage9(90, 91, 92, 93, 94, 95, 96, 97, 98) in EN shouldBe LocalText("Custom key message with 9 arguments: [ 90, 91, 92, 93, 94, 95, 96, 97, 98 ]")
    CustomKeyMessage9(90, 91, 92, 93, 94, 95, 96, 97, 98) in DE shouldBe LocalText("Eigener Key Nachricht mit 9 Argumenten: [ 90, 91, 92, 93, 94, 95, 96, 97, 98 ]")

    AllCustomMessage9(90, 91, 92, 93, 94, 95, 96, 97, 98) in EN shouldBe LocalText("All custom message with 9 arguments: [ 90, 91, 92, 93, 94, 95, 96, 97, 98 ]")
    AllCustomMessage9(90, 91, 92, 93, 94, 95, 96, 97, 98) in DE shouldBe LocalText("Total angepasste Nachricht mit 9 Argumenten: [ 90, 91, 92, 93, 94, 95, 96, 97, 98 ]")

  }

}
