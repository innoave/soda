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

import scala.reflect.classTag
import scala.collection.JavaConverters._
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.mockito.MockitoSugar
import org.mockito.ArgumentCaptor
import org.mockito.Mockito._
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.{Logger => LbLogger}
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender

private class MyServiceClass
private class MyCodecClass

trait LoggerHelper {

  implicit class LevelSetter(logger: Logger) {
    def setLevel(level: Level): Unit = {
      logger.underlying.asInstanceOf[LbLogger].setLevel(level)
    }
  }

}

class LoggerSpec extends FlatSpec with Matchers with MockitoSugar with LoggerHelper {

  def withCapturingAppender(testCode: (Appender[ILoggingEvent], ArgumentCaptor[ILoggingEvent]) => Any): Unit = {
    val mockAppender = mock[Appender[ILoggingEvent]]
    val captorLoggingEvent = ArgumentCaptor.forClass(classOf[ILoggingEvent])
    val logger = Logger.rootLogger
    logger.underlying.asInstanceOf[LbLogger].addAppender(mockAppender)
    try {
      testCode(mockAppender, captorLoggingEvent)
    }
    finally {
      val logger = Logger.rootLogger
      logger.underlying.asInstanceOf[LbLogger].detachAppender(mockAppender)
    }
  }

  "The root logger" should "have a logger the predefined logger name" in {

    Logger.rootLogger.name() shouldBe Logger.RootLoggerName

    Logger(Logger.RootLoggerName).name() shouldBe org.slf4j.Logger.ROOT_LOGGER_NAME

  }

  "A Logger for given name" should "log events under this name" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("Test101")

    log.info("Hello World!")

    verify(mockAppender).doAppend(captorLoggingEvent.capture())
    val loggingEvent = captorLoggingEvent.getValue

    log.name() shouldBe "Test101"
    loggingEvent.getLoggerName shouldBe "Test101"

  }

  "A Logger for a given class" should "log events under the full qualified name of the given class" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger(classOf[MyServiceClass])

    log.info("Hello World!")

    verify(mockAppender).doAppend(captorLoggingEvent.capture())
    val loggingEvent = captorLoggingEvent.getValue

    log.name() shouldBe "com.innoave.soda.logging.MyServiceClass"
    loggingEvent.getLoggerName shouldBe "com.innoave.soda.logging.MyServiceClass"

  }

  "A Logger for this class" should "log events under the full qualified name of this class" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    implicit val classtag = classTag[MyCodecClass]

    val log = Logger()

    log.info("Hello World!")

    verify(mockAppender).doAppend(captorLoggingEvent.capture())
    val loggingEvent = captorLoggingEvent.getValue

    log.name() shouldBe "com.innoave.soda.logging.MyCodecClass"
    loggingEvent.getLoggerName shouldBe "com.innoave.soda.logging.MyCodecClass"

  }

  "A Logger with Level set to Error" should "log a message for level Error only" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.ERROR)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null

  }

  it should "log a message and an exception for level Error only" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.ERROR)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(0).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

  }

  "A Logger with Level set to Warn" should "log a message for level Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.WARN)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null

  }

  it should "log a message and an exception for level Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.WARN)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(0).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(1).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

  }

  "A Logger with Level set to Info" should "log a message for level Info, Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.INFO)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(3)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null

    loggingEvents(2).getLoggerName shouldBe "InfoTest"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy shouldBe null

  }

  it should "log a message and an exception for level Info, Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.INFO)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    verify(mockAppender, times(3)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(0).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(1).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(2).getLoggerName shouldBe "InfoTest"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(2).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

  }

  "A Logger with Level set to Debug" should "log a message for level Debug, Info, Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.DEBUG)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(4)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null

    loggingEvents(2).getLoggerName shouldBe "InfoTest"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy shouldBe null

    loggingEvents(3).getLoggerName shouldBe "InfoTest"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "I did it"
    loggingEvents(3).getThrowableProxy shouldBe null

  }

  it should "log a message and an exception for level Debug, Info, Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.DEBUG)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    verify(mockAppender, times(4)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(0).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(1).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(2).getLoggerName shouldBe "InfoTest"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(2).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(3).getLoggerName shouldBe "InfoTest"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "I did it"
    loggingEvents(3).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(3).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

  }

  "A Logger with Level set to Trace" should "log a message for level Trace, Debug, Info, Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.TRACE)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(5)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null

    loggingEvents(2).getLoggerName shouldBe "InfoTest"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy shouldBe null

    loggingEvents(3).getLoggerName shouldBe "InfoTest"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "I did it"
    loggingEvents(3).getThrowableProxy shouldBe null

    loggingEvents(4).getLoggerName shouldBe "InfoTest"
    loggingEvents(4).getLevel shouldBe Level.TRACE
    loggingEvents(4).getMessage shouldBe "Here are all the details"
    loggingEvents(4).getThrowableProxy shouldBe null

  }

  it should "log a message and an exception for level Trace, Debug, Info, Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.TRACE)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    verify(mockAppender, times(5)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(0).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(1).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(2).getLoggerName shouldBe "InfoTest"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(2).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(3).getLoggerName shouldBe "InfoTest"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "I did it"
    loggingEvents(3).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(3).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(4).getLoggerName shouldBe "InfoTest"
    loggingEvents(4).getLevel shouldBe Level.TRACE
    loggingEvents(4).getMessage shouldBe "Here are all the details"
    loggingEvents(4).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(4).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

  }

  "A Logger with Level set to All" should "log a message for level Trace, Debug, Info, Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.ALL)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(5)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null

    loggingEvents(2).getLoggerName shouldBe "InfoTest"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy shouldBe null

    loggingEvents(3).getLoggerName shouldBe "InfoTest"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "I did it"
    loggingEvents(3).getThrowableProxy shouldBe null

    loggingEvents(4).getLoggerName shouldBe "InfoTest"
    loggingEvents(4).getLevel shouldBe Level.TRACE
    loggingEvents(4).getMessage shouldBe "Here are all the details"
    loggingEvents(4).getThrowableProxy shouldBe null

  }

  it should "log a message and an exception for level Trace, Debug, Info, Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.ALL)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    verify(mockAppender, times(5)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(0).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(1).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(2).getLoggerName shouldBe "InfoTest"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(2).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(3).getLoggerName shouldBe "InfoTest"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "I did it"
    loggingEvents(3).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(3).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(4).getLoggerName shouldBe "InfoTest"
    loggingEvents(4).getLevel shouldBe Level.TRACE
    loggingEvents(4).getMessage shouldBe "Here are all the details"
    loggingEvents(4).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(4).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

  }

}
