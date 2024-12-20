package me.lokmvne.core.domain.utils

sealed class OrderType {
    data object Ascending : OrderType()
    data object Descending : OrderType()
}