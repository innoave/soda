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

    Language("fr") shouldBe Language.fr

  }

  "Country" should "be defined by given ISO code" in {

    Country("US").code shouldBe "US"
    Country("CH").code shouldBe "CH"

  }

  it should "be equal when its ISO code is equal" in {

    Country("US") == Country("US") shouldBe true
    Country("CH") == Country("CH") shouldBe true
    Country("US") == Country("UN") shouldBe false

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

  }

  "Variant" should "be defined by given code" in {

    Variant("polyton").code shouldBe "polyton"
    Variant("traditional").code shouldBe "traditional"

  }

  it should "be equal when its code is equal" in {

    Variant("polyton") == Variant("polyton") shouldBe true
    Variant("traditional") == Variant("traditional") shouldBe true
    Variant("polyton") == Variant("traditional") shouldBe false

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

  }

  "Locale" should "be defined by given ISO code for language" in {

    val locale = Locale.of("en")

    locale.language shouldBe Language("en")
    locale.country  shouldBe Country.Any
    locale.variant  shouldBe Variant.Any

  }

  it should "be defined by given ISO code for language and country" in {

    val locale = Locale.of("en", "US")

    locale.language shouldBe Language("en")
    locale.country  shouldBe Country("US")
    locale.variant  shouldBe Variant.Any

  }

  it should "be defined by given ISO code for language and country and a variant" in {

    val locale = Locale.of("pt", "BR", "polyton")

    locale.language shouldBe Language("pt")
    locale.country  shouldBe Country("BR")
    locale.variant  shouldBe Variant("polyton")

  }

  it should "be defined by given language" in {

    val locale = Locale(Language.it)

    locale.language shouldBe Language.it
    locale.country shouldBe Country.Any
    locale.variant shouldBe Variant.Any

  }

  it should "be defined by given language and country" in {

    val locale = Locale(Language.es, Country.US)

    locale.language shouldBe Language.es
    locale.country shouldBe Country.US
    locale.variant shouldBe Variant.Any

  }

  it should "be defined by given language, country and variant" in {

    val locale = Locale(Language.en, Country.AU, Variant.Any)

    locale.language shouldBe Language.en
    locale.country shouldBe Country.AU
    locale.variant shouldBe Variant.Any

  }

  it should "be constructed from a language tag" in {

    val locale1 = Locale.forLanguageTag("zh-TW")

    locale1.language shouldBe Language.zh
    locale1.country shouldBe Country.TW
    locale1.variant shouldBe Variant.Any

    locale1 shouldBe Locale.zh_TW

    val locale2 = Locale.forLanguageTag("de-AT")

    locale2.language shouldBe Language.de
    locale2.country shouldBe Country.AT
    locale2.variant shouldBe Variant.Any

    locale2 shouldBe Locale.de_AT

  }

  it should "be constructed from java.util.Locale" in {

    val locale1 = Locale.fromJavaLocale(java.util.Locale.ENGLISH)
    locale1.language shouldBe Language.en
    locale1.country shouldBe Country.Any
    locale1.variant shouldBe Variant.Any

    val locale2 = Locale.fromJavaLocale(new java.util.Locale("zh", "SG"))
    locale2.language shouldBe Language.zh
    locale2.country shouldBe Country.SG
    locale2.variant shouldBe Variant.Any

    val locale3 = Locale.fromJavaLocale(new java.util.Locale("pt", "BR", "polyton"))
    locale3.language shouldBe Language.pt
    locale3.country shouldBe Country.BR
    locale3.variant shouldBe Variant("polyton")

  }

  it should "allow an empty language, empty country and empty variant" in {

    val locale = Locale.of("", "", "")

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

    Locale(Language("en")) == Locale(Language("en")) shouldBe true
    Locale(Language("en")) == Locale(Language("es")) shouldBe false

    Locale(Language("en")) == Locale(Language.en) shouldBe true
    Locale(Language("en")) == Locale(Language.es) shouldBe false

    Locale(Language.en, Country.US) == Locale(Language("en"), Country("US")) shouldBe true
    Locale(Language.en, Country.US) == Locale(Language.es, Country.US) shouldBe false
    Locale(Language("en"), Country("US")) == Locale(Language("en"), Country("GB")) shouldBe false

    Locale(Language("pt"), Country("BR"), Variant("polyton")) == Locale(Language.pt, Country.BR, Variant("polyton")) shouldBe true

    Locale(Language("pt"), Country("BR"), Variant("polyton")) == Locale(Language("es"), Country("BR"), Variant("polyton")) shouldBe false
    Locale(Language("pt"), Country("BR"), Variant("polyton")) == Locale(Language("pt"), Country("PT"), Variant("polyton")) shouldBe false
    Locale(Language("pt"), Country("BR"), Variant("polyton")) == Locale(Language("pt"), Country("BR"), Variant("")) shouldBe false

    Locale(Language("en")) == new java.util.Locale("en") shouldBe false

  }

  it should "be compared by the order of the code" in {

    Locale.en compare Locale.es should be < 0
    Locale.en compare Locale.de should be > 0
    Locale.en compare Locale.en shouldBe 0

    Locale.pt_BR compare Locale.pt_PT should be < 0
    Locale.pt_PT compare Locale.pt_BR should be > 0
    Locale.de_AT compare Locale.de_AT shouldBe 0

    Locale(Language.pt, Country.BR, Variant.Any) compare Locale(Language.pt, Country.BR, Variant("polyton")) should be < 0
    Locale(Language.pt, Country.BR, Variant("polyton")) compare Locale(Language.pt, Country.BR, Variant.Any) should be > 0
    Locale(Language.pt, Country.BR, Variant("polyton")) compare Locale(Language.pt, Country.BR, Variant("polyton")) shouldBe 0

    Locale(Language("pt"), Country("BR"), Variant("polyton")) compare Locale(Language("pt"), Country("PT"), Variant("")) should be < 0
    Locale(Language("pt"), Country("PT"), Variant("")) compare Locale(Language("pt"), Country("BR"), Variant("polyton")) should be > 0

    Locale(Language.Any, Country.PT, Variant("polyton")) compare Locale(Language.pt, Country.BR, Variant.Any) should be < 0
    Locale(Language.pt, Country.BR, Variant.Any) compare Locale(Language.Any, Country.PT, Variant("polyton")) should be > 0

  }

  it should "return a human readable string on #toString" in {

    Locale.en.toString shouldBe "Locale(Language(en), Country(), Variant())"
    Locale.pt_PT.toString shouldBe "Locale(Language(pt), Country(PT), Variant())"
    Locale(Language.pt, Country.BR, Variant("polyton")).toString shouldBe "Locale(Language(pt), Country(BR), Variant(polyton))"

  }

  it should "be equal with predefined constant values" in {

    Locale(Language("fr")) shouldBe Locale.fr
    Locale(Language("fr")) should be theSameInstanceAs Locale.fr

    Locale(Language("de"), Country("CH")) shouldBe Locale.de_CH
    Locale(Language("de"), Country("CH")) should be theSameInstanceAs Locale.de_CH

  }

  "Locale#asLanguageTag" should "return the language tag of the locale" in {

    Locale.en.asLanguageTag shouldBe "en"
    Locale.de_CH.asLanguageTag shouldBe "de-CH"
    Locale.zh_SG.asLanguageTag shouldBe "zh-SG"

    Locale(Language.pt, Country.BR, Variant("polyton")).asLanguageTag shouldBe "pt-BR-polyton"

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

    Locale.default = Locale.de_AT

    val locale = Locale.fr_BE

    locale.displayName() shouldBe "Französisch (Belgien)"
    locale.displayLanguage() shouldBe "Französisch"
    locale.displayCountry() shouldBe "Belgien"
    locale.displayVariant() shouldBe ""

    locale.displayName(Locale.en_AU) shouldBe "French (Belgium)"
    locale.displayLanguage(Locale.fr_CA) shouldBe "français"
    locale.displayCountry(Locale.fr_CA) shouldBe "Belgique"
    locale.displayVariant(Locale.fr_CA) shouldBe ""

  }

  "A Locale" should "return the ISO3 language code and ISO3 country code" in {

    val locale = Locale.fr_CH

    locale.iso3Language() shouldBe "fra"
    locale.iso3Country() shouldBe "CHE"

  }

}
