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
import org.scalamock.scalatest.MockFactory
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.{Logger => LbLogger}
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.turbo.MarkerFilter
import ch.qos.logback.core.Appender

private class MyServiceClass
private class MyCodecClass

class LoggerSpec extends FlatSpec with Matchers with MockFactory with LogbackHelper {

  def withMockAppender(testCode: (Appender[ILoggingEvent]) => Any): Unit = {
    val mockAppender = stub[Appender[ILoggingEvent]]
    val logger = Logger.rootLogger.delegate.asInstanceOf[LbLogger]
    logger.addAppender(mockAppender)
    try {
      testCode(mockAppender)
    }
    finally {
      val logger = Logger.rootLogger.delegate.asInstanceOf[LbLogger]
      logger.detachAppender(mockAppender)
    }
  }

  "The root logger" should "have a logger the predefined logger name" in {

    Logger.rootLogger.name() shouldBe Logger.RootLoggerName

    Logger(Logger.RootLoggerName).name() shouldBe org.slf4j.Logger.ROOT_LOGGER_NAME

  }

  "The Logger object" should "return the slf4j.ILoggerFactory instance in use" in {

    Logger.underlyingFactory.getClass shouldBe classOf[ch.qos.logback.classic.LoggerContext]

  }

  "A Logger for given name" should "log events under this name" in
      withMockAppender { (mockAppender) =>

    val log = Logger("Test101")

    log.name() shouldBe "Test101"

    log.info("Hello World!")

    (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
      loggingEvent.getLoggerName == "Test101"
    })

  }

  "A Logger for a given class" should "log events under the full qualified name of the given class" in
      withMockAppender { (mockAppender) =>

    val log = Logger(classOf[MyServiceClass])

    log.name() shouldBe "com.innoave.soda.logging.MyServiceClass"

    log.info("Hello World!")

    (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
      loggingEvent.getLoggerName == "com.innoave.soda.logging.MyServiceClass"
    })

  }

  "A Logger for a given type" should "log events under the full qualified name of this type" in
      withMockAppender { (mockAppender) =>

    val log = Logger[MyCodecClass]

    log.name() shouldBe "com.innoave.soda.logging.MyCodecClass"

    log.info("Hello World!")

    (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
      loggingEvent.getLoggerName == "com.innoave.soda.logging.MyCodecClass"
    })

  }

  "A Logger with Level set to Error" should "log a message for level Error only" in
      withMockAppender { (mockAppender) =>

    val log = Logger("ErrorTestLogger")
    log.setLevel(Level.ERROR)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
      loggingEvent.getLoggerName == "ErrorTestLogger" &&
      loggingEvent.getLevel == Level.ERROR &&
      loggingEvent.getMessage == "Oh what an error" &&
      loggingEvent.getThrowableProxy == null
    })

  }

  it should "log a message and an exception for level Error only" in
      withMockAppender { (mockAppender) =>

    val log = Logger("ErrorTestLogger")
    log.setLevel(Level.ERROR)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
      loggingEvent.getLoggerName == "ErrorTestLogger" &&
      loggingEvent.getLevel == Level.ERROR &&
      loggingEvent.getMessage == "Oh what an error" &&
      loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
      loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
    })

  }

  "A Logger with Level set to Warn" should "log a message for level Warning and Error" in
      withMockAppender { (mockAppender) =>

    val log = Logger("WarnTestLogger")
    log.setLevel(Level.WARN)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "WarnTestLogger" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "WarnTestLogger" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy == null
      })

    }

  }

  it should "log a message and an exception for level Warning and Error" in
      withMockAppender { (mockAppender) =>

    val log = Logger("WarnTestLogger")
    log.setLevel(Level.WARN)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "WarnTestLogger" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "WarnTestLogger" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

    }

  }

  "A Logger with Level set to Info" should "log a message for level Info, Warning and Error" in
      withMockAppender { (mockAppender) =>

    val log = Logger("InfoTestLogger")
    log.setLevel(Level.INFO)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "InfoTestLogger" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "InfoTestLogger" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "InfoTestLogger" &&
        loggingEvent.getLevel == Level.INFO &&
        loggingEvent.getMessage == "Hello World" &&
        loggingEvent.getThrowableProxy == null
      })

    }

  }

  it should "log a message and an exception for level Info, Warning and Error" in
      withMockAppender { (mockAppender) =>

    val log = Logger("InfoTestLogger")
    log.setLevel(Level.INFO)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "InfoTestLogger" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "InfoTestLogger" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "InfoTestLogger" &&
        loggingEvent.getLevel == Level.INFO &&
        loggingEvent.getMessage == "Hello World" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

    }

  }

  "A Logger with Level set to Debug" should "log a message for level Debug, Info, Warning and Error" in
      withMockAppender { (mockAppender) =>

    val log = Logger("DebugTestLogger")
    log.setLevel(Level.DEBUG)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "DebugTestLogger" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "DebugTestLogger" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "DebugTestLogger" &&
        loggingEvent.getLevel == Level.INFO &&
        loggingEvent.getMessage == "Hello World" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "DebugTestLogger" &&
        loggingEvent.getLevel == Level.DEBUG &&
        loggingEvent.getMessage == "I did it" &&
        loggingEvent.getThrowableProxy == null
      })

    }

  }

  it should "log a message and an exception for level Debug, Info, Warning and Error" in
      withMockAppender { (mockAppender) =>

    val log = Logger("DebugTestLogger")
    log.setLevel(Level.DEBUG)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "DebugTestLogger" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "DebugTestLogger" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "DebugTestLogger" &&
        loggingEvent.getLevel == Level.INFO &&
        loggingEvent.getMessage == "Hello World" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "DebugTestLogger" &&
        loggingEvent.getLevel == Level.DEBUG &&
        loggingEvent.getMessage == "I did it" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

    }

  }

  "A Logger with Level set to Trace" should "log a message for level Trace, Debug, Info, Warning and Error" in
      withMockAppender { (mockAppender) =>

    val log = Logger("TraceTestLogger")
    log.setLevel(Level.TRACE)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "TraceTestLogger" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "TraceTestLogger" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "TraceTestLogger" &&
        loggingEvent.getLevel == Level.INFO &&
        loggingEvent.getMessage == "Hello World" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "TraceTestLogger" &&
        loggingEvent.getLevel == Level.DEBUG &&
        loggingEvent.getMessage == "I did it" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "TraceTestLogger" &&
        loggingEvent.getLevel == Level.TRACE &&
        loggingEvent.getMessage == "Here are all the details" &&
        loggingEvent.getThrowableProxy == null
      })

    }

  }

  it should "log a message and an exception for level Trace, Debug, Info, Warning and Error" in
      withMockAppender { (mockAppender) =>

    val log = Logger("TraceTestLogger")
    log.setLevel(Level.TRACE)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "TraceTestLogger" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "TraceTestLogger" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "TraceTestLogger" &&
        loggingEvent.getLevel == Level.INFO &&
        loggingEvent.getMessage == "Hello World" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "TraceTestLogger" &&
        loggingEvent.getLevel == Level.DEBUG &&
        loggingEvent.getMessage == "I did it" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "TraceTestLogger" &&
        loggingEvent.getLevel == Level.TRACE &&
        loggingEvent.getMessage == "Here are all the details" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

    }

  }

  "A Logger with Level set to All" should "log a message for level Trace, Debug, Info, Warning and Error" in
      withMockAppender { (mockAppender) =>

    val log = Logger("AllTestLogger")
    log.setLevel(Level.ALL)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "AllTestLogger" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "AllTestLogger" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "AllTestLogger" &&
        loggingEvent.getLevel == Level.INFO &&
        loggingEvent.getMessage == "Hello World" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "AllTestLogger" &&
        loggingEvent.getLevel == Level.DEBUG &&
        loggingEvent.getMessage == "I did it" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "AllTestLogger" &&
        loggingEvent.getLevel == Level.TRACE &&
        loggingEvent.getMessage == "Here are all the details" &&
        loggingEvent.getThrowableProxy == null
      })

    }

  }

  it should "log a message and an exception for level Trace, Debug, Info, Warning and Error" in
      withMockAppender { (mockAppender) =>

    val log = Logger("AllTestLogger")
    log.setLevel(Level.ALL)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "AllTestLogger" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "AllTestLogger" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "AllTestLogger" &&
        loggingEvent.getLevel == Level.INFO &&
        loggingEvent.getMessage == "Hello World" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "AllTestLogger" &&
        loggingEvent.getLevel == Level.DEBUG &&
        loggingEvent.getMessage == "I did it" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "AllTestLogger" &&
        loggingEvent.getLevel == Level.TRACE &&
        loggingEvent.getMessage == "Here are all the details" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

    }

  }

  "A Logger with marker filter and level set to info" should
      "log a message with marker for level Trace, Debug and Info only if the marker contains the filtered marker" in
      withMockAppender { (mockAppender) =>

    val log = Logger("MarkerTestLogger")
    log.setLevel(Level.INFO)
    val markerFilter = new MarkerFilter()
    markerFilter.setMarker("Timing")
    markerFilter.setOnMatch("NEUTRAL")
    markerFilter.setOnMismatch("DENY")
    markerFilter.start()
    log.addTurboFilter(markerFilter)

    val timingMarker = Marker("Timing")

    log.error(timingMarker, "Oh what an error")
    log.warn(timingMarker, "I've warned you")
    log.info(timingMarker, "Hello World")
    log.debug(timingMarker, "I did it")
    log.trace(timingMarker, "Here are all the details")

    val otherMarker = Marker("Other")

    log.error(otherMarker, "Oh what an error")
    log.warn(otherMarker, "I've warned you")
    log.info(otherMarker, "Hello World")
    log.debug(otherMarker, "I did it")
    log.trace(otherMarker, "Here are all the details")

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MarkerTestLogger" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MarkerTestLogger" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy == null
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MarkerTestLogger" &&
        loggingEvent.getLevel == Level.INFO &&
        loggingEvent.getMessage == "Hello World" &&
        loggingEvent.getThrowableProxy == null
      })

    }

  }

  it should "log a message and an exception with marker for level Trace, Debug and Info only if the marker contains the filtered marker" in
      withMockAppender { (mockAppender) =>

    val log = Logger("MarkerTestLogger")
    log.setLevel(Level.INFO)
    val markerFilter = new MarkerFilter()
    markerFilter.setMarker("Timing")
    markerFilter.setOnMatch("NEUTRAL")
    markerFilter.setOnMismatch("DENY")
    markerFilter.start()
    log.addTurboFilter(markerFilter)

    val timingMarker = Marker("Timing")

    log.error(timingMarker, "Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn(timingMarker, "I've warned you", new RuntimeException("Houston we have a problem"))
    log.info(timingMarker, "Hello World", new RuntimeException("Houston we have a problem"))
    log.debug(timingMarker, "I did it", new RuntimeException("Houston we have a problem"))
    log.trace(timingMarker, "Here are all the details", new RuntimeException("Houston we have a problem"))

    val otherMarker = Marker("Other")

    log.error(otherMarker, "Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn(otherMarker, "I've warned you", new RuntimeException("Houston we have a problem"))
    log.info(otherMarker, "Hello World", new RuntimeException("Houston we have a problem"))
    log.debug(otherMarker, "I did it", new RuntimeException("Houston we have a problem"))
    log.trace(otherMarker, "Here are all the details", new RuntimeException("Houston we have a problem"))

    inSequence {

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MarkerTestLogger" &&
        loggingEvent.getLevel == Level.ERROR &&
        loggingEvent.getMessage == "Oh what an error" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MarkerTestLogger" &&
        loggingEvent.getLevel == Level.WARN &&
        loggingEvent.getMessage == "I've warned you" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

      (mockAppender.doAppend _) verify (where { (loggingEvent: ILoggingEvent) =>
        loggingEvent.getLoggerName == "MarkerTestLogger" &&
        loggingEvent.getLevel == Level.INFO &&
        loggingEvent.getMessage == "Hello World" &&
        loggingEvent.getThrowableProxy.getClassName == "java.lang.RuntimeException" &&
        loggingEvent.getThrowableProxy.getMessage == "Houston we have a problem"
      })

    }

  }

}
