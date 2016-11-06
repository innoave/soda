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
package com.innoave.soda.l10n

object DemoTypes {

  sealed trait Suit
  case object Spades extends Suit
  case object Clubs extends Suit
  case object Diamonds extends Suit
  case object Hearts extends Suit

  sealed trait Face
  case object Ten extends Face
  case object Jack extends Face
  case object Queen extends Face
  case object King extends Face

  case class Card(suit: Suit, face: Face)

}

object DemoTypesLocalizer extends DefineLocalized {
  import DemoTypes._

  override val bundleName = BundleName("l10n.demotypes")
  override val keyNamingStrategy = NamesAsKeys

  implicit def localizedSuit(suit: Suit) = localized(suit)

  implicit def localizedFace(face: Face) = localized(face)

  implicit def localizedCard(card: Card) = localized(card, (localized(card.suit), localized(card.face)))

}
