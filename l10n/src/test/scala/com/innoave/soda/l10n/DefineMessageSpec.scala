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

import java.time.LocalDateTime
import org.scalatest.FlatSpec
import org.scalatest.Matchers

private object DemoMessages extends DefineMessage {

  val MessageWithDefaultNameAndKey = Message0
  val MyMessage1, MyMessage2, MyMessage3 = Message0
  val MessageWithCustomKey = Message0("MESSAGE_WITH_CUSTOM_KEY")
  val MessageWithCustomNameAndKey = Message0("CustomName", "custom_key")

}

private object MessagesWithCustomBundleName extends DefineMessage {

  override val bundleName = BundleName("custom_messages")

  val Message1, Message2, Message3 = Message0

}

private object AllPossibleMessageVariations extends DefineMessage {

  val message0 = Message0
  val message1 = Message1[Long]
  val message2 = Message2[String, String]
  val message3 = Message3[Int, BigDecimal, String]
  val message4 = Message4[String, Long, LocalDateTime, String]
  val message5 = Message5[Int, Int, Int, Int, Int]
  val message6 = Message6[Long, String, Int, Int, String, String]
  val message7 = Message7[LocalDateTime, Long, String, Int, Int, String, String]
  val message8 = Message8[Long, Long, Long, Long, Long, Long, Long, Long]
  val message9 = Message9[Int, Int, Int, Int, Int, Int, Int, Int, Int]

  val ckmessage0 = Message0 ("ck__message0")
  val ckmessage1 = Message1[Long] ("ck__message1")
  val ckmessage2 = Message2[String, String] ("ck__message2")
  val ckmessage3 = Message3[Int, BigDecimal, String] ("ck__message3")
  val ckmessage4 = Message4[String, Long, LocalDateTime, String] ("ck__message4")
  val ckmessage5 = Message5[Int, Int, Int, Int, Int] ("ck__message5")
  val ckmessage6 = Message6[Long, String, Int, Int, String, String] ("ck__message6")
  val ckmessage7 = Message7[LocalDateTime, Long, String, Int, Int, String, String] ("ck__message7")
  val ckmessage8 = Message8[Long, Long, Long, Long, Long, Long, Long, Long] ("ck__message8")
  val ckmessage9 = Message9[Int, Int, Int, Int, Int, Int, Int, Int, Int] ("ck__message9")

  val acmessage0 = Message0 ("AllCustomMessage0", "ac__message0")
  val acmessage1 = Message1[Long] ("AllCustomMessage1", "ac__message1")
  val acmessage2 = Message2[String, String] ("AllCustomMessage2", "ac__message2")
  val acmessage3 = Message3[Int, BigDecimal, String] ("AllCustomMessage3", "ac__message3")
  val acmessage4 = Message4[String, Long, LocalDateTime, String] ("AllCustomMessage4", "ac__message4")
  val acmessage5 = Message5[Int, Int, Int, Int, Int] ("AllCustomMessage5", "ac__message5")
  val acmessage6 = Message6[Long, String, Int, Int, String, String] ("AllCustomMessage6", "ac__message6")
  val acmessage7 = Message7[LocalDateTime, Long, String, Int, Int, String, String] ("AllCustomMessage7", "ac__message7")
  val acmessage8 = Message8[Long, Long, Long, Long, Long, Long, Long, Long] ("AllCustomMessage8", "ac__message8")
  val acmessage9 = Message9[Int, Int, Int, Int, Int, Int, Int, Int, Int] ("AllCustomMessage9", "ac__message9")

}

class DefineMessageSpec extends FlatSpec with Matchers {

  "DefineMessage#toString" should "return an appropriate string" in {

    DemoMessages.toString() shouldBe "DemoMessages"

  }

  "DefineMessage with default BundleName" should "return the simple object name" in {

    DemoMessages.bundleName.value shouldBe "DemoMessages"

  }

  "DefineMessage with custom BundleName" should "return the specified bundle base name" in {

    MessagesWithCustomBundleName.bundleName.value shouldBe "custom_messages"

  }

  "DefineMessage values with no given name nor key" should "have default name and key" in {

    DemoMessages.MessageWithDefaultNameAndKey.id shouldBe 0
    DemoMessages.MessageWithDefaultNameAndKey.name() shouldBe "MessageWithDefaultNameAndKey"
    DemoMessages.MessageWithDefaultNameAndKey.key() shouldBe "message.with.default.name.and.key"

  }

