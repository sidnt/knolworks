package pos

object Terminal {
  import Models._
  
  //scans curItem into a given cart & returns the updated cart
  def scan(curItem: ProductCode)(cart: Cart)(pricing: Pricing): Cart = {

     if (pricing isDefinedAt curItem) {
        val curPrice = pricing(curItem)

        if(cart isDefinedAt curItem) {
          val curCount = cart(curItem)._1 + 1
          val lastSubTotal = cart(curItem)._2
          curPrice.bulkPrice match {
            case Some(bp) => {
              val groups = curCount/bp.groupSize
              val units = curCount%bp.groupSize
              if(units == 0) {//ie a group has formed 
                cart.updated(curItem, (curCount, lastSubTotal - (bp.groupSize-1)*curPrice.unitPrice + bp.groupPrice))
              }
              else {
                cart.updated(curItem, (curCount, lastSubTotal + curPrice.unitPrice))
              }
            }
            case None => {
              cart.updated(curItem, (curCount, lastSubTotal + curPrice.unitPrice))
            }
          }
        }
        else {
          //cart isn't defined at curItem. Add the binding.
          cart.updated(curItem, (1, curPrice.unitPrice))
        }
      }
      else {
        println("Pricing is not defined for this item.")
        cart
      }
    }
  }
