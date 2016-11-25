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
import org.slf4j.{Marker => SLF4JMarker}
import org.slf4j.{MarkerFactory => SLF4JMarkerFactory}

final class Marker private[Marker](val asSlf4j: SLF4JMarker) extends AnyVal {

  def add(reference: Marker): Unit =
    asSlf4j.add(reference.asSlf4j)

  def remove(reference: Marker): Unit =
    asSlf4j.remove(reference.asSlf4j)

  def contains(other: Marker): Boolean =
    asSlf4j.contains(other.asSlf4j)

  def contains(name: String): Boolean =
    asSlf4j.contains(name)

  def hasReferences(): Boolean =
    asSlf4j.hasReferences()

  def iterator(): Iterator[Marker] =
    asSlf4j.iterator().asScala.map(new Marker(_))

}

object Marker {

  def underlying: org.slf4j.IMarkerFactory =
    SLF4JMarkerFactory.getIMarkerFactory()

  val Any: Marker = new Marker(SLF4JMarkerFactory.getMarker(SLF4JMarker.ANY_MARKER))
  val AnyNonNull: Marker = new Marker(SLF4JMarkerFactory.getMarker(SLF4JMarker.ANY_NON_NULL_MARKER))

  def apply(name: String): Marker =
    new Marker(SLF4JMarkerFactory.getMarker(name))

  def detached(name: String): Marker =
    new Marker(SLF4JMarkerFactory.getDetachedMarker(name))

}
