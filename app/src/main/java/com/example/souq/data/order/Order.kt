package com.example.souq.data.order

import android.os.Parcelable
import com.example.souq.data.Address
import com.example.souq.data.CartProduct
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random.Default.nextLong

@Parcelize
data class Order(
    val orderStatus:String="",
    val totalPrice: Float=0f,
    val products:List<CartProduct> = emptyList(),
    val address:Address=Address(),
    val date:String=SimpleDateFormat("yyyy-MM-DD", Locale.US).format(Date()),
    val orderId:Long=nextLong(0,1000_0000_000)+totalPrice.toLong()
):Parcelable
{
//
//    or look up
//    constructor():this("", 0f, emptyList(), Address(),...)
}

fun getOrderStatus(status: String):OrderStatus{
    return when (status)
    {
        "ordered"->OrderStatus.Ordered
        "canceled"->OrderStatus.Canceled
        "confirmed"->OrderStatus.Confirmed
        "shipped"->OrderStatus.Shipped
        "delivered"->OrderStatus.Delivered
        else ->OrderStatus.Returned
    }

}