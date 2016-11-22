import com.innoave.soda.l10n.resource.Utf8PropertiesResourceBundle
import scala.reflect.NameTransformer._
import scala.util.matching.Regex
import java.util.{Locale => JLocale}

object playground {

  import com.innoave.soda.l10n.BundleName
  import com.innoave.soda.l10n.DefineMessage
  
  object DemoMessages extends DefineMessage {
	  
	  //define a custom base name for the message bundle
	  override val bundleName = BundleName("l10n.DemoMessages")
	
	  // message with no parameters
	  val HelloWorld = message0
	
	  // message with one parameter of type string
	  val Greeting = message1[String]
	
	  // message with two parameters of type string and integer
	  val ProductsInShoppingCart = message2[String, Int]

	}
	
	import com.innoave.soda.l10n.Locale._
	import com.innoave.soda.l10n.syntax._
	import DemoMessages._

  //render message localized in specified language
  HelloWorld().in(en).value                       //> res0: String = Hello World!
  
  //render message with parameter in specified language
  Greeting("Frank").in(de).value                  //> res1: String = Guten Tag Frank
  
  //define implicit locale to be used
  implicit val locale = en                        //> locale  : com.innoave.soda.l10n.Locale = Locale(Language(en), Country(), Va
                                                  //| riant())

  //render message localized in the implicitly set language
  ProductsInShoppingCart("Paul", 0).asLocalText.value
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
 

  JLocale.getAvailableLocales.filter(_.getScript != "")
                                                  //> res17: Array[java.util.Locale] = Array(sr_BA_#Latn, sr_ME_#Latn, sr__#Latn,
                                                  //|  sr_RS_#Latn)
  new JLocale("sr").getScript                     //> res18: String = ""

                                                    
  Utf8PropertiesResourceBundle.stubFor(DemoMessages)
                                                  //> res19: String = "#
                                                  //| # DemoMessages : Message definitions
                                                  //| #
                                                  //| hello.world=
                                                  //| greeting=
                                                  //| products.in.shopping.cart=
                                                  //| "


  import com.innoave.soda.l10n.resource.ResourceBundleRenderLocalized._
  import com.innoave.soda.l10n.Locale._

  import com.innoave.soda.l10n.DemoTypes._
  import com.innoave.soda.l10n.DemoTypesLocalizer._

  val queenOfHearts = Card(Hearts, Queen)         //> queenOfHearts  : com.innoave.soda.l10n.DemoTypes.Card = Card(Hearts,Queen)
                                                  //| 
  renderLocalized(queenOfHearts)(en)              //> res20: com.innoave.soda.l10n.LocalText = LocalText(Queen of Hearts)
  renderLocalized(queenOfHearts)(de)              //> res21: com.innoave.soda.l10n.LocalText = LocalText(Herz Dame)

  (1, "2").productIterator.map({case x: Int => x.toString case a => a}).toArray
                                                  //> res22: Array[Any] = Array(1, 2)
  s"text test $queenOfHearts"                     //> res23: String = text test Card(Hearts,Queen)

  def simpleTypeName(clazz: Class[_]): String =
    ((clazz.getName stripSuffix MODULE_SUFFIX_STRING split '.').last split
      Regex.quote(NAME_JOIN_STRING)).last         //> simpleTypeName: (clazz: Class[_])String
      
  queenOfHearts.getClass.getName                  //> res24: String = com.innoave.soda.l10n.DemoTypes$Card
  Hearts.getClass.getName                         //> res25: String = com.innoave.soda.l10n.DemoTypes$Hearts$
  
  val suit: Suit = Hearts                         //> suit  : com.innoave.soda.l10n.DemoTypes.Suit = Hearts

  simpleTypeName(queenOfHearts.getClass)          //> res26: String = Card
  simpleTypeName(Hearts.getClass)                 //> res27: String = Hearts
  simpleTypeName(suit.getClass)                   //> res28: String = Hearts
  
  List(2,1,5,3,4).sorted == List(1,2,3,4,5)       //> res29: Boolean = true

}