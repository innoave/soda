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

import org.scalatest.FlatSpec
import org.scalatest.Matchers

private object TestMessages extends DefineMessage {

  val MessageWithDefaultNameAndKey = Message0
  val MyMessage1, MyMessage2, MyMessage3 = Message0
  val MessageWithCustomKey = Message0("MESSAGE_WITH_CUSTOM_KEY")
  val MessageWithCustomNameAndKey = Message0("CustomName", "custom_key")

}

private object OtherMessages extends DefineMessage {

  val MessageWithDefaultNameAndKey = Message0
  val MyMessage1, MyMessage2, MyMessage3 = Message0
  val MessageWithCustomKey = Message0("MESSAGE_WITH_CUSTOM_KEY")
  val MessageWithCustomNameAndKey = Message0("CustomName", "custom_key")

}

private object DialogMessages extends DefineMessage {

  val DialogTitle, DialogCloseButton = Message0
  val DialogMessage = Message1[String]

}

private object MessagesWithCustomBundleName extends DefineMessage {

  override val bundleName = BundleName("l10n.CustomMessages")

  val DialogTitle, DialogCloseButton = Message0
  val DialogMessage = Message1[String]

}

class DefineMessageSpec extends FlatSpec with Matchers {

  "DefineMessage#toString" should "return an appropriate string" in {

    TestMessages.toString() shouldBe "TestMessages"

  }

  "DefineMessage with default BundleName" should "return the simple object name" in {

    TestMessages.bundleName shouldBe BundleName("TestMessages")

  }

  "DefineMessage with custom BundleName" should "return the specified bundle base name" in {

    MessagesWithCustomBundleName.bundleName shouldBe BundleName("l10n.CustomMessages")

  }

  "DefineMessage values with no given name nor key" should "have default name and key" in {

    TestMessages.MessageWithDefaultNameAndKey.id shouldBe 0
    TestMessages.MessageWithDefaultNameAndKey.name() shouldBe "MessageWithDefaultNameAndKey"
    TestMessages.MessageWithDefaultNameAndKey.key() shouldBe "message.with.default.name.and.key"

  }

  "DefineMessage with multiple values defined at once" should "be separat values" in {

    TestMessages.MyMessage1.id shouldBe 1
    TestMessages.MyMessage2.id shouldBe 2
    TestMessages.MyMessage3.id shouldBe 3

    TestMessages.MyMessage1.name shouldBe "MyMessage1"
    TestMessages.MyMessage2.name shouldBe "MyMessage2"
    TestMessages.MyMessage3.name shouldBe "MyMessage3"

    TestMessages.MyMessage1.key shouldBe "my.message1"
    TestMessages.MyMessage2.key shouldBe "my.message2"
    TestMessages.MyMessage3.key shouldBe "my.message3"

  }

  "DefineMessage values toString method" should "return the appropriate string" in {

    TestMessages.MessageWithDefaultNameAndKey.toString shouldBe "TestMessages#MessageWithDefaultNameAndKey(message.with.default.name.and.key)"
    TestMessages.MyMessage1.toString shouldBe "TestMessages#MyMessage1(my.message1)"
    TestMessages.MyMessage2.toString shouldBe "TestMessages#MyMessage2(my.message2)"
    TestMessages.MyMessage3.toString shouldBe "TestMessages#MyMessage3(my.message3)"

  }

  "DefineMessage value with custom key" should "be defined with custom key" in {

    TestMessages.MessageWithCustomKey.id shouldBe 4
    TestMessages.MessageWithCustomKey.name shouldBe "MessageWithCustomKey"
    TestMessages.MessageWithCustomKey.key shouldBe "MESSAGE_WITH_CUSTOM_KEY"

  }

  "DefineMessage value with custom name and key" should "be defined with custom name and key" in {

    TestMessages.MessageWithCustomNameAndKey.id shouldBe 5
    TestMessages.MessageWithCustomNameAndKey.name shouldBe "CustomName"
    TestMessages.MessageWithCustomNameAndKey.key shouldBe "custom_key"

  }

