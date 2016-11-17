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

import java.lang.reflect.{Method => JMethod}
import scala.collection.immutable
import scala.collection.mutable
import scala.collection.AbstractSet
import scala.collection.SortedSetLike
import scala.reflect.NameTransformer._

abstract class DefineMessage(
    keyNamingStrategy: KeyNamingStrategy = KeyNamingStrategy.default,
    initial: Int = 0
    ) {
  thisDefineMessage =>

  val bundleName = new BundleName(toString)

  /* Note that `readResolve` cannot be private, since otherwise
     the JVM does not invoke it when deserializing subclasses. */
  protected def readResolve(): AnyRef =
    thisDefineMessage.getClass.getField(MODULE_INSTANCE_NAME).get(null)

  /** The name of this messages enumeration.
   */
  override def toString: String = KeyNamingStrategy.simpleTypeName(getClass)

  private val valueMap: mutable.Map[Int, Msg] = new mutable.HashMap
  private val nameMap: mutable.Map[Int, String] = new mutable.HashMap
  private val keyMap: mutable.Map[Int, String] = new mutable.HashMap

  @transient private var vset: MessageSet = null
  @transient @volatile private var vsetDefined = false

  /**
   * The values of this Messages as a set.
   */
  def values: MessageSet = {
    if (!vsetDefined) {
      vset = (MessageSet.newBuilder ++= valueMap.values).result()
      vsetDefined = true
    }
    vset
  }

  /**
   * The integer to use to identify the next created message.
   */
  private var nextId: Int = initial

  /**
   * The highest integer amongst those used to identify messages in this
   * messages enumeration.
   */
  private var topId = initial

  /**
   * The lowest integer amongst those used to identify messages in this
   * messages enumeration, but no higher than 0.
   */
  private var bottomId = if(initial < 0) initial else 0

  /**
   * The message of this Messages with given id.
   */
  final def apply(id: Int): Msg = valueMap(id)

  /**
   * Creates a fresh message, part of this Messages.
   */
  final protected def Message0: Msg0 = new Msg0(nextId, null, null)

  final protected def Message1[A1]: MsgP[Tuple1[A1]] = new MsgP(nextId, null, null)
  final protected def Message2[A1, A2]: MsgP[Tuple2[A1, A2]] = new MsgP(nextId, null, null)
  final protected def Message3[A1, A2, A3]: MsgP[Tuple3[A1, A2, A3]] = new MsgP(nextId, null, null)
  final protected def Message4[A1, A2, A3, A4]: MsgP[Tuple4[A1, A2, A3, A4]] = new MsgP(nextId, null, null)
  final protected def Message5[A1, A2, A3, A4, A5]: MsgP[Tuple5[A1, A2, A3, A4, A5]] = new MsgP(nextId, null, null)
  final protected def Message6[A1, A2, A3, A4, A5, A6]: MsgP[Tuple6[A1, A2, A3, A4, A5, A6]] = new MsgP(nextId, null, null)
  final protected def Message7[A1, A2, A3, A4, A5, A6, A7]: MsgP[Tuple7[A1, A2, A3, A4, A5, A6, A7]] = new MsgP(nextId, null, null)
  final protected def Message8[A1, A2, A3, A4, A5, A6, A7, A8]: MsgP[Tuple8[A1, A2, A3, A4, A5, A6, A7, A8]] = new MsgP(nextId, null, null)
  final protected def Message9[A1, A2, A3, A4, A5, A6, A7, A8, A9]: MsgP[Tuple9[A1, A2, A3, A4, A5, A6, A7, A8, A9]] = new MsgP(nextId, null, null)

  /**
   * Creates a fresh message, part of this Messages
   * with a custom key.
   */
  final protected def Message0(key: String): Msg0 = new Msg0(nextId, null, key)

  final protected def Message1[A1](key: String): MsgP[Tuple1[A1]] = new MsgP(nextId, null, key)
  final protected def Message2[A1, A2](key: String): MsgP[Tuple2[A1, A2]] = new MsgP(nextId, null, key)
  final protected def Message3[A1, A2, A3](key: String): MsgP[Tuple3[A1, A2, A3]] = new MsgP(nextId, null, key)
  final protected def Message4[A1, A2, A3, A4](key: String): MsgP[Tuple4[A1, A2, A3, A4]] = new MsgP(nextId, null, key)
  final protected def Message5[A1, A2, A3, A4, A5](key: String): MsgP[Tuple5[A1, A2, A3, A4, A5]] = new MsgP(nextId, null, key)
  final protected def Message6[A1, A2, A3, A4, A5, A6](key: String): MsgP[Tuple6[A1, A2, A3, A4, A5, A6]] = new MsgP(nextId, null, key)
  final protected def Message7[A1, A2, A3, A4, A5, A6, A7](key: String): MsgP[Tuple7[A1, A2, A3, A4, A5, A6, A7]] = new MsgP(nextId, null, key)
  final protected def Message8[A1, A2, A3, A4, A5, A6, A7, A8](key: String): MsgP[Tuple8[A1, A2, A3, A4, A5, A6, A7, A8]] = new MsgP(nextId, null, key)
  final protected def Message9[A1, A2, A3, A4, A5, A6, A7, A8, A9](key: String): MsgP[Tuple9[A1, A2, A3, A4, A5, A6, A7, A8, A9]] = new MsgP(nextId, null, key)

  /**
   * Creates a fresh message, part of this Messages
   * with a custom name and key.
   */
  final protected def Message0(name: String, key: String): Msg0 = new Msg0(nextId, name, key)

  final protected def Message1[A1](name: String, key: String): MsgP[Tuple1[A1]] = new MsgP(nextId, name, key)
  final protected def Message2[A1, A2](name: String, key: String): MsgP[Tuple2[A1, A2]] = new MsgP(nextId, name, key)
  final protected def Message3[A1, A2, A3](name: String, key: String): MsgP[Tuple3[A1, A2, A3]] = new MsgP(nextId, name, key)
  final protected def Message4[A1, A2, A3, A4](name: String, key: String): MsgP[Tuple4[A1, A2, A3, A4]] = new MsgP(nextId, name, key)
  final protected def Message5[A1, A2, A3, A4, A5](name: String, key: String): MsgP[Tuple5[A1, A2, A3, A4, A5]] = new MsgP(nextId, name, key)
  final protected def Message6[A1, A2, A3, A4, A5, A6](name: String, key: String): MsgP[Tuple6[A1, A2, A3, A4, A5, A6]] = new MsgP(nextId, name, key)
  final protected def Message7[A1, A2, A3, A4, A5, A6, A7](name: String, key: String): MsgP[Tuple7[A1, A2, A3, A4, A5, A6, A7]] = new MsgP(nextId, name, key)
  final protected def Message8[A1, A2, A3, A4, A5, A6, A7, A8](name: String, key: String): MsgP[Tuple8[A1, A2, A3, A4, A5, A6, A7, A8]] = new MsgP(nextId, name, key)
  final protected def Message9[A1, A2, A3, A4, A5, A6, A7, A8, A9](name: String, key: String): MsgP[Tuple9[A1, A2, A3, A4, A5, A6, A7, A8, A9]] = new MsgP(nextId, name, key)

  private def nameOf(id: Int): String =
    synchronized {
      nameMap.getOrElse(id, { populateNameMap(); nameMap(id) })
    }

  private def keyOf(id: Int, name: String): String =
    synchronized {
      keyMap.getOrElseUpdate(id, keyNamingStrategy.keyFor(id, name))
    }

  private def populateNameMap(): Unit = {
    val fields = getClass.getDeclaredFields

    def isValueDefinition(m: JMethod) =
      fields exists (fd => fd.getName == m.getName && fd.getType == m.getReturnType)

    // The list of possible Value methods: 0-args which return a conforming type
    val methods = getClass.getMethods filter { m =>
        m.getParameterTypes.isEmpty &&
        classOf[Msg].isAssignableFrom(m.getReturnType) &&
        m.getDeclaringClass != classOf[DefineMessage] &&
        isValueDefinition(m)
      }

    methods foreach { m =>
      val name = m.getName
      // invoke method to obtain actual `Value` instance
      val value = m.invoke(this).asInstanceOf[Msg]
      // verify that outer points to the correct Enumeration
      if (value.outerDefineMessage eq thisDefineMessage) {
        val id = Int.unbox(classOf[Msg] getMethod "id" invoke value)
        nameMap += ((id, name))
      }
    }

  }

  /**
   * The type of the Messages values.
   */
  sealed trait Msg extends Message with Ordered[Msg] {

    assert(!valueMap.isDefinedAt(id), "Duplicate id: " + id)
    valueMap(id) = this
    vsetDefined = false
    nextId = id + 1
    if (nextId > topId) topId = nextId
    if (id < bottomId) bottomId = id

    override val bundleName: BundleName = thisDefineMessage.bundleName

    def id: Int
    protected def _name: String
    protected def _key: String

    /**
     * a marker so we can tell whose values belong to whom come reflective-naming time
     */
    private[DefineMessage] val outerDefineMessage = thisDefineMessage

    override def compare(that: Msg): Int =
      this.id.compareTo(that.id)

    override def equals(other: Any): Boolean =
      other match {
        case that: DefineMessage#Msg =>
          (this.outerDefineMessage eq that.outerDefineMessage) &&
          (this.id == that.id)
        case _ =>
          false
      }

    override def hashCode(): Int =
      id.##

    override def toString(): String =
      try {
        thisDefineMessage.toString + "#" + name() + "(" + key() + ")"
      } catch {
        case _: NoSuchElementException => "!!! Invalid Messages: no field for #" + id + " !!!"
      }

    final override def name(): String =
      if (_name != null)
        _name
      else
        thisDefineMessage.nameOf(id)

    final override def key(): String =
      if (_key != null)
        _key
      else
        thisDefineMessage.keyOf(id, name())

    /**
     * Create a MessageSet which contains this message and another one.
     */
    def +(v: Msg) =
      MessageSet(this, v)

  }

  final class Msg0 private[DefineMessage](
      override val id: Int,
      override protected val _name: String,
      override protected val _key: String
      ) extends Message0 with Msg

  final class MsgP[A <: Product] private[DefineMessage](
      override val id: Int,
      override protected val _name: String,
      override protected val _key: String
      ) extends MessageP[A] with Msg

  /**
   * An ordering by key for messages of this set.
   */
  object MessageOrdering extends Ordering[Msg] {

    override def compare(x: Msg, y: Msg): Int =
      x compare y

  }

  /**
   * A class for sets of messages.
   * Iterating through this set will yield messages in order of their definition.
   */
  final class MessageSet private[MessageSet] (private[this] var nnIds: immutable.BitSet)
      extends AbstractSet[Msg]
      with immutable.SortedSet[Msg]
      with SortedSetLike[Msg, MessageSet]
      {

    implicit def ordering: Ordering[Msg] = MessageOrdering

    override def empty = MessageSet.empty

    override def contains(v: Msg) = nnIds.contains(v.id)

    override def +(value: Msg) = new MessageSet(nnIds + (value.id - bottomId))

    override def -(value: Msg) = new MessageSet(nnIds - (value.id - bottomId))

    override def iterator = nnIds.iterator map { id => thisDefineMessage.apply(bottomId + id) }

    override def keysIteratorFrom(start: Msg) =
      nnIds.keysIteratorFrom(start.id) map { id => thisDefineMessage.apply(bottomId + id) }

    override def rangeImpl(from: Option[Msg], until: Option[Msg]): MessageSet =
      new MessageSet(nnIds.rangeImpl(from.map(_.id - bottomId), until.map(_.id - bottomId)))

    override def stringPrefix = thisDefineMessage + ".ValueSet"

  }

  /**
   * A factory object for message sets.
   */
  object MessageSet {
    import collection.generic.CanBuildFrom

    /**
     * The empty message set.
     */
    val empty = new MessageSet(immutable.BitSet.empty)

    /**
     * A message set consisting of given elements.
     */
    def apply(elems: Msg*): MessageSet =
      (newBuilder ++= elems).result()

    /**
     * A builder object for message sets.
     */
    def newBuilder: mutable.Builder[Msg, MessageSet] =
      new mutable.Builder[Msg, MessageSet] {
        private[this] val b = new mutable.BitSet
        override def +=(x: Msg) = { b += (x.id - bottomId); this }
        override def clear() = b.clear()
        override def result() = new MessageSet(immutable.BitSet.fromBitMaskNoCopy(b.toBitMask))
      }

    /**
     * The implicit builder for message sets.
     */
    implicit def canBuildFrom: CanBuildFrom[MessageSet, Msg, MessageSet] =
      new CanBuildFrom[MessageSet, Msg, MessageSet] {
        override def apply(from: MessageSet) = newBuilder
        override def apply() = newBuilder
      }

  }

}
