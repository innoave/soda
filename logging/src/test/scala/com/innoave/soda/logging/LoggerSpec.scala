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

import scala.collection.JavaConversions._
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.mockito.ArgumentCaptor
import org.mockito.Mockito._
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.{Logger => LbLogger}
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender
import org.scalatest.mockito.MockitoSugar

private class MyServiceClass {

  val itsLogger = Logger()

}

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
    val logger = Logger(Logger.RootLoggerName)
    logger.underlying.asInstanceOf[LbLogger].addAppender(mockAppender)
    try {
      testCode(mockAppender, captorLoggingEvent)
    }
    finally {
      val logger = Logger(Logger.RootLoggerName)
      logger.underlying.asInstanceOf[LbLogger].detachAppender(mockAppender)
    }
  }

  "A Logger for given name" should "log events under this name" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("Test101")

    log.error("Hello World!")

    verify(mockAppender).doAppend(captorLoggingEvent.capture())
    val loggingEvent = captorLoggingEvent.getValue

    loggingEvent.getLoggerName shouldBe "Test101"
    loggingEvent.getLevel shouldBe Level.ERROR
    loggingEvent.getMessage shouldBe "Hello World!"

  }

  "A Logger for a given class" should "log events under the full qualified name of the given class" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger(classOf[MyServiceClass])

    log.error("Hello World!")

    verify(mockAppender).doAppend(captorLoggingEvent.capture())
    val loggingEvent = captorLoggingEvent.getValue

    loggingEvent.getLoggerName shouldBe "com.innoave.soda.logging.MyServiceClass"
    loggingEvent.getLevel shouldBe Level.ERROR
    loggingEvent.getMessage shouldBe "Hello World!"

  }

  "A Logger for this class" should "log events under the full qualified name of this class" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val service = new MyServiceClass

    service.itsLogger.error("Hello World!")

    verify(mockAppender).doAppend(captorLoggingEvent.capture())
    val loggingEvent = captorLoggingEvent.getValue

    loggingEvent.getLoggerName shouldBe "com.innoave.soda.logging.MyServiceClass"
    loggingEvent.getLevel shouldBe Level.ERROR
    loggingEvent.getMessage shouldBe "Hello World!"

  }

  "A Logger with Level set to Error" should "log events of level Error only" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.ERROR)

    log.error("Hello World!")

    log.warn("Hello World!")
    log.info("Hello World!")
    log.debug("Hello World!")
    log.trace("Hello World!")

    verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Hello World!"

  }

  "A Logger with Level set to Warn" should "log events of level Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.WARN)

    log.error("Hello World!")
    log.warn("Hello World!")

    log.info("Hello World!")
    log.debug("Hello World!")
    log.trace("Hello World!")

    verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Hello World!"

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "Hello World!"

  }

  "A Logger with Level set to Info" should "log events of level Info, Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.INFO)

    log.error("Hello World!")
    log.warn("Hello World!")
    log.info("Hello World!")

    log.debug("Hello World!")
    log.trace("Hello World!")

    verify(mockAppender, times(3)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Hello World!"

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "Hello World!"

    loggingEvents(2).getLoggerName shouldBe "InfoTest"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World!"

  }

  "A Logger with Level set to Debug" should "log events of level Debug, Info, Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.DEBUG)

    log.error("Hello World!")
    log.warn("Hello World!")
    log.info("Hello World!")
    log.debug("Hello World!")

    log.trace("Hello World!")

    verify(mockAppender, times(4)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Hello World!"

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "Hello World!"

    loggingEvents(2).getLoggerName shouldBe "InfoTest"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World!"

    loggingEvents(3).getLoggerName shouldBe "InfoTest"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "Hello World!"

  }

  "A Logger with Level set to Trace" should "log events of level Trace, Debug, Info, Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.TRACE)

    log.error("Hello World!")
    log.warn("Hello World!")
    log.info("Hello World!")
    log.debug("Hello World!")
    log.trace("Hello World!")

    verify(mockAppender, times(5)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Hello World!"

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "Hello World!"

    loggingEvents(2).getLoggerName shouldBe "InfoTest"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World!"

    loggingEvents(3).getLoggerName shouldBe "InfoTest"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "Hello World!"

    loggingEvents(4).getLoggerName shouldBe "InfoTest"
    loggingEvents(4).getLevel shouldBe Level.TRACE
    loggingEvents(4).getMessage shouldBe "Hello World!"

  }

  "A Logger with Level set to All" should "log events of level Trace, Debug, Info, Warning and Error" in withCapturingAppender {
    (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTest")
    log.setLevel(Level.ALL)

    log.error("Hello World!")
    log.warn("Hello World!")
    log.info("Hello World!")
    log.debug("Hello World!")
    log.trace("Hello World!")

    verify(mockAppender, times(5)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues

    loggingEvents(0).getLoggerName shouldBe "InfoTest"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Hello World!"

    loggingEvents(1).getLoggerName shouldBe "InfoTest"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "Hello World!"

    loggingEvents(2).getLoggerName shouldBe "InfoTest"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World!"

    loggingEvents(3).getLoggerName shouldBe "InfoTest"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "Hello World!"

    loggingEvents(4).getLoggerName shouldBe "InfoTest"
    loggingEvents(4).getLevel shouldBe Level.TRACE
    loggingEvents(4).getMessage shouldBe "Hello World!"

  }

}
