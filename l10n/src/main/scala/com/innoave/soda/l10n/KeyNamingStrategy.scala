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

import scala.reflect.NameTransformer._
import scala.util.matching.Regex

/**
 * Defines how the keys are determined, if no explicit key
 * is given at construction time of values.
 * Keys are used to lookup the localized pattern
 * in the localization implementation.
 * E.g. to lookup a resource in a resource bundle.
 */
trait KeyNamingStrategy {

  def keyFor(id: Int, name: String): String

}

object KeyNamingStrategy {

  val default: KeyNamingStrategy = DotSeparatedKeyNames

  def simpleTypeName(clazz: Class[_]): String =
    ((clazz.getName stripSuffix MODULE_SUFFIX_STRING split '.').last split
      Regex.quote(NAME_JOIN_STRING)).last

}

object NamesAsKeys extends KeyNamingStrategy {

  override def keyFor(id: Int, name: String): String = {
    if (name.isEmpty()) {
      throw new IllegalArgumentException("Name must not be empty")
    }
    name
  }

}

class CharacterSeparatedKeyNames(separatorChar: Char) extends KeyNamingStrategy {

  override def keyFor(id: Int, name: String): String = {
    if (name.isEmpty()) {
      throw new IllegalArgumentException("Name must not be empty")
    }
    val result = new StringBuilder
    result + name.head.toLower
    name.tail.foldLeft(result) { (r, c) =>
      if (c.isUpper)
        if (r.last != separatorChar)
          r + separatorChar + c.toLower
        else
          r + c.toLower
      else
        r + c
    }
    result.toString
  }

}

object DotSeparatedKeyNames extends CharacterSeparatedKeyNames('.')
