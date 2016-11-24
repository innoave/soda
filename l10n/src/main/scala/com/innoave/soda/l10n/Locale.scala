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
import scala.collection.JavaConverters._
import enumeratum._

final class Locale private[Locale](val asJavaLocale: JLocale) extends AnyVal with Equals {

  def language(): Language =
    Language(asJavaLocale.getLanguage)

  def country(): Country =
    Country(asJavaLocale.getCountry)

  def variant(): Variant =
    Variant(asJavaLocale.getVariant)

  def script(): Script =
    Script(asJavaLocale.getScript)

  override def toString(): String =
    s"Locale($language, $country, $variant, $script)"

  override def canEqual(other: Any): Boolean =
    other.isInstanceOf[Locale]

  def asLanguageTag(): LanguageTag =
    LanguageTag(asJavaLocale.toLanguageTag)

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

  def displayScript(): String =
    asJavaLocale.getDisplayScript

  def displayScript(inLocale: Locale): String =
    asJavaLocale.getDisplayScript(inLocale.asJavaLocale)

  def iso3Language(): String =
    asJavaLocale.getISO3Language

  def iso3Country(): String =
    asJavaLocale.getISO3Country

}

object Locale extends PredefinedLocales {

  def apply(languageTag: LanguageTag): Locale =
    new Locale(JLocale.forLanguageTag(languageTag.value))

  def apply(language: Language,
      country: Country = Country.Any,
      variant: Variant = Variant.Any,
      script: Script = Script.Any
      ): Locale =
    new Locale(new JLocale.Builder()
        .setLanguage(language.code)
        .setRegion(country.code)
        .setVariant(variant.code)
        .setScript(script.code)
        .build)

  def unapply(locale: Locale): Option[(Language, Country, Variant, Script)] =
    Some((locale.language, locale.country, locale.variant, locale.script))

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

  def filter(priorityList: Seq[JLocale.LanguageRange], locales: Seq[Locale]): Seq[Locale] =
    JLocale.filter(priorityList.asJava, locales.map(_.asJavaLocale).asJava).asScala.map(new Locale(_))

  def filter(priorityList: Seq[JLocale.LanguageRange], locales: Seq[Locale], filteringMode: FilteringMode): Seq[Locale] =
    JLocale.filter(priorityList.asJava, locales.map(_.asJavaLocale).asJava, filteringMode).asScala.map(new Locale(_))

  def filterTags(priorityList: Seq[JLocale.LanguageRange], languageTags: Seq[String]): Seq[String] =
    JLocale.filterTags(priorityList.asJava, languageTags.asJavaCollection).asScala

  def filterTags(priorityList: Seq[JLocale.LanguageRange], languageTags: Seq[String], filteringMode: FilteringMode): Seq[String] =
    JLocale.filterTags(priorityList.asJava, languageTags.asJavaCollection, filteringMode).asScala

  def lookup(priorityList: Seq[LanguageRange], locales: Seq[Locale]): Option[Locale] =
    Option(JLocale.lookup(priorityList.map(_.asJava).asJava, locales.map(_.asJavaLocale).asJavaCollection)).map(new Locale(_))

  def lookupTag(priorityList: Seq[LanguageRange], languageTags: Seq[String]): Option[String] =
    Option(JLocale.lookupTag(priorityList.map(_.asJava).asJava, languageTags.asJava))

  sealed trait Category extends EnumEntry
  object Category extends Enum[Category] {
    val values = findValues
    case object Display extends Category
    case object Format extends Category
  }

  sealed trait FilteringMode extends EnumEntry
  object FilteringMode extends Enum[FilteringMode] {
    val values = findValues
    case object AutoselectFiltering extends FilteringMode
    case object ExtendedFiltering extends FilteringMode
    case object IgnoreExtendedRanges extends FilteringMode
    case object MapExtendedRanges extends FilteringMode
    case object RejectExtendedRanges extends FilteringMode
  }

  implicit final private def asJavaCategory(category: Category): JLocale.Category =
    category match {
      case Category.Display => JLocale.Category.DISPLAY
      case Category.Format => JLocale.Category.FORMAT
    }

  implicit final private def asJavaFilteringMode(filteringMode: FilteringMode): JLocale.FilteringMode =
    filteringMode match {
      case FilteringMode.AutoselectFiltering => JLocale.FilteringMode.AUTOSELECT_FILTERING
      case FilteringMode.ExtendedFiltering => JLocale.FilteringMode.EXTENDED_FILTERING
      case FilteringMode.IgnoreExtendedRanges => JLocale.FilteringMode.IGNORE_EXTENDED_RANGES
      case FilteringMode.MapExtendedRanges => JLocale.FilteringMode.MAP_EXTENDED_RANGES
      case FilteringMode.RejectExtendedRanges => JLocale.FilteringMode.REJECT_EXTENDED_RANGES
    }

  class DefaultSetter private[Locale](category: Category) {
    def :=(locale: Locale): Unit =
      JLocale.setDefault(category, locale.asJavaLocale)
  }

}

final class LanguageRange private[LanguageRange](val asJava: JLocale.LanguageRange) extends AnyVal {

  def range(): String =
    asJava.getRange

  def weight(): Double =
    asJava.getWeight

}

object LanguageRange {

  val MaxWeight: Double = JLocale.LanguageRange.MAX_WEIGHT
  val MinWeight: Double = JLocale.LanguageRange.MIN_WEIGHT

  def apply(range: String): LanguageRange =
    new LanguageRange(new JLocale.LanguageRange(range))

  def apply(range: String, weight: Double): LanguageRange =
    new LanguageRange(new JLocale.LanguageRange(range, weight))

  def parse(ranges: String): Seq[LanguageRange] =
    JLocale.LanguageRange.parse(ranges).asScala.map(new LanguageRange(_))

  def parse(ranges: String, map: Map[String, List[String]]): Seq[LanguageRange] =
    JLocale.LanguageRange.parse(ranges, map.map(x => (x._1, x._2.asJava)).asJava).asScala.map(new LanguageRange(_))

}

final case class LanguageTag private[LanguageTag](val value: String) extends AnyVal

final case class Language private[Language](val code: String) extends AnyVal

object Language extends PredefinedLanguages

final case class Country private[Country](val code: String) extends AnyVal

object Country extends PredefinedCountries

final case class Variant private[Variant](val code: String) extends AnyVal

object Variant extends PredefinedVariants

final case class Script private[Script](val code: String) extends AnyVal

object Script extends PredefinedScripts

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

trait PredefinedScripts {

  val Any = Script("")

  def Cyrl = Script("Cyrl")
  def Hans = Script("Hans")
  def Hant = Script("Hant")
  def Latn = Script("Latn")

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
