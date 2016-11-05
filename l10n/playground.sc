import com.innoave.soda.l10n.TestMessages
import com.innoave.soda.l10n.ResourceBundle

object playground {

  sealed trait Suit
  case object Spades extends Suit
  case object Clubs extends Suit
  case object Diamonds extends Suit
  case object Hearts extends Suit
  
  sealed trait Face
  case object Ten extends Face
  case object Jack extends Face
  case object Queen extends Face
  case object King extends Face

  case class Card(suit: Suit, face: Face)


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

  import com.innoave.soda.l10n.Locale._
  import com.innoave.soda.l10n.ResourceBundleRenderMessage._
  
  render(TestMessages.helloWorld)(EN)             //> java.util.MissingResourceException: Can't find bundle for base name i18n.te
                                                  //| st_messages, locale en
                                                  //| 	at java.util.ResourceBundle.throwMissingResourceException(ResourceBundle
                                                  //| .java:1564)
                                                  //| 	at java.util.ResourceBundle.getBundleImpl(ResourceBundle.java:1387)
                                                  //| 	at java.util.ResourceBundle.getBundle(ResourceBundle.java:845)
                                                  //| 	at com.innoave.soda.l10n.ResourceBundleRenderMessage$.patternFor(Resourc
                                                  //| eBundleRenderMessage.scala:23)
                                                  //| 	at com.innoave.soda.l10n.RenderMessage$class.render(RenderMessage.scala:
                                                  //| 21)
                                                  //| 	at com.innoave.soda.l10n.ResourceBundleRenderMessage$.render(ResourceBun
                                                  //| dleRenderMessage.scala:20)
                                                  //| 	at playground$$anonfun$main$1.apply$mcV$sp(playground.scala:32)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scal
                                                  //| Output exceeds cutoff limit.
  
//  render Card(Hearts, Queen) EN text()

}