package pos

import org.scalatest._
import Terminal.scan
import Models._

class TerminalSpec extends FlatSpec with Matchers {

	val samplePricing: Pricing = Map(
	"A" -> ProductPricing(2.0f, Some(BulkPrice(4,7.0f))),
	"B" -> ProductPricing(12f, None),
	"C" -> ProductPricing(1.25f, Some(BulkPrice(6,6.0f))),
	"D" -> ProductPricing(0.15f, None)
	)

	val cart1 = "ABCDABAA"
	val cart2 = "CCCCCCC"
	val cart3 = "ABCD"

  "The scan method" should "total sample carts correctly" in {
  	val z:Map[ProductCode, (ProductCount, SubTotal)] = Map()
    cart1.foldLeft(z)( (acc:Cart,cur:Char) => scan(cur.toString)(acc)(samplePricing)).values.map(_._2).sum shouldEqual 32.40f
    cart2.foldLeft(z)( (acc:Cart,cur:Char) => scan(cur.toString)(acc)(samplePricing)).values.map(_._2).sum shouldEqual 7.25f
    cart3.foldLeft(z)( (acc:Cart,cur:Char) => scan(cur.toString)(acc)(samplePricing)).values.map(_._2).sum shouldEqual 15.40f
  }
}