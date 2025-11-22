package ru.wolfram.stage1
package range

import scala.math.{max as maxOf, min as minOf}

sealed trait Range {
    def length: Int

    def intersect(other: Range): Range

    def union(other: Range): Option[Range]

    def isEmpty: Boolean

    def contains(i: Int): Boolean

    def contains(other: Range): Boolean

    def isIntersect(other: Range): Boolean

    def toList: List[Int]

    def minimum: Option[Int]

    def maximum: Option[Int]
}

object NonEmptyRange {
    def fromInterval(min: Int, max: Int): Range = if (min > max) {
        EmptyRange
    } else {
        NonEmptyRange(min, max)
    }
}

case object EmptyRange extends Range {
    override def length: Int = 0

    override def intersect(other: Range): Range = EmptyRange

    override def union(other: Range): Option[Range] = None

    override def isEmpty: Boolean = true

    override def contains(i: Int): Boolean = false

    override def contains(other: Range): Boolean = false

    override def isIntersect(other: Range): Boolean = false

    override def toList: List[Int] = List.empty

    override def minimum: Option[Int] = None

    override def maximum: Option[Int] = None
}

case class NonEmptyRange private(min: Int, max: Int) extends Range {
    override def length: Int = max - min + 1

    override def intersect(other: Range): Range = other match {
        case EmptyRange => EmptyRange
        case NonEmptyRange(min, max) =>
            if (this.max < min || this.min > max) {
                EmptyRange
            } else {
                NonEmptyRange(maxOf(min, this.min), minOf(max, this.max))
            }

    }

    override def union(other: Range): Option[Range] = other match {
        case EmptyRange => None
        case NonEmptyRange(min, max) =>
            if (this.max < min || this.min > max) {
                None
            } else {
                Some(NonEmptyRange(minOf(min, this.min), maxOf(max, this.max)))
            }

    }

    override def isEmpty: Boolean = false

    override def contains(i: Int): Boolean = i >= min && i <= max

    override def contains(other: Range): Boolean = other match {
        case EmptyRange => false
        case NonEmptyRange(min, max) => this.min >= min && max <= this.max
    }

    override def isIntersect(other: Range): Boolean = other match {
        case EmptyRange => false
        case NonEmptyRange(min, max) =>
            this.max >= min && this.min <= max
    }

    override def toList: List[Int] = (min to max).toList

    override def minimum: Option[Int] = Some(min)

    override def maximum: Option[Int] = Some(max)
}