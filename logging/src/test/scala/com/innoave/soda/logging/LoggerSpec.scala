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
import ch.qos.logback.classic.turbo.MarkerFilter

private class MyServiceClass
private class MyCodecClass

class LoggerSpec extends FlatSpec with Matchers with LogbackHelper {

  "The root logger" should "have a logger the predefined logger name" in {

    Logger.rootLogger.name() shouldBe Logger.RootLoggerName

    Logger(Logger.RootLoggerName).name() shouldBe org.slf4j.Logger.ROOT_LOGGER_NAME

  }

  "The Logger object" should "return the slf4j.ILoggerFactory instance in use" in {

    Logger.underlying.getClass shouldBe classOf[ch.qos.logback.classic.LoggerContext]

  }

  "A Logger for given name" should "log events under this name" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("Test101")

    log.info("Hello World!")

    verify(mockAppender).doAppend(captorLoggingEvent.capture())
    val loggingEvent = captorLoggingEvent.getValue

    log.name() shouldBe "Test101"
    loggingEvent.getLoggerName shouldBe "Test101"

  }

  "A Logger for a given class" should "log events under the full qualified name of the given class" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger(classOf[MyServiceClass])

    log.info("Hello World!")

    verify(mockAppender).doAppend(captorLoggingEvent.capture())
    val loggingEvent = captorLoggingEvent.getValue

    log.name() shouldBe "com.innoave.soda.logging.MyServiceClass"
    loggingEvent.getLoggerName shouldBe "com.innoave.soda.logging.MyServiceClass"

  }

  "A Logger for a given type" should "log events under the full qualified name of this type" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger[MyCodecClass]

    log.info("Hello World!")

    verify(mockAppender).doAppend(captorLoggingEvent.capture())
    val loggingEvent = captorLoggingEvent.getValue

    log.name() shouldBe "com.innoave.soda.logging.MyCodecClass"
    loggingEvent.getLoggerName shouldBe "com.innoave.soda.logging.MyCodecClass"

  }

  "A Logger with Level set to Error" should "log a message for level Error only" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("ErrorTestLogger")
    log.setLevel(Level.ERROR)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "ErrorTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null

  }

  it should "log a message and an exception for level Error only" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("ErrorTestLogger")
    log.setLevel(Level.ERROR)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "ErrorTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(0).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

  }

  "A Logger with Level set to Warn" should "log a message for level Warning and Error" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("WarnTestLogger")
    log.setLevel(Level.WARN)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "WarnTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null

    loggingEvents(1).getLoggerName shouldBe "WarnTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null

  }

  it should "log a message and an exception for level Warning and Error" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("WarnTestLogger")
    log.setLevel(Level.WARN)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "WarnTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(0).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(1).getLoggerName shouldBe "WarnTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(1).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

  }

  "A Logger with Level set to Info" should "log a message for level Info, Warning and Error" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTestLogger")
    log.setLevel(Level.INFO)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(3)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "InfoTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null

    loggingEvents(1).getLoggerName shouldBe "InfoTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null

    loggingEvents(2).getLoggerName shouldBe "InfoTestLogger"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy shouldBe null

  }

  it should "log a message and an exception for level Info, Warning and Error" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("InfoTestLogger")
    log.setLevel(Level.INFO)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    verify(mockAppender, times(3)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "InfoTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(0).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(1).getLoggerName shouldBe "InfoTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(1).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(2).getLoggerName shouldBe "InfoTestLogger"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(2).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

  }

  "A Logger with Level set to Debug" should "log a message for level Debug, Info, Warning and Error" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("DebugTestLogger")
    log.setLevel(Level.DEBUG)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(4)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "DebugTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null

    loggingEvents(1).getLoggerName shouldBe "DebugTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null

    loggingEvents(2).getLoggerName shouldBe "DebugTestLogger"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy shouldBe null

    loggingEvents(3).getLoggerName shouldBe "DebugTestLogger"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "I did it"
    loggingEvents(3).getThrowableProxy shouldBe null

  }

  it should "log a message and an exception for level Debug, Info, Warning and Error" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("DebugTestLogger")
    log.setLevel(Level.DEBUG)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    verify(mockAppender, times(4)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "DebugTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(0).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(1).getLoggerName shouldBe "DebugTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(1).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(2).getLoggerName shouldBe "DebugTestLogger"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(2).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(3).getLoggerName shouldBe "DebugTestLogger"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "I did it"
    loggingEvents(3).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(3).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

  }

  "A Logger with Level set to Trace" should "log a message for level Trace, Debug, Info, Warning and Error" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("TraceTestLogger")
    log.setLevel(Level.TRACE)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(5)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "TraceTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null

    loggingEvents(1).getLoggerName shouldBe "TraceTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null

    loggingEvents(2).getLoggerName shouldBe "TraceTestLogger"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy shouldBe null

    loggingEvents(3).getLoggerName shouldBe "TraceTestLogger"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "I did it"
    loggingEvents(3).getThrowableProxy shouldBe null

    loggingEvents(4).getLoggerName shouldBe "TraceTestLogger"
    loggingEvents(4).getLevel shouldBe Level.TRACE
    loggingEvents(4).getMessage shouldBe "Here are all the details"
    loggingEvents(4).getThrowableProxy shouldBe null

  }

  it should "log a message and an exception for level Trace, Debug, Info, Warning and Error" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("TraceTestLogger")
    log.setLevel(Level.TRACE)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    verify(mockAppender, times(5)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "TraceTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(0).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(1).getLoggerName shouldBe "TraceTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(1).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(2).getLoggerName shouldBe "TraceTestLogger"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(2).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(3).getLoggerName shouldBe "TraceTestLogger"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "I did it"
    loggingEvents(3).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(3).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(4).getLoggerName shouldBe "TraceTestLogger"
    loggingEvents(4).getLevel shouldBe Level.TRACE
    loggingEvents(4).getMessage shouldBe "Here are all the details"
    loggingEvents(4).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(4).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

  }

  "A Logger with Level set to All" should "log a message for level Trace, Debug, Info, Warning and Error" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("AllTestLogger")
    log.setLevel(Level.ALL)

    log.error("Oh what an error")
    log.warn("I've warned you")
    log.info("Hello World")
    log.debug("I did it")
    log.trace("Here are all the details")

    verify(mockAppender, times(5)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "AllTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null

    loggingEvents(1).getLoggerName shouldBe "AllTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy shouldBe null

    loggingEvents(2).getLoggerName shouldBe "AllTestLogger"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy shouldBe null

    loggingEvents(3).getLoggerName shouldBe "AllTestLogger"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "I did it"
    loggingEvents(3).getThrowableProxy shouldBe null

    loggingEvents(4).getLoggerName shouldBe "AllTestLogger"
    loggingEvents(4).getLevel shouldBe Level.TRACE
    loggingEvents(4).getMessage shouldBe "Here are all the details"
    loggingEvents(4).getThrowableProxy shouldBe null

  }

  it should "log a message and an exception for level Trace, Debug, Info, Warning and Error" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

    val log = Logger("AllTestLogger")
    log.setLevel(Level.ALL)

    log.error("Oh what an error", new RuntimeException("Houston we have a problem"))
    log.warn("I've warned you", new RuntimeException("Houston we have a problem"))
    log.info("Hello World", new RuntimeException("Houston we have a problem"))
    log.debug("I did it", new RuntimeException("Houston we have a problem"))
    log.trace("Here are all the details", new RuntimeException("Houston we have a problem"))

    verify(mockAppender, times(5)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "AllTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(0).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(1).getLoggerName shouldBe "AllTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(1).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(2).getLoggerName shouldBe "AllTestLogger"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(2).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(3).getLoggerName shouldBe "AllTestLogger"
    loggingEvents(3).getLevel shouldBe Level.DEBUG
    loggingEvents(3).getMessage shouldBe "I did it"
    loggingEvents(3).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(3).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(4).getLoggerName shouldBe "AllTestLogger"
    loggingEvents(4).getLevel shouldBe Level.TRACE
    loggingEvents(4).getMessage shouldBe "Here are all the details"
    loggingEvents(4).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(4).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

  }

  "A Logger with marker filter and level set to info" should
      "log a message with marker for level Trace, Debug and Info only if the marker contains the filtered marker" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

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

    verify(mockAppender, times(3)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "MarkerTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy shouldBe null

    loggingEvents(1).getLoggerName shouldBe "MarkerTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(0).getThrowableProxy shouldBe null

    loggingEvents(2).getLoggerName shouldBe "MarkerTestLogger"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(0).getThrowableProxy shouldBe null

  }

  it should "log a message and an exception with marker for level Trace, Debug and Info only if the marker contains the filtered marker" in
      withCapturingAppender { (mockAppender, captorLoggingEvent) =>

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

    verify(mockAppender, times(3)).doAppend(captorLoggingEvent.capture())
    val loggingEvents = captorLoggingEvent.getAllValues.asScala

    loggingEvents(0).getLoggerName shouldBe "MarkerTestLogger"
    loggingEvents(0).getLevel shouldBe Level.ERROR
    loggingEvents(0).getMessage shouldBe "Oh what an error"
    loggingEvents(0).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(0).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(1).getLoggerName shouldBe "MarkerTestLogger"
    loggingEvents(1).getLevel shouldBe Level.WARN
    loggingEvents(1).getMessage shouldBe "I've warned you"
    loggingEvents(1).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(1).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

    loggingEvents(2).getLoggerName shouldBe "MarkerTestLogger"
    loggingEvents(2).getLevel shouldBe Level.INFO
    loggingEvents(2).getMessage shouldBe "Hello World"
    loggingEvents(2).getThrowableProxy.getClassName shouldBe "java.lang.RuntimeException"
    loggingEvents(2).getThrowableProxy.getMessage shouldBe "Houston we have a problem"

  }

}
