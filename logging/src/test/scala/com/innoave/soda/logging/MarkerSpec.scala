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

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class MarkerSpec extends FlatSpec with Matchers {

  "The underlying IMarkerFactory" should "be accessible through underlying method on Marker object" in {

    Marker.underlying.getClass shouldBe classOf[org.slf4j.helpers.BasicMarkerFactory]

  }

  "The underlying Marker" should "be accessible through the asSlf4j method" in {

    Marker("Timing").asSlf4j.getClass shouldBe classOf[org.slf4j.helpers.BasicMarker]

  }

  "A simple Marker" should "have not references" in {

    val marker = Marker("Timing")

    marker.hasReferences() shouldBe false

  }

  "A Marker with no references" should "get added one reference by the add function" in {

    val timingMarker = Marker("Timing")
    val exactMarker = Marker("Exact")

    timingMarker.hasReferences() shouldBe false
    timingMarker.contains(exactMarker) shouldBe false
    exactMarker.hasReferences() shouldBe false

    timingMarker add exactMarker

    timingMarker.hasReferences() shouldBe true
    timingMarker.contains(exactMarker) shouldBe true
    timingMarker.contains("Exact") shouldBe true
    exactMarker.hasReferences() shouldBe false

  }

  "A Marker with one reference" should "get removed the contained reference by the remove function" in {

    val timingMarker = Marker("Timing")
    val exactMarker = Marker("Exact")
    timingMarker add exactMarker

    timingMarker.hasReferences() shouldBe true
    timingMarker.contains(exactMarker) shouldBe true
    exactMarker.hasReferences() shouldBe false

    timingMarker remove exactMarker

    timingMarker.hasReferences() shouldBe false
    timingMarker.contains(exactMarker) shouldBe false
    exactMarker.hasReferences() shouldBe false

  }

  "A Marker with tree of references" should "return an iterator over all direct child references" in {

    val timingMarker = Marker("Timing")
    val exactMarker = Marker("Exact")
    val approximateMarker = Marker("Approximate")
    val thresholdMarker = Marker("Threshold")

    timingMarker add exactMarker
    timingMarker add approximateMarker
    exactMarker add thresholdMarker

    val timingIterator = timingMarker.iterator()

    timingIterator.next shouldBe exactMarker
    timingIterator.next shouldBe approximateMarker
    timingIterator.hasNext shouldBe false

    val exactIterator = exactMarker.iterator()

    exactIterator.next shouldBe thresholdMarker
    exactIterator.hasNext shouldBe false

  }

}
