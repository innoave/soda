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
package com.innoave.soda.l10n.resource

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import com.innoave.soda.l10n.BundleName
import com.innoave.soda.l10n.DemoTypes._
import com.innoave.soda.l10n.DemoTypesLocalizer
import com.innoave.soda.l10n.DemoTypesLocalizer._
import com.innoave.soda.l10n.Locale
import com.innoave.soda.l10n.Locale._
import com.innoave.soda.l10n.TestMessages._
import java.util.MissingResourceException
import org.scalatest.BeforeAndAfterAll

class JavaUtf8ResourceBundleSpec extends FlatSpec with Matchers with BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    //set default Locale to language for which there is no resource file available
    //only needed for tests!!!
    Locale.default = Locale("XX")
  }

  def bundleFor(bundleName: BundleName, locale: Locale): ResourceBundle =
    new JavaUtf8ResourceBundle(bundleName, locale)


  "ResourceBundle" should "return a pattern string for given message" in {

    bundleFor(helloWorld.bundleName, EN).stringFor(helloWorld) shouldBe "Hello World!"
    bundleFor(greeting.bundleName, EN).stringFor(greeting) shouldBe "Greetings to {0}"
    bundleFor(greeting.bundleName, DE_AT).stringFor(greeting) shouldBe "Grüß Gott {0}"

  }

  it should "return a pattern string for given localized" in {

    val card = Card(Spades, Jack)

    bundleFor(DemoTypesLocalizer.bundleName, EN).stringFor(card) shouldBe "{1} of {0}"
    bundleFor(DemoTypesLocalizer.bundleName, DE_AT).stringFor(card) shouldBe "{0} {1}"

  }

  it should "return the key surrounded by exclamation marks if the key is not found in the bundle" in {

    bundleFor(DemoTypesLocalizer.bundleName, EN).stringFor(helloWorld) shouldBe "!!!hello.world!!!"

  }

  it should "return the bundle for the default locale if there is no bundle for the given locale" in {

    Locale.default = DE

    bundleFor(greeting.bundleName, Locale("mm")).stringFor(greeting) shouldBe "Guten Tag {0}"

  }

  it should "return the default bundle if there is no bundle for the given locale and no bundle for the default locale" in {

    Locale.default = Locale("XX")

    bundleFor(greeting.bundleName, Locale("mm")).stringFor(greeting) shouldBe "Hello {0}"

  }

  it should "through an MissingResourceException if there is no bundle for the given BundleName" in {

    the [MissingResourceException] thrownBy {

      bundleFor(BundleName("ooooops"), EN)

    } should have message "Can't find bundle for base name ooooops, locale en"

  }

}
