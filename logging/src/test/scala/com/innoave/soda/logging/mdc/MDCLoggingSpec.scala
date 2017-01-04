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
package com.innoave.soda.logging.mdc

import scala.collection.JavaConverters._
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalamock.scalatest.MockFactory
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.{Logger => LbLogger}
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender
import ch.qos.logback.classic.{Logger => LbLogger}
import com.innoave.soda.logging.LogbackHelper
import com.innoave.soda.logging.Logger
import com.innoave.soda.logging.MDC

class MDCLoggingSpec extends FlatSpec with Matchers with MockFactory with LogbackHelper {

  def withMockAppender(testCode: (Appender[ILoggingEvent]) => Any): Unit = {
    val mockAppender = stub[Appender[ILoggingEvent]]
    val logger = Logger.rootLogger.delegate.asInstanceOf[LbLogger]
    logger.addAppender(mockAppender)
    MDC.clear()
    try {
      testCode(mockAppender)
    }
    finally {
      val logger = Logger.rootLogger.delegate.asInstanceOf[LbLogger]
      logger.detachAppender(mockAppender)
    }
  }

  "A Logger in MDC" should "log additional info from the MDC" in
      withMockAppender { (mockAppender) =>

    val log = Logger("MdcTestLoggerA")
    log.setLevel(Level.WARN)

    MDC.put("host", "somehost")
    MDC.put("actor", "theusualone")

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MdcTestLoggerA" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy == null &&
        loggingEvent.getMDCPropertyMap.asScala.toMap == Map("host" -> "somehost", "actor" -> "theusualone") &&
        loggingEvent.getMDCPropertyMap.size() == 2
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MdcTestLoggerA" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy == null &&
        loggingEvent.getMDCPropertyMap.asScala.toMap == Map("host" -> "somehost", "actor" -> "theusualone") &&
        loggingEvent.getMDCPropertyMap.size() == 2
      })

    }

  }

  it should "not log removed properties of the MDC" in
      withMockAppender { (mockAppender) =>

    val log = Logger("MdcTestLoggerB")
    log.setLevel(Level.WARN)

    MDC.put("host", "somehost")
    MDC.put("actor", "theusualone")

    log.error("Oh what an error")

    MDC.remove("host")

    log.warn("I've warned you")

    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MdcTestLoggerB" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy == null &&
        loggingEvent.getMDCPropertyMap.asScala.toMap == Map("host" -> "somehost", "actor" -> "theusualone") &&
        loggingEvent.getMDCPropertyMap.size() == 2
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MdcTestLoggerB" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy == null &&
        loggingEvent.getMDCPropertyMap.asScala.toMap == Map("actor" -> "theusualone") &&
        loggingEvent.getMDCPropertyMap.size() == 1
      })

    }

  }

  it should "not log closed properties of the MDC" in
      withMockAppender { (mockAppender) =>

    val log = Logger("MdcTestLoggerC")
    log.setLevel(Level.WARN)

    MDC.put("host", "somehost")
    val actor = MDC.putCloseable("actor", "theusualone")

    log.error("Oh what an error")

    actor.close()

    log.warn("I've warned you")

    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MdcTestLoggerC" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy == null &&
        loggingEvent.getMDCPropertyMap.asScala.toMap == Map("host" -> "somehost", "actor" -> "theusualone") &&
        loggingEvent.getMDCPropertyMap.size() == 2
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MdcTestLoggerC" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy == null &&
        loggingEvent.getMDCPropertyMap.asScala.toMap == Map("host" -> "somehost") &&
        loggingEvent.getMDCPropertyMap.size() == 1
      })

    }

  }

  it should "log additional info which replaces the previous one from the MDC" in
      withMockAppender { (mockAppender) =>

    val log = Logger("MdcTestLoggerD")
    log.setLevel(Level.WARN)

    MDC.put("host", "somehost")
    MDC.put("actor", "theusualone")

    MDC.contextMap = Map("host1" -> "someotherhost", "actor1" -> "theotherone")

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MdcTestLoggerD" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy == null &&
        loggingEvent.getMDCPropertyMap.asScala.toMap == Map("host1" -> "someotherhost", "actor1" -> "theotherone") &&
        loggingEvent.getMDCPropertyMap.size() == 2
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MdcTestLoggerD" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy == null &&
        loggingEvent.getMDCPropertyMap.asScala.toMap == Map("host1" -> "someotherhost", "actor1" -> "theotherone") &&
        loggingEvent.getMDCPropertyMap.size() == 2
      })

    }

  }

  it should "log no additional info from a cleared MDC" in
      withMockAppender { (mockAppender) =>

    val log = Logger("MdcTestLoggerE")
    log.setLevel(Level.WARN)

    MDC.put("host", "somehost")
    MDC.put("actor", "theusualone")

    MDC.clear()

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MdcTestLoggerE" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy == null &&
        loggingEvent.getMDCPropertyMap.asScala.toMap == Map() &&
        loggingEvent.getMDCPropertyMap.size() == 0
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MdcTestLoggerE" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy == null &&
        loggingEvent.getMDCPropertyMap.asScala.toMap == Map() &&
        loggingEvent.getMDCPropertyMap.size() == 0
      })

    }

  }

}
