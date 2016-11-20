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

class RenderLocalizedSpec extends FlatSpec with Matchers with BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    //set default Locale to language for which there is no resource file available
    //only needed for tests!!!
    Locale.default = Locale("aa")
  }

  "RenderLocalized" should "render a text for a case class in different languages" in {

    import syntax._
    import Locale._
    import DemoTypes._
    import DemoTypesLocalizer._

    val queenOfHearts = Card(Hearts, Queen)

    implicit val implicitLocale = EN
    render(queenOfHearts).asLocalText shouldBe LocalText("Queen of Hearts")

    render(queenOfHearts).in(DE_AT) shouldBe LocalText("Herz Dame")

    render(queenOfHearts) in Locale("zu") shouldBe LocalText("['Queen' of 'Hearts']")

  }

  it should "render a text for a case object in different languages" in {

    import syntax._
    import Locale._
    import DemoTypes._
    import DemoTypesLocalizer._

    implicit val implicitLocale = EN
    render(King).asLocalText shouldBe LocalText("King")

    render(King) in DE_AT shouldBe LocalText("König")

    render(King).in(Locale("zu")) shouldBe LocalText("'King'")

  }

  it should "render a text for a case class with case class arguments in different languages" in {

    import syntax._
    import Locale._
    import DemoTypes._
    import DemoTypesLocalizer._

    val playedCard = PlayedCard(Player("Frank"), Card(Clubs, King))

    implicit val implicitLocale = EN
    render(playedCard).asLocalText shouldBe LocalText("Player Frank played King of Clubs")

    render(playedCard).in(DE) shouldBe LocalText("Spieler Frank hat Pik König ausgespielt")

    render(playedCard) in Locale("zu") shouldBe LocalText("['Player Frank' played ['King' of 'Clubs']]")

  }

  it should "render a text for a sequence of case class as argument in different languages" in {

    import syntax._
    import Locale._
    import DemoTypes._
    import DemoTypesLocalizer._

    val cards = List(Card(Diamonds, King), Card(Clubs, Ten), Card(Hearts, Ace), Card(Spades, Jack))

    implicit val implicitLocale = EN
    render(cards).asLocalText shouldBe LocalText("King of Diamonds, Ten of Clubs, Ace of Hearts, Jack of Spades")

    render(cards) in DE shouldBe LocalText("Karo König, Pik Zehn, Herz Ass, Kreuz Bube")

    render(cards) in Locale("zu") shouldBe LocalText("['King' of 'Diamonds'], ['Ten' of 'Clubs'], ['Ace' of 'Hearts'], ['Jack' of 'Spades']")

  }

  it should "render a text for a sequence of case objects as argument in different languages" in {

    import syntax._
    import Locale._
    import DemoTypes._
    import DemoTypesLocalizer._

    val faces = List(Ten, Jack, Queen, King, Ace)

    implicit val implicitLocale = EN
    render(faces).asLocalText shouldBe LocalText("Ten, Jack, Queen, King, Ace")

    render(faces).in(DE) shouldBe LocalText("Zehn, Bube, Dame, König, Ass")

    render(faces).in(Locale("zu")) shouldBe LocalText("'Ten', 'Jack', 'Queen', 'King', 'Ace'")

  }

}
