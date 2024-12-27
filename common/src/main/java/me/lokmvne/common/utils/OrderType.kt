package me.lokmvne.common.utils

sealed class OrderType {
    data object Ascending : OrderType()
    data object Descending : OrderType()
}
