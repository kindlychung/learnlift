package code.snippet

import net.liftweb.util.{DynamicCell, ValueCell}
import net.liftweb.util.BindHelpers._
import net.liftweb.http.{WiringUI, SHtml}
import net.liftweb.http.js.JsCmds
import net.liftweb.http.js.JsCmds._


object ShoppingCart {
  val items =
    Item(1, "Item 1", 10, 1) ::
      Item(2, "Item 2", 1, 10) ::
      Item(3, "Item 3", 2, 1.5) ::
      Item(4, "Item 4", 3, 2.5) :: Nil

  val cartTotal = DynamicCell(() => items.foldLeft(0d)((acc, i) => acc + i.total.get))

  def setQuantity(i: Item, qty: Int, totalId: String) = {
    if (i.quantity.get < qty || i.quantity.get > qty) {
      i.quantity.set(qty)
      println(qty)
      // This is nonsense, totalId doesn't exist on the webpage. And since the total field is bound to item.total, you don't need to manually update it, either.
      //      JsCmds.SetValById(totalId, i.total.get)
      Noop
    } else {
      Noop
    }
  }

  def show = {
    "tbody *" #> {
      items.map(i => {
        val totalId = i.id.toString

        ".item *" #> i.name &
          ".quantity *" #> SHtml.ajaxText(
            i.quantity.get.toString,
            v => setQuantity(i, v.toInt, "")
          ) &
          ".price *" #> WiringUI.asText(i.price) &
          ".total *" #> WiringUI.asText(i.total)
      })
    } &
      ".cart-total *" #> WiringUI.asText(cartTotal)
  }
}

case class Item(id: Int, name: String, qty: Int, prc: Double) {
  val quantity = ValueCell(qty)
  val price = ValueCell(prc)
  //  val total = quantity.lift(_ * price)
  val total = DynamicCell(() => quantity * price)
}
