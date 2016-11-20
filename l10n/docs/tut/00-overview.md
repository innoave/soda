---
layout: page
number: 1
title: Overview - Soda L10N
---
With Soda L10N its easy to define and use messages and types that are rendered into localized text. The translations for different languages are defined in localization bundles.

The main goal of the Soda L10N is to provide type safe localization and minimize required coding effort for defining messages and localized types in applications.

The user code references the messages in a typesafe way. Messages can have zero, one or several arguments. The arguments are replaced by their actual value at the time of rendering. The types of the arguments are defined with the message. When using a message with arguments also the number of arguments and the type of each argument is type checked by the Scala compiler.

##Features
* Types for `Locale`, `Language`, `Country` and `Variant`. The `Locale` in Soda L10N is the Scala complement for the `java.util.Locale`.
* Localization of strings. A string that should be localized is represented as `Message`.
* Messages are defined and referenced in a typesafe way.
* A `Message` can take several arguments.
* The types of the arguments are typechecked by the Scala compiler.
* Localization of the arguments of a message through `MessageFormat`. E.g. localization of numbers, date and time. The default implementation of `MessageFormat` is backed by the  `java.text.MessageFormat`.
* Localization of custom types, such as case classes and case objects.
* Flexible definition of message bundles. An application can define one big message bundle or split it up into any number of smaller ones.

##Example of defining and using localized messages

Define messages in an object that represents one message bundle:
```tut:silent
import com.innoave.soda.l10n.BundleName
import com.innoave.soda.l10n.DefineMessage
  
object DemoMessages extends DefineMessage {

  //define a custom base name for the message bundle
  override val bundleName = BundleName("l10n.DemoMessages")

  // message with no parameters
  val HelloWorld = message0

  // message with one parameter of type string
  val Greeting = message1[String]

  // message with two parameters of types string and integer
  val ProductsInShoppingCart = message2[String, Int]

}
```

Prepare the translations in UTF8 text files:
file for default language: `l10n/DemoMessages.txt`
```
#
# Test Messages in English
#
hello.world=Hello World!
greeting=Hello {0}
products.in.shopping.cart={0} has {1,choice,0#no items|1#one item|1<{1,number,integer} items} in the cart.
```
file for translations in german: `l10n/DemoMessages_de.txt`
```
#
# Test Messages in German
#
hello.world=Hallo Welt!
greeting=Guten Tag {0}
products.in.shopping.cart={0} hat {1,choice,0#keine Produkte|1#ein Produkt|1<{1,number,integer} Produkte} im Einkaufskorb.
```

Use defined messages to render localized messages:
```tut:book
import com.innoave.soda.l10n.Locale._
import com.innoave.soda.l10n.syntax._
import DemoMessages._

//render message localized in specified language
HelloWorld().in(en).value

//render message with parameter in specified language
Greeting("Frank").in(de).value

//define implicit locale to be used
implicit val locale = en

//render message localized in the implicitly set language
ProductsInShoppingCart("Paul", 0).asLocalText.value
```

