import com.innoave.soda.l10n.TestMessages
import com.innoave.soda.l10n.resource.ResourceBundle
import scala.reflect.NameTransformer._
import scala.util.matching.Regex
import java.util.{Locale => JLocale}

object playground {

  JLocale.getAvailableLocales.filter(_.getLanguage == "en")
                                                  //> res0: Array[java.util.Locale] = Array(en_US, en_SG, en_MT, en, en_PH, en_NZ,
                                                  //|  en_ZA, en_AU, en_IE, en_CA, en_IN, en_GB)
  JLocale.getAvailableLocales.filter(_.getLanguage == "de")
                                                  //> res1: Array[java.util.Locale] = Array(de, de_CH, de_AT, de_LU, de_DE, de_GR)
                                                  //| 
  JLocale.getAvailableLocales.filter(_.getLanguage == "fr")
                                                  //> res2: Array[java.util.Locale] = Array(fr_BE, fr_CH, fr, fr_LU, fr_FR, fr_CA)
                                                  //| 
  JLocale.getAvailableLocales.filter(_.getLanguage == "it")
                                                  //> res3: Array[java.util.Locale] = Array(it, it_CH, it_IT)
  JLocale.getAvailableLocales.filter(_.getLanguage == "es")
                                                  //> res4: Array[java.util.Locale] = Array(es_PA, es_VE, es_PR, es_BO, es_AR, es_
                                                  //| SV, es, es_ES, es_CO, es_PY, es_EC, es_US, es_GT, es_MX, es_HN, es_CL, es_DO
                                                  //| , es_CU, es_UY, es_CR, es_NI, es_PE)
  JLocale.US                                      //> res5: java.util.Locale = en_US
  JLocale.US.getISO3Language                      //> res6: String = eng
  JLocale.US.getISO3Country                       //> res7: String = USA
  JLocale.US.getDisplayLanguage                   //> res8: String = Englisch
  JLocale.US.getDisplayCountry                    //> res9: String = Vereinigte Staaten von Amerika
                                                  
                                                  
  ResourceBundle.stubFor(TestMessages)            //> res10: String = "#
                                                  //| # TestMessages : Message definitions
                                                  //| #
                                                  //| greeting=
                                                  //| hello.world=
                                                  //| products.in.shopping.cart=
                                                  //| "

  import com.innoave.soda.l10n.resource.ResourceBundleRenderLocalized._
  import com.innoave.soda.l10n.Locale._
  
  import com.innoave.soda.l10n.TestMessages._

  renderLocalized(helloWorld)(EN)                 //> res11: com.innoave.soda.l10n.LocaleText = LocaleText(Hello World!)

  import com.innoave.soda.l10n.DemoTypes._
  import com.innoave.soda.l10n.DemoTypesLocalizer._

  val queenOfHearts = Card(Hearts, Queen)         //> queenOfHearts  : com.innoave.soda.l10n.DemoTypes.Card = Card(Hearts,Queen)
                                                  //| 
  renderLocalized(queenOfHearts)(EN)              //> res12: com.innoave.soda.l10n.LocaleText = LocaleText(Queen of Hearts)
  renderLocalized(queenOfHearts)(DE)              //> res13: com.innoave.soda.l10n.LocaleText = LocaleText(Herz Dame)

  (1, "2").productIterator.map({case x: Int => x.toString case a => a}).toArray
                                                  //> res14: Array[Any] = Array(1, 2)
  s"text test $queenOfHearts"                     //> res15: String = text test Card(Hearts,Queen)

  def simpleTypeName(clazz: Class[_]): String =
    ((clazz.getName stripSuffix MODULE_SUFFIX_STRING split '.').last split
      Regex.quote(NAME_JOIN_STRING)).last         //> simpleTypeName: (clazz: Class[_])String
      
  queenOfHearts.getClass.getName                  //> res16: String = com.innoave.soda.l10n.DemoTypes$Card
  Hearts.getClass.getName                         //> res17: String = com.innoave.soda.l10n.DemoTypes$Hearts$
  
  val suit: Suit = Hearts                         //> suit  : com.innoave.soda.l10n.DemoTypes.Suit = Hearts

  simpleTypeName(queenOfHearts.getClass)          //> res18: String = Card
  simpleTypeName(Hearts.getClass)                 //> res19: String = Hearts
  simpleTypeName(suit.getClass)                   //> res20: String = Hearts

}