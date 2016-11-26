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

import scala.collection.JavaConverters._
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.mockito.Mockito._
import ch.qos.logback.classic.Level

class MDCLoggingSpec extends FlatSpec with Matchers with LogbackHelper {

  "A Logger in MDC" should "log additional info from the MDC" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("MdcTestLogger")
    log.setLevel(Level.WARN)

    MDC.put("host", "somehost")
    MDC.put("actor", "theusualone")

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "MdcTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null
    loggingEvents(0).getMDCPropertyMap.asScala should contain allOf ("host" -> "somehost", "actor" -> "theusualone")
    loggingEvents(1).getMDCPropertyMap.size() shouldBe 2

    loggingEvents(1).getLoggerName shouldBe "MdcTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null
    loggingEvents(1).getMDCPropertyMap.asScala should contain allOf ("host" -> "somehost", "actor" -> "theusualone")
    loggingEvents(1).getMDCPropertyMap.size() shouldBe 2

    MDC.clear()

  }

  it should "not log removed properties of the MDC" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("MdcTestLogger")
    log.setLevel(Level.WARN)

    MDC.put("host", "somehost")
    MDC.put("actor", "theusualone")

    log.error("Oh what an error")

    MDC.remove("host")

    log.warn("I've warned you")

    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "MdcTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null
    loggingEvents(0).getMDCPropertyMap.asScala should contain allOf ("host" -> "somehost", "actor" -> "theusualone")
    loggingEvents(0).getMDCPropertyMap.size() shouldBe 2

    loggingEvents(1).getLoggerName shouldBe "MdcTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null
    loggingEvents(1).getMDCPropertyMap.asScala should contain ("actor" -> "theusualone")
    loggingEvents(1).getMDCPropertyMap.size() shouldBe 1

    MDC.clear()

  }

  it should "not log closed properties of the MDC" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("MdcTestLogger")
    log.setLevel(Level.WARN)

    MDC.put("host", "somehost")
    val actor = MDC.putCloseable("actor", "theusualone")

    log.error("Oh what an error")

    actor.close()

    log.warn("I've warned you")

    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "MdcTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null
    loggingEvents(0).getMDCPropertyMap.asScala should contain allOf ("host" -> "somehost", "actor" -> "theusualone")
    loggingEvents(0).getMDCPropertyMap.size() shouldBe 2

    loggingEvents(1).getLoggerName shouldBe "MdcTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null
    loggingEvents(1).getMDCPropertyMap.asScala should contain ("host" -> "somehost")
    loggingEvents(1).getMDCPropertyMap.size() shouldBe 1

    MDC.clear()

  }

  it should "log additional info which replaces the previous one from the MDC" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("MdcTestLogger")
    log.setLevel(Level.WARN)

    MDC.put("host", "somehost")
    MDC.put("actor", "theusualone")

    MDC.contextMap = Map("host1" -> "someotherhost", "actor1" -> "theotherone")

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "MdcTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null
    loggingEvents(0).getMDCPropertyMap.asScala should contain allOf ("host1" -> "someotherhost", "actor1" -> "theotherone")
    loggingEvents(1).getMDCPropertyMap.size() shouldBe 2

    loggingEvents(1).getLoggerName shouldBe "MdcTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null
    loggingEvents(1).getMDCPropertyMap.asScala should contain allOf ("host1" -> "someotherhost", "actor1" -> "theotherone")
    loggingEvents(1).getMDCPropertyMap.size() shouldBe 2

    MDC.clear()

  }

  it should "log no additional info from a cleared MDC" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("MdcTestLogger")
    log.setLevel(Level.WARN)

    MDC.put("host", "somehost")
    MDC.put("actor", "theusualone")

    MDC.clear()

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "MdcTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null
    loggingEvents(1).getMDCPropertyMap.size() shouldBe 0

    loggingEvents(1).getLoggerName shouldBe "MdcTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null
    loggingEvents(1).getMDCPropertyMap.size() shouldBe 0

    MDC.clear()

  }

}
