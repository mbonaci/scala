package scala2e.chapter10.test

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala2e.chapter10.Element.elem

class ElementSpec extends FlatSpec with ShouldMatchers {
  "A UniformElement" should "have a width equal to the passed value" in {
    val e = elem('x', 2, 3)
    e.width should be (2)
  }
  it should "have a height equal to the passed value" in {
    val e = elem('x', 2, 3)
    e.height should be (3)
  }
  it should "throw an IAE if passed a negative width" in {
    evaluating {
      elem('x', -2, 3)
    } should produce [IllegalArgumentException]
  }
}