  "DefineMessage values method" should "return a ValueSet containing all of its values" in {

    TestMessages.values should contain (TestMessages.MessageWithDefaultNameAndKey)
    TestMessages.values should contain (TestMessages.MyMessage1)
    TestMessages.values should contain (TestMessages.MyMessage2)
    TestMessages.values should contain (TestMessages.MyMessage3)
    TestMessages.values should contain (TestMessages.MessageWithCustomKey)
    TestMessages.values should contain (TestMessages.MessageWithCustomNameAndKey)

    TestMessages.values.size shouldBe 6

  }

  "DefineMessage value set toString method" should "return an appropriate string" in {

    TestMessages.values.toString should be ("""|
        |TestMessages.ValueSet(
        |TestMessages#MessageWithDefaultNameAndKey(message.with.default.name.and.key)
        |, TestMessages#MyMessage1(my.message1)
        |, TestMessages#MyMessage2(my.message2)
        |, TestMessages#MyMessage3(my.message3)
        |, TestMessages#MessageWithCustomKey(MESSAGE_WITH_CUSTOM_KEY)
        |, TestMessages#CustomName(custom_key)
        |)""".stripMargin.replace("\n", ""))

  }

  "DefineMessage range of value set" should "return a value set of the specified range" in {

    val valueSetRange = TestMessages.values.range(TestMessages.MyMessage1, TestMessages.MyMessage3)

    valueSetRange should contain (TestMessages.MyMessage1)
    valueSetRange should contain (TestMessages.MyMessage2)
    valueSetRange should not contain (TestMessages.MyMessage3)

    valueSetRange.size shouldBe 2

  }

  "DefineMessage value + method" should "construct a value set of 2 values" in {

    import TestMessages._

    val valueSet = MessageWithDefaultNameAndKey + MessageWithCustomNameAndKey

    valueSet should contain (TestMessages.MessageWithDefaultNameAndKey)
    valueSet should contain (TestMessages.MessageWithCustomNameAndKey)
    valueSet should not contain (TestMessages.MyMessage1)
    valueSet should not contain (TestMessages.MyMessage2)
    valueSet should not contain (TestMessages.MyMessage3)
    valueSet should not contain (TestMessages.MessageWithCustomKey)

    valueSet.size shouldBe 2

  }

  "DefineMessage MessageSet empty method" should "construct an empty message set" in {

    val emptySet = TestMessages.MessageSet.empty

    emptySet.size shouldBe 0

  }

  "DefineMessage MessageSet empty method on MessageSet with values" should "return a new empty MessageSet" in {

    val valueSet = TestMessages.values

    valueSet.empty.size shouldBe 0

  }

  "DefineMessage MessageSet apply method" should "construct a value set with given messages" in {

    val valueSet = TestMessages.MessageSet(TestMessages.MyMessage1, TestMessages.MyMessage3, TestMessages.MessageWithCustomKey)

    valueSet should contain (TestMessages.MyMessage1)
    valueSet should contain (TestMessages.MyMessage3)
    valueSet should contain (TestMessages.MessageWithCustomKey)

    valueSet.size shouldBe 3

  }

  "DefineMessage MessageSet + method" should "add a value to the message set" in {

    val set0 = TestMessages.MessageSet.empty
    val set1 = set0 + TestMessages.MessageWithDefaultNameAndKey + TestMessages.MessageWithCustomNameAndKey
    val set2 = set1 + TestMessages.MyMessage1
    val set3 = set2 + TestMessages.MessageWithCustomKey

    set1 should contain (TestMessages.MessageWithDefaultNameAndKey)
    set1 should contain (TestMessages.MessageWithCustomNameAndKey)
    set1 should not contain (TestMessages.MyMessage1)
    set1 should not contain (TestMessages.MyMessage2)
    set1 should not contain (TestMessages.MyMessage3)
    set1 should not contain (TestMessages.MessageWithCustomKey)
    set1.size shouldBe 2

    set2 should contain (TestMessages.MessageWithDefaultNameAndKey)
    set2 should contain (TestMessages.MessageWithCustomNameAndKey)
    set2 should contain (TestMessages.MyMessage1)
    set2 should not contain (TestMessages.MyMessage2)
    set2 should not contain (TestMessages.MyMessage3)
    set2 should not contain (TestMessages.MessageWithCustomKey)
    set2.size shouldBe 3

    set3 should contain (TestMessages.MessageWithDefaultNameAndKey)
    set3 should contain (TestMessages.MessageWithCustomNameAndKey)
    set3 should contain (TestMessages.MyMessage1)
    set3 should not contain (TestMessages.MyMessage2)
    set3 should not contain (TestMessages.MyMessage3)
    set3 should contain (TestMessages.MessageWithCustomKey)
    set3.size shouldBe 4

  }

