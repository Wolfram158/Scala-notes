package ru.wolfram.stage1
package range

import org.scalatest.funsuite.AnyFunSuiteLike

class RangeTest extends AnyFunSuiteLike {
    test("GIVEN empty range WHEN isEmpty THEN true") {
        assert(EmptyRange.isEmpty)
    }

    test("GIVEN non empty range WHEN isEmpty THEN false") {
        (-100 to 100).foreach { min =>
            (min to 200).foreach { max =>
                assert(!NonEmptyRange.fromInterval(min, max).isEmpty)
            }
        }
    }

    test("GIVEN two non empty ranges WHEN intersection is non empty THEN intersection is non empty") {
        val range1 = NonEmptyRange.fromInterval(10, 20)
        val range2 = NonEmptyRange.fromInterval(15, 30)
        assert(range1.intersect(range2).equals(NonEmptyRange.fromInterval(15, 20)))
    }
}
