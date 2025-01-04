package me.lokmvne.core.presentation.todo_task

import me.lokmvne.core.data.utils.Priority
import java.time.LocalDate
import java.time.LocalTime

data class SingleTaskState(
    var title: String = "",
    var description: String = "",
    var priority: Priority = Priority.LOW,
    var illustration: Int = illustrationsList.first().second,
    var taskColor: Long = colorsList.first().second,
    var date: LocalDate = LocalDate.now(),
    var time: LocalTime = LocalTime.now(),

    //val text: String = "",
    var isPriorityExpended: Boolean = false,
    var isIllustExpended: Boolean = false,
    var isColorExpended: Boolean = false,
    var isDatePickerShowed: Boolean = false,
    var isTimePickerShowed: Boolean = false,
    var illustText: String = illustrationsList.first().first,
    var colorText: String = colorsList.first().first
)