  "DefineMessage MessageSet - method" should "remove a value from the message set" in {

    val set1 = TestMessages.values
    val set2 = set1 - TestMessages.MessageWithDefaultNameAndKey
    val set3 = set2 - TestMessages.MyMessage2
    val set4 = set3 - TestMessages.MessageWithCustomNameAndKey

    set1 should contain (TestMessages.MessageWithDefaultNameAndKey)
    set1 should contain (TestMessages.MessageWithCustomNameAndKey)
    set1 should contain (TestMessages.MyMessage1)
    set1 should contain (TestMessages.MyMessage2)
    set1 should contain (TestMessages.MyMessage3)
    set1 should contain (TestMessages.MessageWithCustomKey)
    set1.size shouldBe 6

    set2 should not contain (TestMessages.MessageWithDefaultNameAndKey)
    set2 should contain (TestMessages.MessageWithCustomNameAndKey)
    set2 should contain (TestMessages.MyMessage1)
    set2 should contain (TestMessages.MyMessage2)
    set2 should contain (TestMessages.MyMessage3)
    set2 should contain (TestMessages.MessageWithCustomKey)
    set2.size shouldBe 5

    set3 should not contain (TestMessages.MessageWithDefaultNameAndKey)
    set3 should contain (TestMessages.MessageWithCustomNameAndKey)
    set3 should contain (TestMessages.MyMessage1)
    set3 should not contain (TestMessages.MyMessage2)
    set3 should contain (TestMessages.MyMessage3)
    set3 should contain (TestMessages.MessageWithCustomKey)
    set3.size shouldBe 4

    set4 should not contain (TestMessages.MessageWithDefaultNameAndKey)
    set4 should not contain (TestMessages.MessageWithCustomNameAndKey)
    set4 should contain (TestMessages.MyMessage1)
    set4 should not contain (TestMessages.MyMessage2)
    set4 should contain (TestMessages.MyMessage3)
    set4 should contain (TestMessages.MessageWithCustomKey)
    set4.size shouldBe 3

  }

  "DefineMessage MessageSet iterator" should "iterate in the order of the definition of the messages" in {

    val iterator = TestMessages.values.iterator

    iterator.next shouldBe TestMessages.MessageWithDefaultNameAndKey
    iterator.next shouldBe TestMessages.MyMessage1
    iterator.next shouldBe TestMessages.MyMessage2
    iterator.next shouldBe TestMessages.MyMessage3
    iterator.next shouldBe TestMessages.MessageWithCustomKey
    iterator.next shouldBe TestMessages.MessageWithCustomNameAndKey
    iterator.hasNext shouldBe false

  }

  "DefineMessage MessageSet keysIteratorFrom" should "iterate in the order of the definition of the messages beginning with the start value" in {

    val iterator = TestMessages.values.keysIteratorFrom(TestMessages.MyMessage2)

    iterator.next shouldBe TestMessages.MyMessage2
    iterator.next shouldBe TestMessages.MyMessage3
    iterator.next shouldBe TestMessages.MessageWithCustomKey
    iterator.next shouldBe TestMessages.MessageWithCustomNameAndKey
    iterator.hasNext shouldBe false

  }

  "DefineMessage MessageSet contains method" should "return true if a value is part of the set" in {

    val valueSet = TestMessages.values

    valueSet.contains(TestMessages.MyMessage1) shouldBe true
    valueSet.contains(TestMessages.MessageWithCustomKey) shouldBe true
    valueSet.contains(TestMessages.MyMessage2) shouldBe true
    valueSet.contains(TestMessages.MyMessage3) shouldBe true
    valueSet.contains(TestMessages.MessageWithDefaultNameAndKey) shouldBe true
    valueSet.contains(TestMessages.MessageWithCustomNameAndKey) shouldBe true

  }

