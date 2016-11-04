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

class Locale(
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

class Language(val code: String) extends Equals {

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

class Country(val code: String) extends Equals {

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

class Variant(val code: String) extends Equals {

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

  case object EN extends Language("en")
  case object DE extends Language("de")
  case object FR extends Language("fr")
  case object IT extends Language("it")
  case object ES extends Language("es")

}

object Country {

  case object Any extends Country("")
  case object US extends Country("US")
  case object GB extends Country("GB")
  case object AU extends Country("AU")
  case object DE extends Country("DE")
  case object AT extends Country("AT")
  case object CH extends Country("CH")

}

object Variant {

  case object Any extends Variant("")

}

object Locale {
  import java.{util => ju}

  def default: Locale =
    fromJava(ju.Locale.getDefault)

  def fromJava(jLocale: ju.Locale): Locale =
    (jLocale.getLanguage, jLocale.getCountry, jLocale.getVariant) match {
      case ("en", "US", _) => EN_US
      case ("en", "GB", _) => EN_GB
      case ("en", "AU", _) => EN_AU
      case ("en", _, _) => EN
      case ("de", "DE", _) => DE_DE
      case ("de", "AT", _) => DE_AT
      case ("de", "CH", _) => DE_CH
      case ("de", _, _) => DE
      case ("fr", _, _) => FR
      case ("it", _, _) => IT
      case ("es", _, _) => ES
      case (l, "", "") => new Locale(new Language(l), Country.Any, Variant.Any)
      case (l, c, "") => new Locale(new Language(l), new Country(c), Variant.Any)
      case (l, c, v) => new Locale(new Language(l), new Country(c), new Variant(v))
    }

  case object EN extends Locale(Language.EN, Country.Any, Variant.Any)
  case object EN_US extends Locale(Language.EN, Country.US, Variant.Any)
  case object EN_GB extends Locale(Language.EN, Country.GB, Variant.Any)
  case object EN_AU extends Locale(Language.EN, Country.AU, Variant.Any)

  case object DE extends Locale(Language.DE, Country.Any, Variant.Any)
  case object DE_DE extends Locale(Language.DE, Country.DE, Variant.Any)
  case object DE_AT extends Locale(Language.DE, Country.AT, Variant.Any)
  case object DE_CH extends Locale(Language.DE, Country.CH, Variant.Any)

  case object FR extends Locale(Language.FR, Country.Any, Variant.Any)

  case object IT extends Locale(Language.IT, Country.Any, Variant.Any)

  case object ES extends Locale(Language.ES, Country.Any, Variant.Any)

}
