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

class LocaleSpec extends FlatSpec with Matchers {

  "Language" should "be defined by given ISO code" in {

    Language("en").code shouldBe "en"
    Language("pt").code shouldBe "pt"

  }

  it should "be equal when ISO code is equal" in {

    Language("en") == Language("en") shouldBe true
    Language("pt") == Language("pt") shouldBe true
    Language("en") == Language("es") shouldBe false
    Language("en") == Country("en") shouldBe false

  }

  it should "be compared by the order of the code" in {

    Language("en").compare(Language("pt")) should be < 0
    Language("en").compare(Language("de")) should be > 0
    Language("en").compare(Language("es")) should be < 0
    Language("en").compare(Language("en")) shouldBe 0

  }

  it should "return a human readable string on #toString" in {

    Language("en").toString shouldBe "Language(en)"

  }

  "Country" should "be defined by given ISO code" in {

    Country("US").code shouldBe "US"
    Country("CH").code shouldBe "CH"

  }

  it should "be equal when ISO code is equal" in {

    Country("US") == Country("US") shouldBe true
    Country("CH") == Country("CH") shouldBe true
    Country("US") == Country("UN") shouldBe false
    Country("US") == Language("US") shouldBe false

  }

  it should "be compared by the order of the code" in {

    Country("DE").compare(Country("FR")) should be < 0
    Country("DE").compare(Country("CH")) should be > 0
    Country("DE").compare(Country("DE")) shouldBe 0

  }

  it should "return a human readable string on #toString" in {

    Country("US").toString shouldBe "Country(US)"

  }

  "Variant" should "be defined by given code" in {

    Variant("polyton").code shouldBe "polyton"
    Variant("traditional").code shouldBe "traditional"

  }

  it should "be equal when ISO code is equal" in {

    Variant("polyton") == Variant("polyton") shouldBe true
    Variant("traditional") == Variant("traditional") shouldBe true
    Variant("polyton") == Variant("traditional") shouldBe false
    Variant("polyton") == Country("polyton") shouldBe false

  }

  it should "be compared by the order of the code" in {

    Variant("polyton").compare(Variant("traditional")) should be < 0
    Variant("traditional").compare(Variant("polyton")) should be > 0
    Variant("polyton").compare(Variant("polyton")) shouldBe 0

  }

  it should "return a human readable string on #toString" in {

    Variant("polyton").toString shouldBe "Variant(polyton)"

  }

}