  "DefineMessage with multiple values defined at once" should "be separat values" in {

    DemoMessages.MyMessage1.id shouldBe 1
    DemoMessages.MyMessage2.id shouldBe 2
    DemoMessages.MyMessage3.id shouldBe 3

    DemoMessages.MyMessage1.name shouldBe "MyMessage1"
    DemoMessages.MyMessage2.name shouldBe "MyMessage2"
    DemoMessages.MyMessage3.name shouldBe "MyMessage3"

    DemoMessages.MyMessage1.key shouldBe "my.message1"
    DemoMessages.MyMessage2.key shouldBe "my.message2"
    DemoMessages.MyMessage3.key shouldBe "my.message3"

  }

  "DefineMessage values toString method" should "return the appropriate string" in {

    DemoMessages.MessageWithDefaultNameAndKey.toString shouldBe "DemoMessages#MessageWithDefaultNameAndKey(message.with.default.name.and.key)"
    DemoMessages.MyMessage1.toString shouldBe "DemoMessages#MyMessage1(my.message1)"
    DemoMessages.MyMessage2.toString shouldBe "DemoMessages#MyMessage2(my.message2)"
    DemoMessages.MyMessage3.toString shouldBe "DemoMessages#MyMessage3(my.message3)"

  }

  "DefineMessage value with custom key" should "be defined with custom key" in {

    DemoMessages.MessageWithCustomKey.id shouldBe 4
    DemoMessages.MessageWithCustomKey.name shouldBe "MessageWithCustomKey"
    DemoMessages.MessageWithCustomKey.key shouldBe "MESSAGE_WITH_CUSTOM_KEY"

  }

  "DefineMessage value with custom name and key" should "be defined with custom name and key" in {

    DemoMessages.MessageWithCustomNameAndKey.id shouldBe 5
    DemoMessages.MessageWithCustomNameAndKey.name shouldBe "CustomName"
    DemoMessages.MessageWithCustomNameAndKey.key shouldBe "custom_key"

  }

  "DefineMessage values method" should "return a ValueSet containing all of its values" in {

    DemoMessages.values should contain (DemoMessages.MessageWithDefaultNameAndKey)
    DemoMessages.values should contain (DemoMessages.MyMessage1)
    DemoMessages.values should contain (DemoMessages.MyMessage2)
    DemoMessages.values should contain (DemoMessages.MyMessage3)
    DemoMessages.values should contain (DemoMessages.MessageWithCustomKey)
    DemoMessages.values should contain (DemoMessages.MessageWithCustomNameAndKey)

    DemoMessages.values.size shouldBe 6

  }

  "DefineMessage value set toString method" should "return an appropriate string" in {

    DemoMessages.values.toString should be ("""|
        |DemoMessages.ValueSet(
        |DemoMessages#MessageWithDefaultNameAndKey(message.with.default.name.and.key)
        |, DemoMessages#MyMessage1(my.message1)
        |, DemoMessages#MyMessage2(my.message2)
        |, DemoMessages#MyMessage3(my.message3)
        |, DemoMessages#MessageWithCustomKey(MESSAGE_WITH_CUSTOM_KEY)
        |, DemoMessages#CustomName(custom_key)
        |)""".stripMargin.replace("\n", ""))

  }

  "DefineMessage range of value set" should "return a value set of the specified range" in {

    val valueSetRange = DemoMessages.values.range(DemoMessages.MyMessage1, DemoMessages.MyMessage3)

    valueSetRange should contain (DemoMessages.MyMessage1)
    valueSetRange should contain (DemoMessages.MyMessage2)
    valueSetRange should not contain (DemoMessages.MyMessage3)

    valueSetRange.size shouldBe 2

  }

  "DefineMessage value + method" should "construct a value set of 2 values" in {

    import DemoMessages._

    val valueSet = MessageWithDefaultNameAndKey + MessageWithCustomNameAndKey

    valueSet should contain (DemoMessages.MessageWithDefaultNameAndKey)
    valueSet should contain (DemoMessages.MessageWithCustomNameAndKey)
    valueSet should not contain (DemoMessages.MyMessage1)
    valueSet should not contain (DemoMessages.MyMessage2)
    valueSet should not contain (DemoMessages.MyMessage3)
    valueSet should not contain (DemoMessages.MessageWithCustomKey)

    valueSet.size shouldBe 2

  }

