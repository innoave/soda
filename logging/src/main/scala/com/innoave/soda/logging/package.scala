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
package com.innoave.soda

/** Soda Logging wraps the SLF4J Logger API in a lightweight and Scala
 *  idiomatic way.
 *
 *  The log message is passed as a call-by-name parameter. So there is
 *  no need to provide method signatures for messages with arguments as the
 *  construction of the log message is done lazy anyway.
 *
 *  Also methods to query the log level like isDebugEnabled and they like
 *  are omitted. The check for the log level is done inside the log methods
 *  so the message is composed only when it is to be written to the logger.
 *  There is no longer a good reason to use such methods in the user code.
 *
 *  In contrast to other Scala Logging wrapper APIs Soda Logging provides
 *  wrappers for `Marker`s and `MDC` in addition to the `Logger` itself.
 *
 *  For example a [[Marker]] can be instantiated using the apply method of
 *  the companion object and the [[Marker#iterator]] method returns a
 *  [[scala.Iterator]] instead of a [[java.util.Iterator]]
 *
 *  The advantages may seem to be minimal but when actually using the Logger
 *  API developers do not need to think about whether they are dealing with
 *  a Java class. Instead one can just write Scala code.
 *
 *  @since 0.1.0
 *  @author haraldmaida
 */
package object logging
