import com.innoave.soda.l10n.TestMessages
import com.innoave.soda.l10n.MessageBundle

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
                                                  
  MessageBundle.stubFor(TestMessages)             //> res5: String = greeting=
                                                  //| hello.world=
                                                  //| products.in.shopping.cart

}