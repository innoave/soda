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
package com.innoave.soda.desktop

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import com.innoave.soda.desktop.OS.Brand._

class OSSpec extends FlatSpec with Matchers {

  def testOS(tname: String, tversion: String): OS =
    new OS {
      override def name: Option[String] = Some(tname)
      override def version: Option[String] = Some(tversion)
    }

  "OS.brand" should "return Unkown for Fantasy OS" in {

    testOS("Fantasy", "0").brand shouldBe Unknown

  }

  "OS.brand" should "return MsWin for Windows OS" in {

    testOS("Windows", "10.0.14393").brand shouldBe MsWin

  }

  "OS.brand" should "return MacOs for Mac OS" in {

    testOS("Mac OS X", "10.0.14393").brand shouldBe MacOs

  }

  "OS.brand" should "return Linux for Linux OS" in {

    testOS("Linux", "10.0.14393").brand shouldBe Linux

  }

  "OS.majorVersion" should "return None for Version ver1" in {

    testOS("Fantasy", "ver1").majorVersion shouldBe None

  }

  "OS.majorVersion" should "return 1 for Version v1" in {

    testOS("Fantasy", "v1").majorVersion shouldBe Some(1)

  }

  "OS.majorVersion" should "return 0 for Version 0" in {

    testOS("Fantasy", "0").majorVersion shouldBe Some(0)

  }

  "OS.majorVersion" should "return 10 for Version 10.0.14393" in {

    testOS("Windows", "10.0.14393").majorVersion shouldBe Some(10)

  }

}
