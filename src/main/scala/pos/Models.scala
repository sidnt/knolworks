package pos

object Models {
	type ProductCode = String
	type ProductCount = Int
	type SubTotal = Float
	type Cart = Map[ProductCode, (ProductCount, SubTotal)]
	type Pricing = Map[ProductCode, ProductPricing]
	case class BulkPrice(groupSize: Int, groupPrice: Float)
	case class ProductPricing(unitPrice: Float, bulkPrice: Option[BulkPrice])
}
