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

import org.scalatest.mockito.MockitoSugar
import org.mockito.ArgumentCaptor
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.{Logger => LbLogger}
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.turbo.TurboFilter
import ch.qos.logback.core.Appender

trait LogbackHelper extends MockitoSugar {

  implicit class LogbackLoggerSetter(logger: Logger) {
    val lbl = logger.underlying.asInstanceOf[LbLogger]
    def setLevel(level: Level): Unit =
      lbl.setLevel(level)
    def addTurboFilter(newFilter: TurboFilter): Unit = {
      lbl.getLoggerContext.addTurboFilter(newFilter)
    }
  }

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

}
