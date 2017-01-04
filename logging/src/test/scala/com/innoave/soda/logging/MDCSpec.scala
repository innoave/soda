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
package com.innoave.soda.logging

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class MDCSpec extends FlatSpec with Matchers {

  "MDC" should "contain added key/value pair" in {

    MDC.clear()
    MDC.put("clientId", "Susan")

    MDC.contextMap should contain ("clientId" -> "Susan")
    MDC.contextMap.size shouldBe 1

  }

  it should "not contain a removed key" in {

    MDC.clear()
    MDC.put("clientId", "Susan")
    MDC.put("token", "lkw91n2ir5on")

    MDC.contextMap should contain allOf ("clientId" -> "Susan", "token" -> "lkw91n2ir5on")
    MDC.contextMap.size shouldBe 2

    MDC.remove("clientId")

    MDC.contextMap should contain ("token" -> "lkw91n2ir5on")
    MDC.contextMap should not contain ("clientId" -> "Susan")
    MDC.contextMap.size shouldBe 1

  }

  it should "return the value for a given key" in {

    MDC.clear()
    MDC.put("clientId", "Susan")
    MDC.put("token", "lkw91n2ir5on")

    MDC.get("clientId") shouldBe Some("Susan")
    MDC.get("token") shouldBe Some("lkw91n2ir5on")

  }

  it should "return None if it does not contain the given key" in {

    MDC.clear()

    MDC.get("clientId") shouldBe None

  }

  it should "return update the MDC with the given optional value if it does not contain the given key" in {

    MDC.clear()

    MDC.get("clientId") shouldBe None
    MDC.getOrElseUpdate("clientId", "guest") shouldBe "guest"
    MDC.get("clientId") shouldBe Some("guest")

  }

  it should "contain no key/value pairs after clear" in {

    MDC.clear()
    MDC.put("clientId", "Susan")
    MDC.put("token", "lkw91n2ir5on")

    MDC.contextMap should contain allOf ("clientId" -> "Susan", "token" -> "lkw91n2ir5on")
    MDC.contextMap.size shouldBe 2

    MDC.clear()

    MDC.contextMap.isEmpty shouldBe true

  }

}
