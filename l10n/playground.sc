import com.innoave.soda.l10n.TestMessages
import com.innoave.soda.l10n.ResourceBundle
import scala.reflect.NameTransformer._
import scala.util.matching.Regex

object playground {

  java.util.Locale.getAvailableLocales.filter(_.getLanguage == "en")
                                                  //> res0: Array[java.util.Locale] = Array(en_US, en_SG, en_MT, en, en_PH, en_NZ,
                                                  //|  en_ZA, en_AU, en_IE, en_CA, en_IN, en_GB)
  java.util.Locale.getAvailableLocales.filter(_.getLanguage == "de")
                                                  //> res1: Array[java.util.Locale] = Array(de, de_CH, de_AT, de_LU, de_DE, de_GR)
                                                  //| 
  java.util.Locale.getAvailableLocales.filter(_.getLanguage == "fr")
                                                  //> res2: Array[java.util.Locale] = Array(fr_BE, fr_CH, fr, fr_LU, fr_FR, fr_CA)
                                                  //| 
  java.util.Locale.getAvailableLocales.filter(_.getLanguage == "it")
                                                  //> res3: Array[java.util.Locale] = Array(it, it_CH, it_IT)
  java.util.Locale.getAvailableLocales.filter(_.getLanguage == "es")
                                                  //> res4: Array[java.util.Locale] = Array(es_PA, es_VE, es_PR, es_BO, es_AR, es_
                                                  //| SV, es, es_ES, es_CO, es_PY, es_EC, es_US, es_GT, es_MX, es_HN, es_CL, es_DO
                                                  //| , es_CU, es_UY, es_CR, es_NI, es_PE)
                                                  
  ResourceBundle.stubFor(TestMessages)            //> res5: String = greeting=
                                                  //| hello.world=
                                                  //| products.in.shopping.cart

  import com.innoave.soda.l10n.ResourceBundleRenderLocalized._
  import com.innoave.soda.l10n.Locale._
  
  import com.innoave.soda.l10n.TestMessages._

  renderLocalized(helloWorld)(EN)                 //> res6: com.innoave.soda.l10n.LocaleText = LocaleText(Hello World!)

  import com.innoave.soda.l10n.DemoTypes._
  import com.innoave.soda.l10n.DemoTypesLocalizer._

  val queenOfHearts = Card(Hearts, Queen)         //> queenOfHearts  : com.innoave.soda.l10n.DemoTypes.Card = Card(Hearts,Queen)
  renderLocalized(queenOfHearts)(EN)              //> res7: com.innoave.soda.l10n.LocaleText = LocaleText(Queen of Hearts)
  renderLocalized(queenOfHearts)(DE)              //> res8: com.innoave.soda.l10n.LocaleText = LocaleText(Herz Dame)

  (1, "2").productIterator.map({case x: Int => x.toString case a => a}).toArray
                                                  //> res9: Array[Any] = Array(1, 2)
  s"text test $queenOfHearts"                     //> res10: String = text test Card(Hearts,Queen)

  def simpleTypeName(clazz: Class[_]): String =
    ((clazz.getName stripSuffix MODULE_SUFFIX_STRING split '.').last split
      Regex.quote(NAME_JOIN_STRING)).last         //> simpleTypeName: (clazz: Class[_])String
      
  queenOfHearts.getClass.getName                  //> res11: String = com.innoave.soda.l10n.DemoTypes$Card
  Hearts.getClass.getName                         //> res12: String = com.innoave.soda.l10n.DemoTypes$Hearts$
  
  val suit: Suit = Hearts                         //> suit  : com.innoave.soda.l10n.DemoTypes.Suit = Hearts

  simpleTypeName(queenOfHearts.getClass)          //> res13: String = Card
  simpleTypeName(Hearts.getClass)                 //> res14: String = Hearts
  simpleTypeName(suit.getClass)                   //> res15: String = Hearts

}