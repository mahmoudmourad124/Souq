package com.example.souq.data.order

sealed class OrderStatus(val status:String){
    object Ordered:OrderStatus("Orederd")
    object Canceled:OrderStatus("Canceled")
    object Confirmed:OrderStatus("Confirmed")
    object Shipped:OrderStatus("Shipped")
    object Delivered:OrderStatus("Delivered")
    object Returned:OrderStatus("Returned")
}
