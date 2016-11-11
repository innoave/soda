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

import scala.collection.mutable

class Locale private[Locale](
    val language: Language,
    val country: Country,
    val variant: Variant
    ) extends Equals {

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
    super.hashCode()
    val prime = 41
    prime * (prime * (prime + language.hashCode) + country.hashCode) + variant.hashCode
  }

  override def toString(): String =
    s"Locale($language, $country, $variant)"

  def asJava: java.util.Locale =
    new java.util.Locale(language.code, country.code, variant.code)

}

class Language private[Language](val code: String) extends Equals {

  override def canEqual(other: Any): Boolean =
    other.isInstanceOf[Language]

  override def equals(other: Any): Boolean =
    other match {
      case that: Language => that.canEqual(Language.this) && this.code == that.code
      case _ => false
    }

  override def toString(): String =
    s"Language($code)"

}

class Country private[Country](val code: String) extends Equals {

  override def canEqual(other: Any): Boolean =
    other.isInstanceOf[Country]

  override def equals(other: Any): Boolean =
    other match {
      case that: Country => that.canEqual(Country.this) && this.code == that.code
      case _ => false
    }

  override def toString(): String =
    s"Country($code)"
}

class Variant private[Variant](val code: String) extends Equals {

  override def canEqual(other: Any): Boolean =
    other.isInstanceOf[Variant]

  override def equals(other: Any): Boolean =
    other match {
      case that: Variant => that.canEqual(Variant.this) && this.code == that.code
      case _ => false
    }

  override def toString(): String =
    s"Variant($code)"
}

object Language {

  private val valueMap: mutable.Map[String, Language] = mutable.HashMap()

  final private def languageOf(code: String): Language =
    valueMap.getOrElseUpdate(code, new Language(code))

  def apply(code: String): Language =
    valueMap.synchronized {
      languageOf(code)
    }

  val Any = languageOf("")
  val EN = languageOf("en")
  val DE = languageOf("de")
  val FR = languageOf("fr")
  val IT = languageOf("it")
  val ES = languageOf("es")

}

object Country {

  private val valueMap: mutable.Map[String, Country] = mutable.HashMap()

  final private def countryOf(code: String): Country =
    valueMap.getOrElseUpdate(code, new Country(code))

  def apply(code: String): Country =
    valueMap.synchronized {
      countryOf(code)
    }

  val Any = countryOf("")
  val US = countryOf("US")
  val GB = countryOf("GB")
  val AU = countryOf("AU")
  val DE = countryOf("DE")
  val AT = countryOf("AT")
  val CH = countryOf("CH")

}

object Variant {

  private val valueMap: mutable.Map[String, Variant] = mutable.HashMap()

  final private def variantOf(code: String): Variant =
    valueMap.getOrElseUpdate(code, new Variant(code))

  def apply(code: String): Variant =
    valueMap.synchronized {
      variantOf(code)
    }

  val Any = variantOf("")

}

object Locale {
  import java.util.{Locale => JLocale}

  private val valueMap: mutable.Map[(String, String, String), Locale] = mutable.Map()

  final private def localeOf(language: String): Locale =
    valueMap.getOrElseUpdate((language, Country.Any.code, Variant.Any.code),
        new Locale(Language(language), Country.Any, Variant.Any))

  final private def localeOf(language: String, country: String): Locale =
    valueMap.getOrElseUpdate((language, country, Variant.Any.code),
        new Locale(Language(language), Country(country), Variant.Any))

  final private def localeOf(language: String, country: String, variant: String): Locale =
    valueMap.getOrElseUpdate((language, country, variant),
        new Locale(Language(language), Country(country), Variant(variant)))

  def apply(language: String): Locale =
    valueMap.synchronized {
      localeOf(language)
    }

  def apply(language: String, country: String): Locale =
    valueMap.synchronized {
      localeOf(language, country)
    }

  def apply(language: String, country: String, variant: String): Locale =
    valueMap.synchronized {
      localeOf(language, country, variant)
    }

  def fromJava(jLocale: JLocale): Locale =
    valueMap.synchronized {
      localeOf(jLocale.getLanguage, jLocale.getCountry, jLocale.getVariant)
    }

  def default: Locale =
    fromJava(JLocale.getDefault);

  def default_=(locale: Locale) =
    JLocale.setDefault(locale.asJava)

  def getIsoLanguages(): Seq[String] =
    JLocale.getISOLanguages

  def getIsoCountries(): Seq[String] =
    JLocale.getISOCountries

  val EN = localeOf("EN")
  val EN_US = localeOf("EN", "US")
  val EN_GB = localeOf("EN", "GB")
  val EN_AU = localeOf("EN", "AU")

  val DE = localeOf("DE")
  val DE_DE = localeOf("DE", "DE")
  val DE_AT = localeOf("DE", "AT")
  val DE_CH = localeOf("DE", "CH")

  val FR = localeOf("FR")

  val IT = localeOf("IT")

  val ES = localeOf("ES")

}
