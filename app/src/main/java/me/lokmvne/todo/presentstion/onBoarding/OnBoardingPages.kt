package me.lokmvne.todo.presentstion.onBoarding

import me.lokmvne.todo.R

sealed class OnBoardingPages(
    val title: String,
    val description: String,
    val image: Int
) {
    data object First : OnBoardingPages(
        title = "Organize Your Tasks",
        description = "Easily plan and schedule your tasks with our intuitive interface. Stay on top of your daily goals with smart categorization and deadlines.",
        image = R.drawable.first
    )

    data object Second : OnBoardingPages(
        title = "Never Miss a Deadline",
        description = "Get timely notifications to keep you on track. We'll remind you about due dates and important tasks, so you never forget what's important.",
        image = R.drawable.second
    )

    data object Third : OnBoardingPages(
        title = "Focus on What Matters",
        description = "Prioritize your tasks and highlight the ones that matter most. Stay productive by focusing on high-priority items first.",
        image = R.drawable.third
    )
}