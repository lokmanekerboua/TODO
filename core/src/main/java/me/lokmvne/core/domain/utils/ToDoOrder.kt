package me.lokmvne.core.domain.utils

sealed class ToDoOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : ToDoOrder(orderType)
    class Priority(orderType: OrderType) : ToDoOrder(orderType)
    class TriggerTime(orderType: OrderType) : ToDoOrder(orderType)

    fun copy(orderType: OrderType): ToDoOrder {
        return when (this) {
            is Priority -> Priority(orderType)
            is Title -> Title(orderType)
            is TriggerTime -> TriggerTime(orderType)
        }
    }
}