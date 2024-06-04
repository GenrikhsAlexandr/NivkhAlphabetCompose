package com.aleksandrgenrikhs.nivkhalphabetcompose.navigator

import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.NavConstants.FIRST_TASK_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.NavConstants.LETTERS_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.NavConstants.SECOND_TASK_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.NavConstants.TASKS_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.NavConstants.THIRD_TASK_SCREEN

sealed class NavigationDestination(val destination: String) {
    data object LettersScreen : NavigationDestination(LETTERS_SCREEN)
    data object TasksScreen: NavigationDestination(TASKS_SCREEN)
    data object FirstTaskScreen: NavigationDestination(FIRST_TASK_SCREEN)
    data object SecondTaskScreen: NavigationDestination(SECOND_TASK_SCREEN)
    data object ThirdTaskScreen: NavigationDestination(THIRD_TASK_SCREEN)
}