  "DefineMessage MessageSet empty method" should "construct an empty message set" in {

    val emptySet = DemoMessages.MessageSet.empty

    emptySet.size shouldBe 0

  }

  "DefineMessage MessageSet empty method on MessageSet with values" should "return a new empty MessageSet" in {

    val valueSet = DemoMessages.values

    valueSet.empty.size shouldBe 0

  }

  "DefineMessage MessageSet apply method" should "construct a value set with given messages" in {

    val valueSet = DemoMessages.MessageSet(DemoMessages.MyMessage1, DemoMessages.MyMessage3, DemoMessages.MessageWithCustomKey)

    valueSet should contain (DemoMessages.MyMessage1)
    valueSet should contain (DemoMessages.MyMessage3)
    valueSet should contain (DemoMessages.MessageWithCustomKey)

    valueSet.size shouldBe 3

  }

  "DefineMessage MessageSet + method" should "add a value to the message set" in {

    val set0 = DemoMessages.MessageSet.empty
    val set1 = set0 + DemoMessages.MessageWithDefaultNameAndKey + DemoMessages.MessageWithCustomNameAndKey
    val set2 = set1 + DemoMessages.MyMessage1
    val set3 = set2 + DemoMessages.MessageWithCustomKey

    set1 should contain (DemoMessages.MessageWithDefaultNameAndKey)
    set1 should contain (DemoMessages.MessageWithCustomNameAndKey)
    set1 should not contain (DemoMessages.MyMessage1)
    set1 should not contain (DemoMessages.MyMessage2)
    set1 should not contain (DemoMessages.MyMessage3)
    set1 should not contain (DemoMessages.MessageWithCustomKey)
    set1.size shouldBe 2

    set2 should contain (DemoMessages.MessageWithDefaultNameAndKey)
    set2 should contain (DemoMessages.MessageWithCustomNameAndKey)
    set2 should contain (DemoMessages.MyMessage1)
    set2 should not contain (DemoMessages.MyMessage2)
    set2 should not contain (DemoMessages.MyMessage3)
    set2 should not contain (DemoMessages.MessageWithCustomKey)
    set2.size shouldBe 3

    set3 should contain (DemoMessages.MessageWithDefaultNameAndKey)
    set3 should contain (DemoMessages.MessageWithCustomNameAndKey)
    set3 should contain (DemoMessages.MyMessage1)
    set3 should not contain (DemoMessages.MyMessage2)
    set3 should not contain (DemoMessages.MyMessage3)
    set3 should contain (DemoMessages.MessageWithCustomKey)
    set3.size shouldBe 4

  }

  "DefineMessage MessageSet - method" should "remove a value from the message set" in {

    val set1 = DemoMessages.values
    val set2 = set1 - DemoMessages.MessageWithDefaultNameAndKey
    val set3 = set2 - DemoMessages.MyMessage2
    val set4 = set3 - DemoMessages.MessageWithCustomNameAndKey

    set1 should contain (DemoMessages.MessageWithDefaultNameAndKey)
    set1 should contain (DemoMessages.MessageWithCustomNameAndKey)
    set1 should contain (DemoMessages.MyMessage1)
    set1 should contain (DemoMessages.MyMessage2)
    set1 should contain (DemoMessages.MyMessage3)
    set1 should contain (DemoMessages.MessageWithCustomKey)
    set1.size shouldBe 6

    set2 should not contain (DemoMessages.MessageWithDefaultNameAndKey)
    set2 should contain (DemoMessages.MessageWithCustomNameAndKey)
    set2 should contain (DemoMessages.MyMessage1)
    set2 should contain (DemoMessages.MyMessage2)
    set2 should contain (DemoMessages.MyMessage3)
    set2 should contain (DemoMessages.MessageWithCustomKey)
    set2.size shouldBe 5

    set3 should not contain (DemoMessages.MessageWithDefaultNameAndKey)
    set3 should contain (DemoMessages.MessageWithCustomNameAndKey)
    set3 should contain (DemoMessages.MyMessage1)
    set3 should not contain (DemoMessages.MyMessage2)
    set3 should contain (DemoMessages.MyMessage3)
    set3 should contain (DemoMessages.MessageWithCustomKey)
    set3.size shouldBe 4

    set4 should not contain (DemoMessages.MessageWithDefaultNameAndKey)
    set4 should not contain (DemoMessages.MessageWithCustomNameAndKey)
    set4 should contain (DemoMessages.MyMessage1)
    set4 should not contain (DemoMessages.MyMessage2)
    set4 should contain (DemoMessages.MyMessage3)
    set4 should contain (DemoMessages.MessageWithCustomKey)
    set4.size shouldBe 3

  }

