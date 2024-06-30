package com.aleksandrgenrikhs.nivkhalphabetcompose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FIFTH_TASK_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FIRST_TASK_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FOURTH_TASK_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.SECOND_TASK_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.THIRD_TASK_SCREEN

enum class Task(
    val stableId: Int,
    @StringRes
    val titleResId: Int,
    @DrawableRes
    val icon: Int,
    val route: String
) {
    FIRST(
        stableId = 1,
        titleResId = R.string.firstTask,
        icon = R.drawable.ic_task1,
        route = FIRST_TASK_SCREEN
    ),
    SECOND(
        stableId = 2,
        titleResId = R.string.secondTask,
        icon = R.drawable.ic_task2,
        route = SECOND_TASK_SCREEN
    ),
    THIRD(
        stableId = 3,
        titleResId = R.string.thirdTask,
        icon = R.drawable.ic_task3,
        route = THIRD_TASK_SCREEN
    ),
    FOURTH(
        stableId = 4,
        titleResId = R.string.fourthTask,
        icon = R.drawable.ic_task4,
        route = FOURTH_TASK_SCREEN
    ),
    FIFTH(
        stableId = 5,
        titleResId = R.string.fifthTask,
        icon = R.drawable.ic_task5,
        route = FIFTH_TASK_SCREEN
    )
}