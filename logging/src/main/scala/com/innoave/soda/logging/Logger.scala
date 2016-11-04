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

import org.slf4j.{Logger => SLF4JLogger}
import org.slf4j.{LoggerFactory => SLF4JLoggerFactory}
import scala.reflect.ClassTag
import scala.reflect.classTag
import org.slf4j.Marker

class Logger(underlying: SLF4JLogger) {

  @inline final def name(): String = underlying.getName

  @inline final def trace(message: => String): Unit =
    if (underlying.isTraceEnabled) underlying.trace(message)

  @inline final def trace(message: => String, throwable: Throwable): Unit =
    if (underlying.isTraceEnabled) underlying.trace(message, throwable)

  @inline final def trace(marker: Marker, message: => String): Unit =
    if (underlying.isTraceEnabled(marker)) underlying.trace(marker, message)

  @inline final def trace(marker: Marker, message: => String, throwable: Throwable): Unit =
    if (underlying.isTraceEnabled(marker)) underlying.trace(marker, message, throwable)

  @inline final def debug(message: => String): Unit =
    if (underlying.isDebugEnabled) underlying.debug(message)

  @inline final def debug(message: => String, throwable: Throwable): Unit =
    if (underlying.isDebugEnabled) underlying.debug(message, throwable)

  @inline final def debug(marker: Marker, message: => String): Unit =
    if (underlying.isDebugEnabled(marker)) underlying.debug(marker, message)

  @inline final def debug(marker: Marker, message: => String, throwable: Throwable): Unit =
    if (underlying.isDebugEnabled(marker)) underlying.debug(marker, message, throwable)

  @inline final def info(message: => String): Unit =
    if (underlying.isInfoEnabled) underlying.info(message)

  @inline final def info(message: => String, throwable: Throwable): Unit =
    if (underlying.isInfoEnabled) underlying.info(message, throwable)

  @inline final def info(marker: Marker, message: => String): Unit =
    if (underlying.isInfoEnabled(marker)) underlying.info(marker, message)

  @inline final def info(marker: Marker, message: => String, throwable: Throwable): Unit =
    if (underlying.isInfoEnabled(marker)) underlying.info(marker, message, throwable)

  @inline final def warn(message: => String): Unit =
    if (underlying.isWarnEnabled) underlying.warn(message)

  @inline final def warn(message: => String, throwable: Throwable): Unit =
    if (underlying.isWarnEnabled) underlying.warn(message, throwable)

  @inline final def warn(marker: Marker, message: => String): Unit =
    if (underlying.isWarnEnabled(marker)) underlying.warn(marker, message)

  @inline final def warn(marker: Marker, message: => String, throwable: Throwable): Unit =
    if (underlying.isWarnEnabled(marker)) underlying.warn(marker, message, throwable)

  @inline final def error(message: => String): Unit =
    if (underlying.isErrorEnabled) underlying.error(message)

  @inline final def error(message: => String, throwable: Throwable): Unit =
    if (underlying.isErrorEnabled) underlying.error(message, throwable)

  @inline final def error(marker: Marker, message: => String): Unit =
    if (underlying.isErrorEnabled(marker)) underlying.error(marker, message)

  @inline final def error(marker: Marker, message: => String, throwable: Throwable): Unit =
    if (underlying.isErrorEnabled(marker)) underlying.error(marker, message, throwable)

}

object Logger {

  val RootLoggerName = SLF4JLogger.ROOT_LOGGER_NAME

  def apply(name: String): Logger =
    new Logger(SLF4JLoggerFactory.getLogger(name))

  def apply(clazz: Class[_]): Logger =
    apply(clazz.getName)

  def apply[C: ClassTag](): Logger =
    apply(classTag[C].runtimeClass.getName)

  def rootLogger =
    apply(RootLoggerName)

}