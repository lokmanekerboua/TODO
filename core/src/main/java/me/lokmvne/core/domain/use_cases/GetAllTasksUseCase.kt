package me.lokmvne.core.domain.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.lokmvne.core.domain.model.ToDoTask
import me.lokmvne.core.domain.utils.OrderType
import me.lokmvne.core.domain.utils.ToDoOrder
import me.lokmvne.core.repository.ToDoRepository

class GetAllTasksUseCase(private val repository: ToDoRepository) {
    operator fun invoke(
        toDoOrder: ToDoOrder = ToDoOrder.TriggerTime(OrderType.Descending)
    ): Flow<List<ToDoTask>> {
        return repository.getAllTasks().map { todoTasks ->
            when (toDoOrder.orderType) {
                OrderType.Ascending -> {
                    when (toDoOrder) {
                        is ToDoOrder.Priority -> {
                            todoTasks.sortedBy { it.priority.ordinal }
                        }

                        is ToDoOrder.Title -> {
                            todoTasks.sortedBy { it.title }
                        }

                        is ToDoOrder.TriggerTime -> {
                            todoTasks.sortedBy { it.date }
                        }
                    }
                }

                OrderType.Descending -> {
                    when (toDoOrder) {
                        is ToDoOrder.Priority -> {
                            todoTasks.sortedByDescending { it.priority.ordinal }
                        }

                        is ToDoOrder.Title -> {
                            todoTasks.sortedByDescending { it.title }
                        }

                        is ToDoOrder.TriggerTime -> {
                            todoTasks.sortedByDescending { it.date }
                        }
                    }
                }
            }
        }
    }
}