  "DefineMessage MessageSet iterator" should "iterate in the order of the definition of the messages" in {

    val iterator = DemoMessages.values.iterator

    iterator.next shouldBe DemoMessages.MessageWithDefaultNameAndKey
    iterator.next shouldBe DemoMessages.MyMessage1
    iterator.next shouldBe DemoMessages.MyMessage2
    iterator.next shouldBe DemoMessages.MyMessage3
    iterator.next shouldBe DemoMessages.MessageWithCustomKey
    iterator.next shouldBe DemoMessages.MessageWithCustomNameAndKey
    iterator.hasNext shouldBe false

  }

  "DefineMessage MessageSet keysIteratorFrom" should "iterate in the order of the definition of the messages beginning with the start value" in {

    val iterator = DemoMessages.values.keysIteratorFrom(DemoMessages.MyMessage2)

    iterator.next shouldBe DemoMessages.MyMessage2
    iterator.next shouldBe DemoMessages.MyMessage3
    iterator.next shouldBe DemoMessages.MessageWithCustomKey
    iterator.next shouldBe DemoMessages.MessageWithCustomNameAndKey
    iterator.hasNext shouldBe false

  }

  "DefineMessage MessageSet contains method" should "return true if a value is part of the set" in {

    val valueSet = DemoMessages.values

    valueSet.contains(DemoMessages.MyMessage1) shouldBe true
    valueSet.contains(DemoMessages.MessageWithCustomKey) shouldBe true
    valueSet.contains(DemoMessages.MyMessage2) shouldBe true
    valueSet.contains(DemoMessages.MyMessage3) shouldBe true
    valueSet.contains(DemoMessages.MessageWithDefaultNameAndKey) shouldBe true
    valueSet.contains(DemoMessages.MessageWithCustomNameAndKey) shouldBe true

  }

  "DefineMessage Message equals method" should "return true for same values" in {

    val value1 = DemoMessages.MessageWithDefaultNameAndKey
    val value2 = DemoMessages.MessageWithCustomKey

    (value1 == DemoMessages.MessageWithDefaultNameAndKey) should be (true)
    (value2 == DemoMessages.MessageWithCustomKey) should be (true)

    (value1 != DemoMessages.MessageWithDefaultNameAndKey) should be (false)
    (value2 != DemoMessages.MessageWithCustomKey) should be (false)

  }

  "DefineMessage Message equals method" should "return false for different values" in {

    val value1 = DemoMessages.MessageWithDefaultNameAndKey
    val value2 = DemoMessages.MessageWithCustomKey

    (value1 == DemoMessages.MessageWithCustomKey) should be (false)
    (value2 == DemoMessages.MessageWithDefaultNameAndKey) should be (false)

    (value1 != DemoMessages.MessageWithCustomKey) should be (true)
    (value2 != DemoMessages.MessageWithDefaultNameAndKey) should be (true)

  }

  "DefineMessage Message compare method" should "compare message by their ordinal in order of their definition" in {

    import DemoMessages._

    MyMessage1.compare(MyMessage2) shouldBe -1
    MyMessage3.compare(MyMessage2) shouldBe +1

  }

