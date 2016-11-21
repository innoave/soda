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

import java.util.{Locale => JLocale}

final class Locale private[Locale](val asJavaLocale: JLocale) extends AnyVal with Equals with Ordered[Locale] {

  def language(): Language =
    Language(asJavaLocale.getLanguage)

  def country(): Country =
    Country(asJavaLocale.getCountry)

  def variant(): Variant =
    Variant(asJavaLocale.getVariant)

  override def toString(): String =
    s"Locale($language, $country, $variant)"

  override def compare(that: Locale): Int = {
    val languageCompared = this.language compare that.language
    if (languageCompared == 0) {
      val countryCompared = this.country compare that.country
      if (countryCompared == 0) {
        this.variant compare that.variant
      } else {
        countryCompared
      }
    } else {
      languageCompared
    }
  }

  override def canEqual(other: Any): Boolean =
    other.isInstanceOf[Locale]

  def asLanguageTag(): String =
    asJavaLocale.toLanguageTag

  def displayName(): String =
    asJavaLocale.getDisplayName

  def displayName(inLocale: Locale): String =
    asJavaLocale.getDisplayName(inLocale.asJavaLocale)

  def displayLanguage(): String =
    asJavaLocale.getDisplayLanguage

  def displayLanguage(inLocale: Locale): String =
    asJavaLocale.getDisplayLanguage(inLocale.asJavaLocale)

  def displayCountry(): String =
    asJavaLocale.getDisplayCountry

  def displayCountry(inLocale: Locale): String =
    asJavaLocale.getDisplayCountry(inLocale.asJavaLocale)

  def displayVariant(): String =
    asJavaLocale.getDisplayVariant

  def displayVariant(inLocale: Locale): String =
    asJavaLocale.getDisplayVariant(inLocale.asJavaLocale)

  def script(): String =
    asJavaLocale.getScript

  def displayScript(): String =
    asJavaLocale.getDisplayScript

  def displayScript(inLocale: Locale): String =
    asJavaLocale.getDisplayScript(inLocale.asJavaLocale)

  def iso3Language(): String =
    asJavaLocale.getISO3Language

  def iso3Country(): String =
    asJavaLocale.getISO3Country

}

object LocaleOrdering extends Ordering[Locale] {

  override def compare(x: Locale, y: Locale): Int =
    x compare y

}

object Locale extends PredefinedLocales {

  def apply(language: Language): Locale =
    new Locale(new JLocale(language.code))

  def apply(language: Language, country: Country): Locale =
    new Locale(new JLocale(language.code, country.code))

  def apply(language: Language, country: Country, variant: Variant): Locale =
    new Locale(new JLocale(language.code, country.code, variant.code))

  def unapply(locale: Locale): Option[(Language, Country, Variant)] =
    Some((locale.language, locale.country, locale.variant))

  def of(language: String): Locale =
    new Locale(new JLocale(language))

  def of(language: String, country: String): Locale =
    new Locale(new JLocale(language, country))

  def of(language: String, country: String, variant: String): Locale =
    new Locale(new JLocale(language, country, variant))

  def forLanguageTag(languageTag: String): Locale =
    new Locale(JLocale.forLanguageTag(languageTag))

  def fromJavaLocale(jLocale: JLocale): Locale =
    new Locale(jLocale)

  def default: Locale =
    new Locale(JLocale.getDefault);

  def default_=(locale: Locale): Unit =
    JLocale.setDefault(locale.asJavaLocale)

  def defaultFor(category: Category): Locale =
    new Locale(JLocale.getDefault(category))

  def setDefaultFor(category: Category): DefaultSetter =
    new DefaultSetter(category)

  def isoLanguages(): Seq[String] =
    JLocale.getISOLanguages

  def isoCountries(): Seq[String] =
    JLocale.getISOCountries

  def languages(): Seq[Language] =
    JLocale.getISOLanguages.map(Language(_))

  def countries(): Seq[Country] =
    JLocale.getISOCountries.map(Country(_))

  def availableLocales(): Seq[Locale] =
    JLocale.getAvailableLocales.map(new Locale(_))

