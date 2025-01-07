package me.lokmvne.todo.presentstion.onBoarding

import me.lokmvne.todo.R

sealed class OnBoardingPages(
    val title: Int,
    val description: Int,
    val image: Int
) {
    data object First : OnBoardingPages(
        title = R.string.page1_title,
        description = R.string.page1_description,
        image = R.drawable.first
    )

    data object Second : OnBoardingPages(
        title = R.string.page2_title,
        description = R.string.page2_description,
        image = R.drawable.second
    )

    data object Third : OnBoardingPages(
        title = R.string.page3_title,
        description = R.string.page3_description,
        image = R.drawable.third
    )
}