  "DefineMessage Message equals method" should "return true for same values" in {

    val value1 = TestMessages.MessageWithDefaultNameAndKey
    val value2 = TestMessages.MessageWithCustomKey

    (value1 == TestMessages.MessageWithDefaultNameAndKey) should be (true)
    (value2 == TestMessages.MessageWithCustomKey) should be (true)

    (value1 != TestMessages.MessageWithDefaultNameAndKey) should be (false)
    (value2 != TestMessages.MessageWithCustomKey) should be (false)

  }

  "DefineMessage Message equals method" should "return false for different values" in {

    val value1 = TestMessages.MessageWithDefaultNameAndKey
    val value2 = TestMessages.MessageWithCustomKey

    (value1 == TestMessages.MessageWithCustomKey) should be (false)
    (value2 == TestMessages.MessageWithDefaultNameAndKey) should be (false)

    (value1 != TestMessages.MessageWithCustomKey) should be (true)
    (value2 != TestMessages.MessageWithDefaultNameAndKey) should be (true)

    (value1 == DialogMessages.DialogCloseButton) should be (false)
    (value2 == DialogMessages.DialogCloseButton) should be (false)

    (value1 != DialogMessages.DialogCloseButton) should be (true)
    (value2 != DialogMessages.DialogCloseButton) should be (true)

  }

  "Messages of different DefineMessages" should "never be equal" in {

    (TestMessages.MessageWithDefaultNameAndKey == OtherMessages.MessageWithDefaultNameAndKey) should be (false)
    (TestMessages.MessageWithCustomKey == OtherMessages.MessageWithCustomKey) should be (false)

    (TestMessages.MessageWithDefaultNameAndKey != OtherMessages.MessageWithDefaultNameAndKey) should be (true)
    (TestMessages.MessageWithCustomKey != OtherMessages.MessageWithCustomKey) should be (true)
  }

  "DefineMessage Message equals object of other type" should "return false" in {

    DialogMessages.DialogTitle == "DialogTitle" should be (false)
    DialogMessages.DialogTitle == 0 should be (false)

  }

  "DefineMessage Message compare method" should "compare message by their ordinal in order of their definition" in {

    import DialogMessages._

    DialogTitle.compare(DialogCloseButton) should be < 0
    DialogMessage.compare(DialogCloseButton) should be > 0

  }

