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

object DefineLocalizedWithDefaultBundleName extends DefineLocalized {

}

class DefineLocalizedSpec extends FlatSpec with Matchers {

  "DefineLocalized#toString" should "return an appropriate string" in {

    DefineLocalizedWithDefaultBundleName.toString() shouldBe "DefineLocalizedWithDefaultBundleName"

  }

  "DefineLocalized with default BundleName" should "return the simple object name" in {

    DefineLocalizedWithDefaultBundleName.bundleName shouldBe BundleName("DefineLocalizedWithDefaultBundleName")

  }

  "DefineMessage with custom BundleName" should "return the specified bundle base name" in {

    DemoTypesLocalizer.bundleName shouldBe BundleName("l10n.demotypes")

  }

}
