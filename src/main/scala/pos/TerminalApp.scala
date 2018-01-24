package pos

object TerminalApp extends App {
	import io.StdIn._
	import Models._
	import Terminal._

	def setPricing: Pricing  = {

		def iter(acc: Pricing): Pricing = {
			
			println("Input Product Code. Press Enter to terminate.")

			val productCode = readLine()
			if (productCode == "") acc
			else {
				
				println("Input Unit Price")
	    	val unitPrice = readLine().toFloat
	    	
	    	println("Input No. of items for bulk pricing. Press Enter to skip this.")
	    	val groupSize = readLine()

	    	if (groupSize == "") iter(acc + (productCode -> ProductPricing(unitPrice, None)))
	    	else {
	    		println("Input Bulk Price")
		    	val bp = readLine().toFloat
		    	val bulkPrice = BulkPrice(groupSize.toInt, bp)
		    	iter(acc + (productCode -> ProductPricing(unitPrice, Some(bulkPrice))))
	    	}
			}
		}

		iter(Map())
	}

	println("Starting POS Terminal v0.0.1")
	println("Setting Price")
	val pricing = setPricing

	def scanLoop(acc:Cart): Cart = {
		val curTotal = acc.values.map(_._2).sum
		println(s"$curTotal | Scan Item. Press Enter to finish scanning & calculate final total.")
		readLine() match {
			case "" => acc
			case curItem: String => scanLoop(scan(curItem)(acc)(pricing))
		}
	}
	
	def outerLoop: Unit = {
		println("Starting Scanning")
		val emptyCart:Map[ProductCode, (ProductCount, SubTotal)] = Map()
		val customerCart = scanLoop(emptyCart)
		println("Final Cart: " + customerCart)
		println("Final Total: " + customerCart.values.map(_._2).sum)
		println("Repeat? y/n")
		readLine() match {
			case "y" => outerLoop
			case _ => println("Goodbye!")
		}
	}

	outerLoop
}