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

class Locale private[Locale](
    val language: Language,
    val country: Country,
    val variant: Variant
    ) extends Equals with Ordered[Locale] {

  lazy val asJavaLocale: JLocale =
    new java.util.Locale(language.code, country.code, variant.code)

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

  def toLanguageTag: String =
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

class Language private[Language](val code: String) extends Equals with Ordered[Language] {

  override def canEqual(other: Any): Boolean =
    other.isInstanceOf[Language]

  override def equals(other: Any): Boolean =
    other match {
      case that: Language => that.canEqual(Language.this) && this.code == that.code
      case _ => false
    }

  override def hashCode(): Int = {
    val prime = 41
    prime + code.hashCode
  }

  override def toString(): String =
    s"Language($code)"

  override def compare(that: Language): Int =
    this.code.compare(that.code)

}

class Country private[Country](val code: String) extends Equals with Ordered[Country] {

  override def canEqual(other: Any): Boolean =
    other.isInstanceOf[Country]

  override def equals(other: Any): Boolean =
    other match {
      case that: Country => that.canEqual(Country.this) && this.code == that.code
      case _ => false
    }

  override def hashCode(): Int = {
    val prime = 41
    prime + code.hashCode
  }

  override def toString(): String =
    s"Country($code)"

  override def compare(that: Country): Int =
    this.code.compare(that.code)

}

class Variant private[Variant](val code: String) extends Equals with Ordered[Variant] {

  override def canEqual(other: Any): Boolean =
    other.isInstanceOf[Variant]

  override def equals(other: Any): Boolean =
    other match {
      case that: Variant => that.canEqual(Variant.this) && this.code == that.code
      case _ => false
    }

  override def hashCode(): Int = {
    val prime = 41
    prime + code.hashCode
  }

  override def toString(): String =
    s"Variant($code)"

  override def compare(that: Variant): Int =
    this.code.compare(that.code)

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

  private[this] val cache: Cache[String, Language] = new Cache()

  final private def languageOf(code: String): Language =
    cache.getOrElseUpdate(code, new Language(code))

  def apply(code: String): Language =
    languageOf(code)

  val Any = languageOf("")

  def EN = languageOf("en")
  def DE = languageOf("de")
  def FR = languageOf("fr")
  def IT = languageOf("it")
  def ES = languageOf("es")

}

object Country {

  private[this] val cache: Cache[String, Country] = new Cache()

  final private def countryOf(code: String): Country =
    cache.getOrElseUpdate(code, new Country(code))

  def apply(code: String): Country =
    countryOf(code)

  val Any = countryOf("")

  def US = countryOf("US")
  def GB = countryOf("GB")
  def AU = countryOf("AU")
  def DE = countryOf("DE")
  def AT = countryOf("AT")
  def CH = countryOf("CH")

}

object Variant {

  private[this] val cache: Cache[String, Variant] = new Cache()

  final private def variantOf(code: String): Variant =
    cache.getOrElseUpdate(code, new Variant(code))

  def apply(code: String): Variant =
    variantOf(code)

  val Any = variantOf("")

}

object Locale {

  private[this] val cache: Cache[(String, String, String), Locale] = new Cache()

  final private def localeOf(language: String): Locale =
    cache.getOrElseUpdate((language, Country.Any.code, Variant.Any.code),
        new Locale(Language(language), Country.Any, Variant.Any))

  final private def localeOf(language: String, country: String): Locale =
    cache.getOrElseUpdate((language, country, Variant.Any.code),
        new Locale(Language(language), Country(country), Variant.Any))

  final private def localeOf(language: String, country: String, variant: String): Locale =
    cache.getOrElseUpdate((language, country, variant),
        new Locale(Language(language), Country(country), Variant(variant)))

  final private def javaLocale2Locale(jLocale: JLocale): Locale =
    localeOf(jLocale.getLanguage, jLocale.getCountry, jLocale.getVariant)

  def apply(language: String): Locale =
    localeOf(language)

  def apply(language: String, country: String): Locale =
    localeOf(language, country)

  def apply(language: String, country: String, variant: String): Locale =
    localeOf(language, country, variant)

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

  def availableLocales(): Seq[Locale] =
    JLocale.getAvailableLocales.map(javaLocale2Locale(_))

  def EN = localeOf("EN")
  def EN_US = localeOf("EN", "US")
  def EN_GB = localeOf("EN", "GB")
  def EN_AU = localeOf("EN", "AU")

  def DE = localeOf("DE")
  def DE_DE = localeOf("DE", "DE")
  def DE_AT = localeOf("DE", "AT")
  def DE_CH = localeOf("DE", "CH")

  def FR = localeOf("FR")

  def IT = localeOf("IT")

  def ES = localeOf("ES")

}
