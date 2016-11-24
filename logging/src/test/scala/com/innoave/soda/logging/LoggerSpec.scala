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

import org.scalatest.BeforeAndAfterEach
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.Suite
import org.mockito.ArgumentCaptor
import org.mockito.Mockito._
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.{Logger => LbLogger}
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender
import org.scalatest.mockito.MockitoSugar

trait CapturingAppender extends BeforeAndAfterEach with MockitoSugar { this: Suite =>

  val mockAppender = mock[Appender[ILoggingEvent]]

  val captorLoggingEvent = ArgumentCaptor.forClass(classOf[ILoggingEvent])

  override def beforeEach() {
    val logger = Logger(Logger.RootLoggerName)
    logger.underlying.asInstanceOf[LbLogger].addAppender(mockAppender)
    super.beforeEach()
  }

  override def afterEach() {
    try {
      super.afterEach()
    } finally {
      val logger = Logger(Logger.RootLoggerName)
      logger.underlying.asInstanceOf[LbLogger].detachAppender(mockAppender)
    }
  }

}

class LoggerSpec extends FlatSpec with Matchers with CapturingAppender { this: Suite =>

  "Logger" should "log error event" in {

    val log = Logger("Test101")

    log.error("Hello World!")

    verify(mockAppender).doAppend(captorLoggingEvent.capture())
    val loggingEvent = captorLoggingEvent.getValue

    loggingEvent.getLevel shouldBe Level.ERROR
    loggingEvent.getMessage shouldBe "Hello World!"

  }

}
