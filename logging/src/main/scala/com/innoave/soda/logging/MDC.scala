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
import org.slf4j.{MDC => SLF4JMDC}

/** Scala wrapper object for `org.slf4j.MDC`.
 *
 *  Some method signatures are slightly different to the original SLF4J API
 *  to provide a Scala idiomatic syntax. E.g. `MDC.get(key:String)` returns an
 *  `Option[String]` instead of a `String` that may be `null`.
 *
 *  @since 0.1.0
 *  @author haraldmaida
 */
object MDC {

  def mdcAdapter: org.slf4j.spi.MDCAdapter =
    SLF4JMDC.getMDCAdapter

  def get(key: String): Option[String] =
    Option(SLF4JMDC.get(key))

  def getOrElseUpdate(key: String, op: => String): String =
    Option(SLF4JMDC.get(key)) match {
      case Some(value) =>
        value
      case None =>
        val value = op
        SLF4JMDC.put(key, value)
        value
    }

  def put(key: String, value: String): Unit =
    SLF4JMDC.put(key, value)

  def remove(key: String): Unit =
    SLF4JMDC.remove(key)

  def clear(): Unit =
    SLF4JMDC.clear()

  def contextMap: Map[String, String] =
    Option(SLF4JMDC.getCopyOfContextMap).map(_.asScala.toMap).getOrElse(Map())

  def contextMap_=(value: Map[String, String]): Unit =
    SLF4JMDC.setContextMap(value.asJava)

  def putCloseable(key: String, value: String): MDCCloseable =
    new MDCCloseable(SLF4JMDC.putCloseable(key, value))

  /** Scala wrapper class for `org.slf4j.MDC.MDCCloseable`.
   *
   *  @since 0.1.0
   *  @author haraldmaida
   */
  final class MDCCloseable(val underlying: SLF4JMDC.MDCCloseable) extends AnyVal {

    def close(): Unit = underlying.close()

  }

}
