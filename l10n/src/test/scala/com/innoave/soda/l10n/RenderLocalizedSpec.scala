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
import DemoTypes._
import org.scalatest.BeforeAndAfterAll

class RenderLocalizedSpec extends FlatSpec with Matchers with BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    //set default Locale to language for which there is no resource file available
    //only needed for tests!!!
    Locale.default = Locale("XX")
  }

  "RenderLocalized" should "render a text for a case class in different languages" in {

    import ResourceBundleRenderLocalized._
    import Locale._
    import DemoTypesLocalizer._

    val queenOfHearts = Card(Hearts, Queen)

    renderLocalized(queenOfHearts)(EN) shouldBe LocaleText("Queen of Hearts")

    renderLocalized(queenOfHearts)(DE_AT) shouldBe LocaleText("Herz Dame")

    renderLocalized(queenOfHearts)(Locale("MM")) shouldBe LocaleText("Queen of Hearts")

  }

  it should "render a text for a case object in different languages" in {

    import ResourceBundleRenderLocalized._
    import Locale._
    import DemoTypesLocalizer._

    renderLocalized(King)(EN) shouldBe LocaleText("King")

    renderLocalized(King)(DE_AT) shouldBe LocaleText("König")

    renderLocalized(King)(Locale("MM")) shouldBe LocaleText("King")

  }

}