  "DefineMessage values" should "all have different hash values" in {

    TestMessages.values.map(_.##).toSet.size shouldBe TestMessages.values.size

  }

  "DefineMessage" should "support messages with up to 9 parameters" in {

    import AllPossibleMessageVariations._

    MyMessage0.id shouldBe 0
    MyMessage0.name shouldBe "MyMessage0"
    MyMessage0.key shouldBe "my.message0"

    MyMessage1.id shouldBe 1
    MyMessage1.name shouldBe "MyMessage1"
    MyMessage1.key shouldBe "my.message1"

    MyMessage2.id shouldBe 2
    MyMessage2.name shouldBe "MyMessage2"
    MyMessage2.key shouldBe "my.message2"

    MyMessage3.id shouldBe 3
    MyMessage3.name shouldBe "MyMessage3"
    MyMessage3.key shouldBe "my.message3"

    MyMessage4.id shouldBe 4
    MyMessage4.name shouldBe "MyMessage4"
    MyMessage4.key shouldBe "my.message4"

    MyMessage5.id shouldBe 5
    MyMessage5.name shouldBe "MyMessage5"
    MyMessage5.key shouldBe "my.message5"

    MyMessage6.id shouldBe 6
    MyMessage6.name shouldBe "MyMessage6"
    MyMessage6.key shouldBe "my.message6"

    MyMessage7.id shouldBe 7
    MyMessage7.name shouldBe "MyMessage7"
    MyMessage7.key shouldBe "my.message7"

    MyMessage8.id shouldBe 8
    MyMessage8.name shouldBe "MyMessage8"
    MyMessage8.key shouldBe "my.message8"

    MyMessage9.id shouldBe 9
    MyMessage9.name shouldBe "MyMessage9"
    MyMessage9.key shouldBe "my.message9"

  }

  it should "support messages with custom key and up to 9 parameters" in {

    import AllPossibleMessageVariations._

    CustomKeyMessage0.id shouldBe 10
    CustomKeyMessage0.name shouldBe "CustomKeyMessage0"
    CustomKeyMessage0.key shouldBe "ck__message0"

    CustomKeyMessage1.id shouldBe 11
    CustomKeyMessage1.name shouldBe "CustomKeyMessage1"
    CustomKeyMessage1.key shouldBe "ck__message1"

    CustomKeyMessage2.id shouldBe 12
    CustomKeyMessage2.name shouldBe "CustomKeyMessage2"
    CustomKeyMessage2.key shouldBe "ck__message2"

    CustomKeyMessage3.id shouldBe 13
    CustomKeyMessage3.name shouldBe "CustomKeyMessage3"
    CustomKeyMessage3.key shouldBe "ck__message3"

    CustomKeyMessage4.id shouldBe 14
    CustomKeyMessage4.name shouldBe "CustomKeyMessage4"
    CustomKeyMessage4.key shouldBe "ck__message4"

    CustomKeyMessage5.id shouldBe 15
    CustomKeyMessage5.name shouldBe "CustomKeyMessage5"
    CustomKeyMessage5.key shouldBe "ck__message5"

    CustomKeyMessage6.id shouldBe 16
    CustomKeyMessage6.name shouldBe "CustomKeyMessage6"
    CustomKeyMessage6.key shouldBe "ck__message6"

    CustomKeyMessage7.id shouldBe 17
    CustomKeyMessage7.name shouldBe "CustomKeyMessage7"
    CustomKeyMessage7.key shouldBe "ck__message7"

    CustomKeyMessage8.id shouldBe 18
    CustomKeyMessage8.name shouldBe "CustomKeyMessage8"
    CustomKeyMessage8.key shouldBe "ck__message8"

    CustomKeyMessage9.id shouldBe 19
    CustomKeyMessage9.name shouldBe "CustomKeyMessage9"
    CustomKeyMessage9.key shouldBe "ck__message9"

  }

  it should "support messages with custom key, custom name and up to 9 parameters" in {

    import AllPossibleMessageVariations._

    AllCustomMessage0.id shouldBe 20
    AllCustomMessage0.name shouldBe "acMessage0"
    AllCustomMessage0.key shouldBe "ac__message0"

    AllCustomMessage1.id shouldBe 21
    AllCustomMessage1.name shouldBe "acMessage1"
    AllCustomMessage1.key shouldBe "ac__message1"

    AllCustomMessage2.id shouldBe 22
    AllCustomMessage2.name shouldBe "acMessage2"
    AllCustomMessage2.key shouldBe "ac__message2"

    AllCustomMessage3.id shouldBe 23
    AllCustomMessage3.name shouldBe "acMessage3"
    AllCustomMessage3.key shouldBe "ac__message3"

    AllCustomMessage4.id shouldBe 24
    AllCustomMessage4.name shouldBe "acMessage4"
    AllCustomMessage4.key shouldBe "ac__message4"

    AllCustomMessage5.id shouldBe 25
    AllCustomMessage5.name shouldBe "acMessage5"
    AllCustomMessage5.key shouldBe "ac__message5"

    AllCustomMessage6.id shouldBe 26
    AllCustomMessage6.name shouldBe "acMessage6"
    AllCustomMessage6.key shouldBe "ac__message6"

    AllCustomMessage7.id shouldBe 27
    AllCustomMessage7.name shouldBe "acMessage7"
    AllCustomMessage7.key shouldBe "ac__message7"

    AllCustomMessage8.id shouldBe 28
    AllCustomMessage8.name shouldBe "acMessage8"
    AllCustomMessage8.key shouldBe "ac__message8"

    AllCustomMessage9.id shouldBe 29
    AllCustomMessage9.name shouldBe "acMessage9"
    AllCustomMessage9.key shouldBe "ac__message9"

  }

}
