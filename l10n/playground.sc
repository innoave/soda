import com.innoave.soda.l10n.TestMessages
import com.innoave.soda.l10n.resource.ResourceBundle
import scala.reflect.NameTransformer._
import scala.util.matching.Regex
import java.util.{Locale => JLocale}

object playground {

  import com.innoave.soda.l10n.BundleName
  import com.innoave.soda.l10n.DefineMessage
  
  object DemoMessages extends DefineMessage {
	  
	  //define a custom base name for the message bundle
	  override val bundleName = BundleName("l10n.testmessages")
	
	  // message with no parameters
	  val helloWorld = Message0
	
	  // message with one parameter of type string
	  val greeting = Message1[String]
	
	  // message with two parameters of type string and integer
	  val productsInShoppingCart = Message2[String, Int]

	}
	
	import com.innoave.soda.l10n.Locale._
	import com.innoave.soda.l10n.syntax._
	import DemoMessages._

  //render message localized in specified language
  helloWorld().in(EN).value                       //> res0: String = Hello World!
  
  //render message with parameter in specified language
  greeting("Frank").in(DE).value                  //> res1: String = Guten Tag Frank
  
  //define implicit locale to be used
  implicit val locale = EN                        //> locale  : com.innoave.soda.l10n.Locale = Locale(Language(en), Country(), Va
                                                  //| riant())

  //render message localized in the implicitly set language
  productsInShoppingCart("Paul", 0).asLocaleText.value
                                                  //> res2: String = Paul has no items in the cart.

  
  

  JLocale.getAvailableLocales.filter(_.getLanguage == "en")
                                                  //> res3: Array[java.util.Locale] = Array(en_US, en_SG, en_MT, en, en_PH, en_NZ
                                                  //| , en_ZA, en_AU, en_IE, en_CA, en_IN, en_GB)
  JLocale.getAvailableLocales.filter(_.getLanguage == "de")
                                                  //> res4: Array[java.util.Locale] = Array(de, de_CH, de_AT, de_LU, de_DE, de_GR
                                                  //| )
  JLocale.getAvailableLocales.filter(_.getLanguage == "fr")
                                                  //> res5: Array[java.util.Locale] = Array(fr_BE, fr_CH, fr, fr_LU, fr_FR, fr_CA
                                                  //| )
  JLocale.getAvailableLocales.filter(_.getLanguage == "it")
                                                  //> res6: Array[java.util.Locale] = Array(it, it_CH, it_IT)
  JLocale.getAvailableLocales.filter(_.getLanguage == "es")
                                                  //> res7: Array[java.util.Locale] = Array(es_PA, es_VE, es_PR, es_BO, es_AR, es
                                                  //| _SV, es, es_ES, es_CO, es_PY, es_EC, es_US, es_GT, es_MX, es_HN, es_CL, es_
                                                  //| DO, es_CU, es_UY, es_CR, es_NI, es_PE)
	JLocale.getAvailableLocales.filter(_.getLanguage == "pt")
                                                  //> res8: Array[java.util.Locale] = Array(pt, pt_BR, pt_PT)
  
  JLocale.getAvailableLocales.filter(_.getLanguage == "zh")
                                                  //> res9: Array[java.util.Locale] = Array(zh_TW, zh_HK, zh_SG, zh_CN, zh)
  JLocale.US                                      //> res10: java.util.Locale = en_US
  JLocale.US.getISO3Language                      //> res11: String = eng
  JLocale.US.getISO3Country                       //> res12: String = USA
  JLocale.US.getDisplayName                       //> res13: String = Englisch (Vereinigte Staaten von Amerika)
  JLocale.US.getDisplayScript                     //> res14: String = ""
  JLocale.US.getDisplayLanguage                   //> res15: String = Englisch
  JLocale.US.getDisplayCountry                    //> res16: String = Vereinigte Staaten von Amerika
 

                                                    
  ResourceBundle.stubFor(TestMessages)            //> res17: String = "#
                                                  //| # TestMessages : Message definitions
                                                  //| #
                                                  //| greeting=
                                                  //| hello.world=
                                                  //| products.in.shopping.cart=
                                                  //| "


  import com.innoave.soda.l10n.resource.ResourceBundleRenderLocalized._
  import com.innoave.soda.l10n.Locale._

  import com.innoave.soda.l10n.DemoTypes._
  import com.innoave.soda.l10n.DemoTypesLocalizer._

  val queenOfHearts = Card(Hearts, Queen)         //> queenOfHearts  : com.innoave.soda.l10n.DemoTypes.Card = Card(Hearts,Queen)
                                                  //| 
  renderLocalized(queenOfHearts)(EN)              //> res18: com.innoave.soda.l10n.LocaleText = LocaleText(Queen of Hearts)
  renderLocalized(queenOfHearts)(DE)              //> res19: com.innoave.soda.l10n.LocaleText = LocaleText(Herz Dame)

  (1, "2").productIterator.map({case x: Int => x.toString case a => a}).toArray
                                                  //> res20: Array[Any] = Array(1, 2)
  s"text test $queenOfHearts"                     //> res21: String = text test Card(Hearts,Queen)

  def simpleTypeName(clazz: Class[_]): String =
    ((clazz.getName stripSuffix MODULE_SUFFIX_STRING split '.').last split
      Regex.quote(NAME_JOIN_STRING)).last         //> simpleTypeName: (clazz: Class[_])String
      
  queenOfHearts.getClass.getName                  //> res22: String = com.innoave.soda.l10n.DemoTypes$Card
  Hearts.getClass.getName                         //> res23: String = com.innoave.soda.l10n.DemoTypes$Hearts$
  
  val suit: Suit = Hearts                         //> suit  : com.innoave.soda.l10n.DemoTypes.Suit = Hearts

  simpleTypeName(queenOfHearts.getClass)          //> res24: String = Card
  simpleTypeName(Hearts.getClass)                 //> res25: String = Hearts
  simpleTypeName(suit.getClass)                   //> res26: String = Hearts
  
  List(2,1,5,3,4).sorted == List(1,2,3,4,5)       //> res27: Boolean = true

}