  sealed trait Category
  object Category {
    case object Display extends Category
    case object Format extends Category
  }

  implicit final private def category2JavaCategory(category: Category): JLocale.Category =
    category match {
      case Category.Display => JLocale.Category.DISPLAY
      case Category.Format => JLocale.Category.FORMAT
    }

  class DefaultSetter private[Locale](category: Category) {
    def :=(locale: Locale): Unit =
      JLocale.setDefault(category, locale.asJavaLocale)
  }

}

final case class Language private[Language](val code: String) extends AnyVal
    with Equals with Ordered[Language] {

  override def compare(that: Language): Int =
    this.code.compare(that.code)

}

object LanguageOrdering extends Ordering[Language] {

  override def compare(x: Language, y: Language): Int =
    x compare y

}

object Language extends PredefinedLanguages

final case class Country private[Country](val code: String) extends AnyVal
    with Equals with Ordered[Country] {

  override def compare(that: Country): Int =
    this.code.compare(that.code)

}

object CountryOrdering extends Ordering[Country] {

  override def compare(x: Country, y: Country): Int =
    x compare y

}

object Country extends PredefinedCountries

final case class Variant private[Variant](val code: String) extends AnyVal
    with Equals with Ordered[Variant] {

  override def compare(that: Variant): Int =
    this.code.compare(that.code)

}

object VariantOrdering extends Ordering[Variant] {

  override def compare(x: Variant, y: Variant): Int =
    x compare y

}

object Variant extends PredefinedVariants

trait PredefinedLanguages {

  val Any = Language("")

  def de = Language("de")
  def en = Language("en")
  def es = Language("es")
  def fr = Language("fr")
  def it = Language("it")
  def pt = Language("pt")
  def zh = Language("zh")

}

trait PredefinedCountries {

  val Any = Country("")

  def AT = Country("AT")
  def AU = Country("AU")
  def BE = Country("BE")
  def BR = Country("BR")
  def CA = Country("CA")
  def CH = Country("CH")
  def CN = Country("CN")
  def DE = Country("DE")
  def ES = Country("ES")
  def FR = Country("FR")
  def GB = Country("GB")
  def HK = Country("HK")
  def IT = Country("IT")
  def MX = Country("MX")
  def PT = Country("PT")
  def SG = Country("SG")
  def US = Country("US")
  def TW = Country("TW")

}

trait PredefinedVariants {

  val Any = Variant("")

}

trait PredefinedLocales {

  final def de = Locale(Language.de)
  final def de_DE = Locale(Language.de, Country.DE)
  final def de_AT = Locale(Language.de, Country.AT)
  final def de_CH = Locale(Language.de, Country.CH)

  final def en = Locale(Language.en)
  final def en_AU = Locale(Language.en, Country.AU)
  final def en_CA = Locale(Language.en, Country.CA)
  final def en_GB = Locale(Language.en, Country.GB)
  final def en_US = Locale(Language.en, Country.US)

  final def es = Locale(Language.es)
  final def es_ES = Locale(Language.es, Country.ES)
  final def es_MX = Locale(Language.es, Country.MX)

  final def fr = Locale(Language.fr)
  final def fr_BE = Locale(Language.fr, Country.BE)
  final def fr_CA = Locale(Language.fr, Country.CA)
  final def fr_CH = Locale(Language.fr, Country.CH)
  final def fr_FR = Locale(Language.fr, Country.FR)

  final def it = Locale(Language.it)
  final def it_CH = Locale(Language.it, Country.CH)
  final def it_IT = Locale(Language.it, Country.IT)

  final def pt = Locale(Language.pt)
  final def pt_BR = Locale(Language.pt, Country.BR)
  final def pt_PT = Locale(Language.pt, Country.PT)

  final def zh = Locale(Language.zh)
  final def zh_CN = Locale(Language.zh, Country.CN)
  final def zh_HK = Locale(Language.zh, Country.HK)
  final def zh_SG = Locale(Language.zh, Country.SG)
  final def zh_TW = Locale(Language.zh, Country.TW)

}
