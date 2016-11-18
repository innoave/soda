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

  it should "be equal when its ISO code is equal" in {

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

  it should "be equal with predefined constant value" in {

    Language("fr") shouldBe Language.FR
    Language("fr") should be theSameInstanceAs Language.FR

  }

  "Country" should "be defined by given ISO code" in {

    Country("US").code shouldBe "US"
    Country("CH").code shouldBe "CH"

  }

  it should "be equal when its ISO code is equal" in {

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

  it should "be equal with predefined constant value" in {

    Country("FR") shouldBe Country.FR
    Country("FR") should be theSameInstanceAs Country.FR

  }

  "Variant" should "be defined by given code" in {

    Variant("polyton").code shouldBe "polyton"
    Variant("traditional").code shouldBe "traditional"

  }

  it should "be equal when its code is equal" in {

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

  it should "be equal with predefined constant value" in {

    Variant("") shouldBe Variant.Any
    Variant("") should be theSameInstanceAs Variant.Any

  }

  "Locale" should "be defined by given ISO code for language" in {

    val locale = Locale("en")

    locale.language shouldBe Language("en")
    locale.country  shouldBe Country.Any
    locale.variant  shouldBe Variant.Any

  }

  it should "be defined by given ISO code for language and country" in {

    val locale = Locale("en", "US")

    locale.language shouldBe Language("en")
    locale.country  shouldBe Country("US")
    locale.variant  shouldBe Variant.Any

  }

  it should "be defined by given ISO code for language and country and a variant" in {

    val locale = Locale("pt", "BR", "polyton")

    locale.language shouldBe Language("pt")
    locale.country  shouldBe Country("BR")
    locale.variant  shouldBe Variant("polyton")

  }

  it should "be defined by given language" in {

    val locale = Locale(Language.IT)

    locale.language shouldBe Language.IT
    locale.country shouldBe Country.Any
    locale.variant shouldBe Variant.Any

  }

  it should "be defined by given language and country" in {

    val locale = Locale(Language.ES, Country.US)

    locale.language shouldBe Language.ES
    locale.country shouldBe Country.US
    locale.variant shouldBe Variant.Any

  }

  it should "be defined by given language, country and variant" in {

    val locale = Locale(Language.EN, Country.AU, Variant.Any)

    locale.language shouldBe Language.EN
    locale.country shouldBe Country.AU
    locale.variant shouldBe Variant.Any

  }

  it should "be constructed from a language tag" in {

    val locale1 = Locale.forLanguageTag("zh-TW")

    locale1.language shouldBe Language.ZH
    locale1.country shouldBe Country.TW
    locale1.variant shouldBe Variant.Any

    locale1 shouldBe Locale.ZH_TW

    val locale2 = Locale.forLanguageTag("de-AT")

    locale2.language shouldBe Language.DE
    locale2.country shouldBe Country.AT
    locale2.variant shouldBe Variant.Any

    locale2 shouldBe Locale.DE_AT

  }

  it should "be constructed from java.util.Locale" in {

    val locale1 = Locale.fromJavaLocale(java.util.Locale.ENGLISH)
    locale1.language shouldBe Language.EN
    locale1.country shouldBe Country.Any
    locale1.variant shouldBe Variant.Any

    val locale2 = Locale.fromJavaLocale(new java.util.Locale("zh", "SG"))
    locale2.language shouldBe Language.ZH
    locale2.country shouldBe Country.SG
    locale2.variant shouldBe Variant.Any

    val locale3 = Locale.fromJavaLocale(new java.util.Locale("pt", "BR", "polyton"))
    locale3.language shouldBe Language.PT
    locale3.country shouldBe Country.BR
    locale3.variant shouldBe Variant("polyton")

  }

  it should "allow an empty language, empty country and empty variant" in {

    val locale = Locale("", "", "")

    locale.language shouldBe Language.Any
    locale.country  shouldBe Country.Any
    locale.variant  shouldBe Variant.Any

  }

  it should "allow any language, any country and any variant" in {

    val locale = Locale(Language.Any, Country.Any, Variant.Any)

    locale.language shouldBe Language.Any
    locale.country  shouldBe Country.Any
    locale.variant  shouldBe Variant.Any

  }

  it should "be equal when language, country and variant are equal" in {

    Locale("en") == Locale("en") shouldBe true
    Locale("en") == Locale("es") shouldBe false

    Locale("en", "US") == Locale("en", "US") shouldBe true
    Locale("en", "US") == Locale("es", "US") shouldBe false
    Locale("en", "US") == Locale("en", "GB") shouldBe false

    Locale("pt", "BR", "polyton") == Locale("pt", "BR", "polyton") shouldBe true

    Locale("pt", "BR", "polyton") == Locale("es", "BR", "polyton") shouldBe false
    Locale("pt", "BR", "polyton") == Locale("pt", "PT", "polyton") shouldBe false
    Locale("pt", "BR", "polyton") == Locale("pt", "BR", "") shouldBe false

    Locale("en") == new java.util.Locale("en") shouldBe false

  }

  it should "be compared by the order of the code" in {

    Locale("en").compare(Locale("es")) should be < 0
    Locale("en").compare(Locale("de")) should be > 0
    Locale("en").compare(Locale("en")) shouldBe 0

    Locale("pt", "BR").compare(Locale("pt", "PT")) should be < 0
    Locale("pt", "PT").compare(Locale("pt", "BR")) should be > 0
    Locale("de", "AT").compare(Locale("de", "AT")) shouldBe 0

    Locale("pt", "BR", "").compare(Locale("pt", "BR", "polyton")) should be < 0
    Locale("pt", "BR", "polyton").compare(Locale("pt", "BR", "")) should be > 0
    Locale("pt", "BR", "polyton").compare(Locale("pt", "BR", "polyton")) shouldBe 0

    Locale("pt", "BR", "polyton").compare(Locale("pt", "PT", "")) should be < 0
    Locale("pt", "PT", "").compare(Locale("pt", "BR", "polyton")) should be > 0

    Locale("", "PT", "polyton").compare(Locale("pt", "BR", "")) should be < 0
    Locale("pt", "BR", "").compare(Locale("", "PT", "polyton")) should be > 0

  }

  it should "return a human readable string on #toString" in {

    Locale("en").toString shouldBe "Locale(Language(en), Country(), Variant())"
    Locale("pt", "PT").toString shouldBe "Locale(Language(pt), Country(PT), Variant())"
    Locale("pt", "BR", "polyton").toString shouldBe "Locale(Language(pt), Country(BR), Variant(polyton))"

  }

  it should "be equal with predefined constant values" in {

    Locale("fr") shouldBe Locale.FR
    Locale("fr") should be theSameInstanceAs Locale.FR

    Locale("de", "CH") shouldBe Locale.DE_CH
    Locale("de", "CH") should be theSameInstanceAs Locale.DE_CH

  }

  "Locale#asLanguageTag" should "return the language tag of the locale" in {

    Locale.EN.asLanguageTag shouldBe "en"
    Locale.DE_CH.asLanguageTag shouldBe "de-CH"
    Locale.ZH_SG.asLanguageTag shouldBe "zh-SG"

    Locale("pt", "BR", "polyton").asLanguageTag shouldBe "pt-BR-polyton"

  }

  "All available Locales" should "have different hash codes" in {

    val availableLocales = Locale.availableLocales().distinct

    val hashCodes = availableLocales.map(_.hashCode())
    val dupHashCodes = hashCodes.diff(hashCodes.distinct)

    availableLocales.filter(x => dupHashCodes.contains(x.hashCode())).map(x => (x, x.hashCode())) shouldBe empty

  }

  "All languages" should "match in code with all ISO codes" in {

    Locale.languages().map(_.code) shouldBe Locale.isoLanguages()

  }

  "All countries" should "match in code with all ISO countries" in {

    Locale.countries().map(_.code) shouldBe Locale.isoCountries()

  }

  "A Locale" should "display info in human readable form" in {

    Locale.default = Locale.DE_AT

    val locale = Locale.FR_BE

    locale.displayName() shouldBe "Französisch (Belgien)"
    locale.displayLanguage() shouldBe "Französisch"
    locale.displayCountry() shouldBe "Belgien"
    locale.displayVariant() shouldBe ""

    locale.displayName(Locale.EN_AU) shouldBe "French (Belgium)"
    locale.displayLanguage(Locale.FR_CA) shouldBe "français"
    locale.displayCountry(Locale.FR_CA) shouldBe "Belgique"
    locale.displayVariant(Locale.FR_CA) shouldBe ""

  }

  "A Locale" should "return the ISO3 language code and ISO3 country code" in {

    val locale = Locale.FR_CH

    locale.iso3Language() shouldBe "fra"
    locale.iso3Country() shouldBe "CHE"

  }

}
