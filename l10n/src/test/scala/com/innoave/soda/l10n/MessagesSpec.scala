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

private object DemoMessages extends Messages {

  val MessageWithDefaultNameAndKey = Message0
  val Message1, Message2, Message3 = Message0
  val MessageWithCustomKey = Message0("MESSAGE_WITH_CUSTOM_KEY")
  val MessageWithCustomNameAndKey = Message0("CustomName", "custom_key")

}

private object MessagesWithCustomBundleBaseName extends Messages {

  override val BundleName = "custom_messages"

  val Message1, Message2, Message3 = Message0

}

class MessagesSpec extends FlatSpec with Matchers {

  "Messages#toString" should "return an appropriate string" in {

    DemoMessages.toString() shouldBe "DemoMessages"

  }

  "Messages with default BundleBaseName" should "return the simple object name" in {

    DemoMessages.BundleName shouldBe "DemoMessages"

  }

  "Messages with custom BundleBaseName" should "return the specified bundle base name" in {

    MessagesWithCustomBundleBaseName.BundleName shouldBe "custom_messages"

  }

  "Messages values with no given name nor key" should "have default name and key" in {

    DemoMessages.MessageWithDefaultNameAndKey.id shouldBe 0
    DemoMessages.MessageWithDefaultNameAndKey.name() shouldBe "MessageWithDefaultNameAndKey"
    DemoMessages.MessageWithDefaultNameAndKey.key() shouldBe "message.with.default.name.and.key"

  }

  "Messages with multiple values defined at once" should "be separat values" in {

    DemoMessages.Message1.id shouldBe 1
    DemoMessages.Message2.id shouldBe 2
    DemoMessages.Message3.id shouldBe 3

    DemoMessages.Message1.name shouldBe "Message1"
    DemoMessages.Message2.name shouldBe "Message2"
    DemoMessages.Message3.name shouldBe "Message3"

    DemoMessages.Message1.key shouldBe "message1"
    DemoMessages.Message2.key shouldBe "message2"
    DemoMessages.Message3.key shouldBe "message3"

  }

  "Messages values toString method" should "return the appropriate string" in {

    DemoMessages.MessageWithDefaultNameAndKey.toString shouldBe "DemoMessages#MessageWithDefaultNameAndKey(message.with.default.name.and.key)"
    DemoMessages.Message1.toString shouldBe "DemoMessages#Message1(message1)"
    DemoMessages.Message2.toString shouldBe "DemoMessages#Message2(message2)"
    DemoMessages.Message3.toString shouldBe "DemoMessages#Message3(message3)"

  }

  "Messages value with custom key" should "be defined with custom key" in {

    DemoMessages.MessageWithCustomKey.id shouldBe 4
    DemoMessages.MessageWithCustomKey.name shouldBe "MessageWithCustomKey"
    DemoMessages.MessageWithCustomKey.key shouldBe "MESSAGE_WITH_CUSTOM_KEY"

  }

  "Messages value with custom name and key" should "be defined with custom name and key" in {

    DemoMessages.MessageWithCustomNameAndKey.id shouldBe 5
    DemoMessages.MessageWithCustomNameAndKey.name shouldBe "CustomName"
    DemoMessages.MessageWithCustomNameAndKey.key shouldBe "custom_key"

  }

  "Messages values method" should "return a ValueSet containing all of its values" in {

    DemoMessages.values should contain (DemoMessages.MessageWithDefaultNameAndKey)
    DemoMessages.values should contain (DemoMessages.Message1)
    DemoMessages.values should contain (DemoMessages.Message2)
    DemoMessages.values should contain (DemoMessages.Message3)
    DemoMessages.values should contain (DemoMessages.MessageWithCustomKey)
    DemoMessages.values should contain (DemoMessages.MessageWithCustomNameAndKey)

    DemoMessages.values.size shouldBe 6

  }

  "Messages value set toString method" should "return an appropriate string" in {

    DemoMessages.values.toString should be ("""|
        |DemoMessages.ValueSet(
        |DemoMessages#MessageWithDefaultNameAndKey(message.with.default.name.and.key)
        |, DemoMessages#Message1(message1)
        |, DemoMessages#Message2(message2)
        |, DemoMessages#Message3(message3)
        |, DemoMessages#MessageWithCustomKey(MESSAGE_WITH_CUSTOM_KEY)
        |, DemoMessages#CustomName(custom_key)
        |)""".stripMargin.replace("\n", ""))

  }

  "Messages range of value set" should "return a value set of the specified range" in {

    val valueSetRange = DemoMessages.values.range(DemoMessages.Message1, DemoMessages.Message3)

    valueSetRange should contain (DemoMessages.Message1)
    valueSetRange should contain (DemoMessages.Message2)
    valueSetRange should not contain (DemoMessages.Message3)

    valueSetRange.size shouldBe 2

  }

  "Messages value + method" should "construct a value set of 2 values" in {

    import DemoMessages._

    val valueSet = MessageWithDefaultNameAndKey + MessageWithCustomNameAndKey

    valueSet should contain (DemoMessages.MessageWithDefaultNameAndKey)
    valueSet should contain (DemoMessages.MessageWithCustomNameAndKey)
    valueSet should not contain (DemoMessages.Message1)
    valueSet should not contain (DemoMessages.Message2)
    valueSet should not contain (DemoMessages.Message3)
    valueSet should not contain (DemoMessages.MessageWithCustomKey)

    valueSet.size shouldBe 2

  }

  "Messages ValueSet empty method" should "construct an empty value set" in {

    val emptySet = DemoMessages.MessageSet.empty

    emptySet.size shouldBe 0

  }

