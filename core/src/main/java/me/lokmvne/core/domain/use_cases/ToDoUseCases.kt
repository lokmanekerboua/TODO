package me.lokmvne.core.domain.use_cases

data class ToDoUseCases(
    val addTaskUseCase: AddTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val deleteAllTasksUseCase: DeleteAllTasksUseCase,
    val getAllTasksUseCase: GetAllTasksUseCase,
    val getSelectedTaskUseCase: GetSelectedTaskUseCase,
    val searchDatabaseUseCase: SearchDatabaseUseCase,
    val updateTaskUseCase: UpdateTaskUseCase,
    val getHighPriorityTasks: GetHighPriorityTasks,
    val getNearTasks: GetNearTasks
)