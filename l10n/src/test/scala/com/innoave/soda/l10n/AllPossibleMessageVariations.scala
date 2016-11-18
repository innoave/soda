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

import java.time.LocalDateTime

object AllPossibleMessageVariations extends DefineMessage {

  val MyMessage0 = Message0
  val MyMessage1 = Message1[Long]
  val MyMessage2 = Message2[String, String]
  val MyMessage3 = Message3[Int, BigDecimal, String]
  val MyMessage4 = Message4[String, Long, LocalDateTime, String]
  val MyMessage5 = Message5[Int, Int, Int, Int, Int]
  val MyMessage6 = Message6[Long, String, Int, Int, String, String]
  val MyMessage7 = Message7[LocalDateTime, Long, String, Int, Int, String, String]
  val MyMessage8 = Message8[Long, Long, Long, Long, Long, Long, Long, Long]
  val MyMessage9 = Message9[Int, Int, Int, Int, Int, Int, Int, Int, Int]

  val CustomKeyMessage0 = Message0 ("ck__message0")
  val CustomKeyMessage1 = Message1[Long] ("ck__message1")
  val CustomKeyMessage2 = Message2[String, String] ("ck__message2")
  val CustomKeyMessage3 = Message3[Int, BigDecimal, String] ("ck__message3")
  val CustomKeyMessage4 = Message4[String, Long, LocalDateTime, String] ("ck__message4")
  val CustomKeyMessage5 = Message5[Int, Int, Int, Int, Int] ("ck__message5")
  val CustomKeyMessage6 = Message6[Long, String, Int, Int, String, String] ("ck__message6")
  val CustomKeyMessage7 = Message7[LocalDateTime, Long, String, Int, Int, String, String] ("ck__message7")
  val CustomKeyMessage8 = Message8[Long, Long, Long, Long, Long, Long, Long, Long] ("ck__message8")
  val CustomKeyMessage9 = Message9[Int, Int, Int, Int, Int, Int, Int, Int, Int] ("ck__message9")

  val AllCustomMessage0 = Message0 ("acMessage0", "ac__message0")
  val AllCustomMessage1 = Message1[Long] ("acMessage1", "ac__message1")
  val AllCustomMessage2 = Message2[String, String] ("acMessage2", "ac__message2")
  val AllCustomMessage3 = Message3[Int, BigDecimal, String] ("acMessage3", "ac__message3")
  val AllCustomMessage4 = Message4[String, Long, LocalDateTime, String] ("acMessage4", "ac__message4")
  val AllCustomMessage5 = Message5[Int, Int, Int, Int, Int] ("acMessage5", "ac__message5")
  val AllCustomMessage6 = Message6[Long, String, Int, Int, String, String] ("acMessage6", "ac__message6")
  val AllCustomMessage7 = Message7[LocalDateTime, Long, String, Int, Int, String, String] ("acMessage7", "ac__message7")
  val AllCustomMessage8 = Message8[Long, Long, Long, Long, Long, Long, Long, Long] ("acMessage8", "ac__message8")
  val AllCustomMessage9 = Message9[Int, Int, Int, Int, Int, Int, Int, Int, Int] ("acMessage9", "ac__message9")

}