  "Messages ValueSet apply method" should "construct a value set with given values" in {

    val valueSet = DemoMessages.MessageSet(DemoMessages.Message1, DemoMessages.Message3, DemoMessages.MessageWithCustomKey)

    valueSet should contain (DemoMessages.Message1)
    valueSet should contain (DemoMessages.Message3)
    valueSet should contain (DemoMessages.MessageWithCustomKey)

    valueSet.size shouldBe 3

  }

  "Messages value set + method" should "add a value to the value set" in {

    val set0 = DemoMessages.MessageSet.empty
    val set1 = set0 + DemoMessages.MessageWithDefaultNameAndKey + DemoMessages.MessageWithCustomNameAndKey
    val set2 = set1 + DemoMessages.Message1
    val set3 = set2 + DemoMessages.MessageWithCustomKey

    set1 should contain (DemoMessages.MessageWithDefaultNameAndKey)
    set1 should contain (DemoMessages.MessageWithCustomNameAndKey)
    set1 should not contain (DemoMessages.Message1)
    set1 should not contain (DemoMessages.Message2)
    set1 should not contain (DemoMessages.Message3)
    set1 should not contain (DemoMessages.MessageWithCustomKey)
    set1.size shouldBe 2

    set2 should contain (DemoMessages.MessageWithDefaultNameAndKey)
    set2 should contain (DemoMessages.MessageWithCustomNameAndKey)
    set2 should contain (DemoMessages.Message1)
    set2 should not contain (DemoMessages.Message2)
    set2 should not contain (DemoMessages.Message3)
    set2 should not contain (DemoMessages.MessageWithCustomKey)
    set2.size shouldBe 3

    set3 should contain (DemoMessages.MessageWithDefaultNameAndKey)
    set3 should contain (DemoMessages.MessageWithCustomNameAndKey)
    set3 should contain (DemoMessages.Message1)
    set3 should not contain (DemoMessages.Message2)
    set3 should not contain (DemoMessages.Message3)
    set3 should contain (DemoMessages.MessageWithCustomKey)
    set3.size shouldBe 4

  }

  "Messages value set - method" should "remove a value from the value set" in {

    val set1 = DemoMessages.values
    val set2 = set1 - DemoMessages.MessageWithDefaultNameAndKey
    val set3 = set2 - DemoMessages.Message2
    val set4 = set3 - DemoMessages.MessageWithCustomNameAndKey

    set1 should contain (DemoMessages.MessageWithDefaultNameAndKey)
    set1 should contain (DemoMessages.MessageWithCustomNameAndKey)
    set1 should contain (DemoMessages.Message1)
    set1 should contain (DemoMessages.Message2)
    set1 should contain (DemoMessages.Message3)
    set1 should contain (DemoMessages.MessageWithCustomKey)
    set1.size shouldBe 6

    set2 should not contain (DemoMessages.MessageWithDefaultNameAndKey)
    set2 should contain (DemoMessages.MessageWithCustomNameAndKey)
    set2 should contain (DemoMessages.Message1)
    set2 should contain (DemoMessages.Message2)
    set2 should contain (DemoMessages.Message3)
    set2 should contain (DemoMessages.MessageWithCustomKey)
    set2.size shouldBe 5

    set3 should not contain (DemoMessages.MessageWithDefaultNameAndKey)
    set3 should contain (DemoMessages.MessageWithCustomNameAndKey)
    set3 should contain (DemoMessages.Message1)
    set3 should not contain (DemoMessages.Message2)
    set3 should contain (DemoMessages.Message3)
    set3 should contain (DemoMessages.MessageWithCustomKey)
    set3.size shouldBe 4

    set4 should not contain (DemoMessages.MessageWithDefaultNameAndKey)
    set4 should not contain (DemoMessages.MessageWithCustomNameAndKey)
    set4 should contain (DemoMessages.Message1)
    set4 should not contain (DemoMessages.Message2)
    set4 should contain (DemoMessages.Message3)
    set4 should contain (DemoMessages.MessageWithCustomKey)
    set4.size shouldBe 3

  }

  "Messages value set iterator" should "iterate in the order of the definition of the values" in {

    val iterator = DemoMessages.values.iterator

    iterator.next shouldBe DemoMessages.MessageWithDefaultNameAndKey
    iterator.next shouldBe DemoMessages.Message1
    iterator.next shouldBe DemoMessages.Message2
    iterator.next shouldBe DemoMessages.Message3
    iterator.next shouldBe DemoMessages.MessageWithCustomKey
    iterator.next shouldBe DemoMessages.MessageWithCustomNameAndKey

  }

  "Messages value equals method" should "return true for same values" in {

    val value1 = DemoMessages.MessageWithDefaultNameAndKey
    val value2 = DemoMessages.MessageWithCustomKey

    (value1 == DemoMessages.MessageWithDefaultNameAndKey) should be (true)
    (value2 == DemoMessages.MessageWithCustomKey) should be (true)

    (value1 != DemoMessages.MessageWithDefaultNameAndKey) should be (false)
    (value2 != DemoMessages.MessageWithCustomKey) should be (false)

  }

  "Messages value equals method" should "return false for different values" in {

    val value1 = DemoMessages.MessageWithDefaultNameAndKey
    val value2 = DemoMessages.MessageWithCustomKey

    (value1 == DemoMessages.MessageWithCustomKey) should be (false)
    (value2 == DemoMessages.MessageWithDefaultNameAndKey) should be (false)

    (value1 != DemoMessages.MessageWithCustomKey) should be (true)
    (value2 != DemoMessages.MessageWithDefaultNameAndKey) should be (true)

  }

  "Messages values" should "all have different hash values" in {

    DemoMessages.values.map(_.##).toSet.size shouldBe DemoMessages.values.size

  }

}