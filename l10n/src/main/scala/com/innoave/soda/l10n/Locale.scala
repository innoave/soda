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
import scala.collection.mutable

final class Locale private[Locale](
    val language: Language,
    val country: Country,
    val variant: Variant
    ) extends Equals with Ordered[Locale] {

  lazy val asJavaLocale: JLocale =
    new JLocale(language.code, country.code, variant.code)

  override def canEqual(other: Any): Boolean =
    other.isInstanceOf[Locale]

  override def equals(other: Any): Boolean =
    other match {
      case that: Locale =>
        that.canEqual(Locale.this) &&
        this.language == that.language &&
        this.country == that.country &&
        this.variant == that.variant
      case _ => false
    }

  override def hashCode(): Int = {
    val prime = 41
    prime * (prime * (prime + language.hashCode) + country.hashCode) + variant.hashCode
  }

  override def toString(): String =
    s"Locale($language, $country, $variant)"

  override def compare(that: Locale): Int = {
    val languageCompared = this.language.compare(that.language)
    if (languageCompared == 0) {
      val countryCompared = this.country.compare(that.country)
      if (countryCompared == 0) {
        this.variant.compare(that.variant)
      } else {
        countryCompared
      }
    } else {
      languageCompared
    }
  }

  def asLanguageTag: String =
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

  def iso3Language(): String =
    asJavaLocale.getISO3Language

  def iso3Country(): String =
    asJavaLocale.getISO3Country

}

object LocaleOrdering extends Ordering[Locale] {

  override def compare(x: Locale, y: Locale): Int =
    x compare y

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

final case class Country private[Country](val code: String) extends AnyVal
    with Equals with Ordered[Country] {

  override def compare(that: Country): Int =
    this.code.compare(that.code)

}

object CountryOrdering extends Ordering[Country] {

  override def compare(x: Country, y: Country): Int =
    x compare y

}

final case class Variant private[Variant](val code: String) extends AnyVal
    with Equals with Ordered[Variant] {

  override def compare(that: Variant): Int =
    this.code.compare(that.code)

}

object VariantOrdering extends Ordering[Variant] {

  override def compare(x: Variant, y: Variant): Int =
    x compare y

}

final private[l10n] class Cache[K, V] {
  private[this] val valueMap: mutable.Map[K, V] = new mutable.WeakHashMap()
  final def getOrElseUpdate(key: K, op: => V): V = {
    valueMap.synchronized {
      valueMap.getOrElseUpdate(key, op)
    }
  }
}

object Language {

  val Any = Language("")

  def de = Language("de")
  def en = Language("en")
  def es = Language("es")
  def fr = Language("fr")
  def it = Language("it")
  def pt = Language("pt")
  def zh = Language("zh")

}

object Country {

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

object Variant {

  val Any = Variant("")

}

object Locale {

  private[this] val cache: Cache[(Language, Country, Variant), Locale] = new Cache()

  final private def localeOf(language: Language): Locale =
    cache.getOrElseUpdate((language, Country.Any, Variant.Any),
        new Locale(language, Country.Any, Variant.Any))

  final private def localeOf(language: Language, country: Country): Locale =
    cache.getOrElseUpdate((language, country, Variant.Any),
        new Locale(language, country, Variant.Any))

  final private def localeOf(language: Language, country: Country, variant: Variant): Locale =
    cache.getOrElseUpdate((language, country, variant),
        new Locale(language, country, variant))

  final private def javaLocale2Locale(jLocale: JLocale): Locale =
    localeOf(Language(jLocale.getLanguage), Country(jLocale.getCountry), Variant(jLocale.getVariant))

  def apply(language: Language): Locale =
    localeOf(language)

  def apply(language: Language, country: Country): Locale =
    localeOf(language, country)

  def apply(language: Language, country: Country, variant: Variant): Locale =
    localeOf(language, country, variant)

  def of(language: String): Locale =
    localeOf(Language(language))

  def of(language: String, country: String): Locale =
    localeOf(Language(language), Country(country))

  def of(language: String, country: String, variant: String): Locale =
    localeOf(Language(language), Country(country), Variant(variant))

  def unapply(locale: Locale): Option[(Language, Country, Variant)] =
    Some((locale.language, locale.country, locale.variant))

  def forLanguageTag(languageTag: String): Locale =
    javaLocale2Locale(JLocale.forLanguageTag(languageTag))

  def fromJavaLocale(jLocale: JLocale): Locale =
    javaLocale2Locale(jLocale)

  def default: Locale =
    javaLocale2Locale(JLocale.getDefault);

  def default_=(locale: Locale) =
    JLocale.setDefault(locale.asJavaLocale)

  def isoLanguages(): Seq[String] =
    JLocale.getISOLanguages

  def isoCountries(): Seq[String] =
    JLocale.getISOCountries

  def languages(): Seq[Language] =
    JLocale.getISOLanguages.map(Language(_))

  def countries(): Seq[Country] =
    JLocale.getISOCountries.map(Country(_))

  def availableLocales(): Seq[Locale] =
    JLocale.getAvailableLocales.map(javaLocale2Locale(_))

  def de = localeOf(Language.de)
  def de_DE = localeOf(Language.de, Country.DE)
  def de_AT = localeOf(Language.de, Country.AT)
  def de_CH = localeOf(Language.de, Country.CH)

  def en = localeOf(Language.en)
  def en_US = localeOf(Language.en, Country.US)
  def en_GB = localeOf(Language.en, Country.GB)
  def en_AU = localeOf(Language.en, Country.AU)

  def es = localeOf(Language.es)
  def es_ES = localeOf(Language.es, Country.ES)
  def es_MX = localeOf(Language.es, Country.MX)

  def fr = localeOf(Language.fr)
  def fr_BE = localeOf(Language.fr, Country.BE)
  def fr_CA = localeOf(Language.fr, Country.CA)
  def fr_CH = localeOf(Language.fr, Country.CH)
  def fr_FR = localeOf(Language.fr, Country.FR)

  def it = localeOf(Language.it)
  def it_IT = localeOf(Language.it, Country.IT)
  def it_CH = localeOf(Language.it, Country.CH)

  def pt = localeOf(Language.pt)
  def pt_BR = localeOf(Language.pt, Country.BR)
  def pt_PT = localeOf(Language.pt, Country.PT)

  def zh = localeOf(Language.zh)
  def zh_CN = localeOf(Language.zh, Country.CN)
  def zh_HK = localeOf(Language.zh, Country.HK)
  def zh_SG = localeOf(Language.zh, Country.SG)
  def zh_TW = localeOf(Language.zh, Country.TW)

}
