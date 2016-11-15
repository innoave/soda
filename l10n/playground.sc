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
  JLocale.US                                      //> res8: java.util.Locale = en_US
  JLocale.US.getISO3Language                      //> res9: String = eng
  JLocale.US.getISO3Country                       //> res10: String = USA
  JLocale.US.getDisplayName                       //> res11: String = Englisch (Vereinigte Staaten von Amerika)
  JLocale.US.getDisplayScript                     //> res12: String = ""
  JLocale.US.getDisplayLanguage                   //> res13: String = Englisch
  JLocale.US.getDisplayCountry                    //> res14: String = Vereinigte Staaten von Amerika
 

                                                    
  ResourceBundle.stubFor(TestMessages)            //> res15: String = "#
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
  renderLocalized(queenOfHearts)(EN)              //> res16: com.innoave.soda.l10n.LocaleText = LocaleText(Queen of Hearts)
  renderLocalized(queenOfHearts)(DE)              //> res17: com.innoave.soda.l10n.LocaleText = LocaleText(Herz Dame)

  (1, "2").productIterator.map({case x: Int => x.toString case a => a}).toArray
                                                  //> res18: Array[Any] = Array(1, 2)
  s"text test $queenOfHearts"                     //> res19: String = text test Card(Hearts,Queen)

  def simpleTypeName(clazz: Class[_]): String =
    ((clazz.getName stripSuffix MODULE_SUFFIX_STRING split '.').last split
      Regex.quote(NAME_JOIN_STRING)).last         //> simpleTypeName: (clazz: Class[_])String
      
  queenOfHearts.getClass.getName                  //> res20: String = com.innoave.soda.l10n.DemoTypes$Card
  Hearts.getClass.getName                         //> res21: String = com.innoave.soda.l10n.DemoTypes$Hearts$
  
  val suit: Suit = Hearts                         //> suit  : com.innoave.soda.l10n.DemoTypes.Suit = Hearts

  simpleTypeName(queenOfHearts.getClass)          //> res22: String = Card
  simpleTypeName(Hearts.getClass)                 //> res23: String = Hearts
  simpleTypeName(suit.getClass)                   //> res24: String = Hearts

}