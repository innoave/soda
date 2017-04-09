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

import scala.reflect.ClassTag
import scala.reflect.classTag
import org.slf4j.{Logger => SLF4JLogger}
import org.slf4j.{ILoggerFactory => SLF4JILoggerFactory}
import org.slf4j.{LoggerFactory => SLF4JLoggerFactory}

/** The Logger is a Scala wrapper for the SLF4J Logger.
 *
 *  The exposed API is modified to be more ideomatic to Scala. Most notably the
 *  message to be logged is passed as a call-by-name parameter. This allows to
 *  compose complex messages on the caller side without impact performance
 *  impact at runtime when a log level is not activated. Another advantage is
 *  that the caller can choose how the log message is composed. It can compose
 *  the message in a simple way using string interpolation or any more complex
 *  concept such as providing localized messages.
 *
 *  Also the methods to checked for a log level like `isDebugEnabled` are
 *  omitted due there is no longer a reason to do such checks in the
 *  application code.
 *
 *  @since 0.1.0
 *  @author haraldmaida
 */
final class Logger private (val delegate: SLF4JLogger) extends AnyVal {

  /** Returns the name of this `Logger`.
   *
   *  @return name of this `Logger`
   */
  @inline final def name(): String = delegate.getName

  @inline final def trace(message: => String): Unit =
    if (delegate.isTraceEnabled) delegate.trace(message)

  @inline final def trace(message: => String, throwable: Throwable): Unit =
    if (delegate.isTraceEnabled) delegate.trace(message, throwable)

  @inline final def trace(marker: Marker, message: => String): Unit =
    if (delegate.isTraceEnabled(marker.asSlf4j)) delegate.trace(marker.asSlf4j, message)

  @inline final def trace(marker: Marker, message: => String, throwable: Throwable): Unit =
    if (delegate.isTraceEnabled(marker.asSlf4j)) delegate.trace(marker.asSlf4j, message, throwable)

  @inline final def debug(message: => String): Unit =
    if (delegate.isDebugEnabled) delegate.debug(message)

  @inline final def debug(message: => String, throwable: Throwable): Unit =
    if (delegate.isDebugEnabled) delegate.debug(message, throwable)

  @inline final def debug(marker: Marker, message: => String): Unit =
    if (delegate.isDebugEnabled(marker.asSlf4j)) delegate.debug(marker.asSlf4j, message)

  @inline final def debug(marker: Marker, message: => String, throwable: Throwable): Unit =
    if (delegate.isDebugEnabled(marker.asSlf4j)) delegate.debug(marker.asSlf4j, message, throwable)

  @inline final def info(message: => String): Unit =
    if (delegate.isInfoEnabled) delegate.info(message)

  @inline final def info(message: => String, throwable: Throwable): Unit =
    if (delegate.isInfoEnabled) delegate.info(message, throwable)

  @inline final def info(marker: Marker, message: => String): Unit =
    if (delegate.isInfoEnabled(marker.asSlf4j)) delegate.info(marker.asSlf4j, message)

  @inline final def info(marker: Marker, message: => String, throwable: Throwable): Unit =
    if (delegate.isInfoEnabled(marker.asSlf4j)) delegate.info(marker.asSlf4j, message, throwable)

  @inline final def warn(message: => String): Unit =
    if (delegate.isWarnEnabled) delegate.warn(message)

  @inline final def warn(message: => String, throwable: Throwable): Unit =
    if (delegate.isWarnEnabled) delegate.warn(message, throwable)

  @inline final def warn(marker: Marker, message: => String): Unit =
    if (delegate.isWarnEnabled(marker.asSlf4j)) delegate.warn(marker.asSlf4j, message)

  @inline final def warn(marker: Marker, message: => String, throwable: Throwable): Unit =
    if (delegate.isWarnEnabled(marker.asSlf4j)) delegate.warn(marker.asSlf4j, message, throwable)

  @inline final def error(message: => String): Unit =
    if (delegate.isErrorEnabled) delegate.error(message)

  @inline final def error(message: => String, throwable: Throwable): Unit =
    if (delegate.isErrorEnabled) delegate.error(message, throwable)

  @inline final def error(marker: Marker, message: => String): Unit =
    if (delegate.isErrorEnabled(marker.asSlf4j)) delegate.error(marker.asSlf4j, message)

  @inline final def error(marker: Marker, message: => String, throwable: Throwable): Unit =
    if (delegate.isErrorEnabled(marker.asSlf4j)) delegate.error(marker.asSlf4j, message, throwable)

}

/** Logger factory object.
 *
 *  The `Logger` companion object is used to obtain a `Logger` instance.
 *
 *  `Logger` instances can be obtained by name, by class tag or class.
 *
 *  @example Logger by name
 *  {{{
 *  val log = Logger("MyServiceClass")
 *  }}}
 *
 *  @example Logger by class tag
 *  {{{
 *  val log = Logger[MyServiceClass]
 *  }}}
 *
 *  @example Logger by actual class
 *  {{{
 *  val log = Logger(getClass)
 *  }}}
 */
object Logger {

  /** The name of the root logger. */
  val RootLoggerName = SLF4JLogger.ROOT_LOGGER_NAME

  /** Returns the underlying SLF4J `ILoggerFactory` that is actually used
   *  to obtains instances of `Logger`.
   */
  def underlyingFactory(): SLF4JILoggerFactory =
    SLF4JLoggerFactory.getILoggerFactory()

  /** Obtains the instance of the root `Logger`
   */
  def rootLogger(): Logger =
    new Logger(SLF4JLoggerFactory.getLogger(RootLoggerName))

  /** Obtains the instance of `Logger` for the given name
   *
   *  @example Logger by name
   *  {{{
   *  val log = Logger("MyServiceClass")
   *  }}}
   *
   *  @param name a name for which a `Logger` should be obtained.
   *  @return the `Logger` instance for the given name.
   */
  def apply(name: String): Logger =
    new Logger(SLF4JLoggerFactory.getLogger(name))

  /** Obtains the instance of `Logger` for a class tag
   *
   *  @example Logger by class tag
   *  {{{
   *  val log = Logger[MyServiceClass]
   *  }}}
   *
   *  @param C a class tag for which a `Logger` should be obtained.
   *  @return the `Logger` instance for the given class tag.
   */
  def apply[C: ClassTag](): Logger =
    new Logger(SLF4JLoggerFactory.getLogger(classTag[C].runtimeClass))

  /** Obtains the instance of `Logger`
   *
   *  @example Logger by actual class
   *  {{{
   *  val log = Logger(getClass)
   *  }}}
   *
   *  @param clazz a class for which a `Logger` should be obtained.
   *  @return the `Logger` instance for the given class.
   */
  def apply(clazz: Class[_]): Logger =
    new Logger(SLF4JLoggerFactory.getLogger(clazz))

}