  "DefineMessage values" should "all have different hash values" in {

    DemoMessages.values.map(_.##).toSet.size shouldBe DemoMessages.values.size

  }

  "DefineMessage" should "support messages with up to 9 parameters" in {

    import AllPossibleMessageVariations._

    message0.id shouldBe 0
    message0.name shouldBe "message0"
    message0.key shouldBe "message0"

    message1.id shouldBe 1
    message1.name shouldBe "message1"
    message1.key shouldBe "message1"

    message2.id shouldBe 2
    message2.name shouldBe "message2"
    message2.key shouldBe "message2"

    message3.id shouldBe 3
    message3.name shouldBe "message3"
    message3.key shouldBe "message3"

    message4.id shouldBe 4
    message4.name shouldBe "message4"
    message4.key shouldBe "message4"

    message5.id shouldBe 5
    message5.name shouldBe "message5"
    message5.key shouldBe "message5"

    message6.id shouldBe 6
    message6.name shouldBe "message6"
    message6.key shouldBe "message6"

    message7.id shouldBe 7
    message7.name shouldBe "message7"
    message7.key shouldBe "message7"

    message8.id shouldBe 8
    message8.name shouldBe "message8"
    message8.key shouldBe "message8"

    message9.id shouldBe 9
    message9.name shouldBe "message9"
    message9.key shouldBe "message9"

  }

  it should "support messages with custom key and up to 9 parameters" in {

    import AllPossibleMessageVariations._

    ckmessage0.id shouldBe 10
    ckmessage0.name shouldBe "ckmessage0"
    ckmessage0.key shouldBe "ck__message0"

    ckmessage1.id shouldBe 11
    ckmessage1.name shouldBe "ckmessage1"
    ckmessage1.key shouldBe "ck__message1"

    ckmessage2.id shouldBe 12
    ckmessage2.name shouldBe "ckmessage2"
    ckmessage2.key shouldBe "ck__message2"

    ckmessage3.id shouldBe 13
    ckmessage3.name shouldBe "ckmessage3"
    ckmessage3.key shouldBe "ck__message3"

    ckmessage4.id shouldBe 14
    ckmessage4.name shouldBe "ckmessage4"
    ckmessage4.key shouldBe "ck__message4"

    ckmessage5.id shouldBe 15
    ckmessage5.name shouldBe "ckmessage5"
    ckmessage5.key shouldBe "ck__message5"

    ckmessage6.id shouldBe 16
    ckmessage6.name shouldBe "ckmessage6"
    ckmessage6.key shouldBe "ck__message6"

    ckmessage7.id shouldBe 17
    ckmessage7.name shouldBe "ckmessage7"
    ckmessage7.key shouldBe "ck__message7"

    ckmessage8.id shouldBe 18
    ckmessage8.name shouldBe "ckmessage8"
    ckmessage8.key shouldBe "ck__message8"

    ckmessage9.id shouldBe 19
    ckmessage9.name shouldBe "ckmessage9"
    ckmessage9.key shouldBe "ck__message9"

  }

  it should "support messages with custom key, custom name and up to 9 parameters" in {

    import AllPossibleMessageVariations._

    acmessage0.id shouldBe 20
    acmessage0.name shouldBe "AllCustomMessage0"
    acmessage0.key shouldBe "ac__message0"

    acmessage1.id shouldBe 21
    acmessage1.name shouldBe "AllCustomMessage1"
    acmessage1.key shouldBe "ac__message1"

    acmessage2.id shouldBe 22
    acmessage2.name shouldBe "AllCustomMessage2"
    acmessage2.key shouldBe "ac__message2"

    acmessage3.id shouldBe 23
    acmessage3.name shouldBe "AllCustomMessage3"
    acmessage3.key shouldBe "ac__message3"

    acmessage4.id shouldBe 24
    acmessage4.name shouldBe "AllCustomMessage4"
    acmessage4.key shouldBe "ac__message4"

    acmessage5.id shouldBe 25
    acmessage5.name shouldBe "AllCustomMessage5"
    acmessage5.key shouldBe "ac__message5"

    acmessage6.id shouldBe 26
    acmessage6.name shouldBe "AllCustomMessage6"
    acmessage6.key shouldBe "ac__message6"

    acmessage7.id shouldBe 27
    acmessage7.name shouldBe "AllCustomMessage7"
    acmessage7.key shouldBe "ac__message7"

    acmessage8.id shouldBe 28
    acmessage8.name shouldBe "AllCustomMessage8"
    acmessage8.key shouldBe "ac__message8"

    acmessage9.id shouldBe 29
    acmessage9.name shouldBe "AllCustomMessage9"
    acmessage9.key shouldBe "ac__message9"

  }

}
