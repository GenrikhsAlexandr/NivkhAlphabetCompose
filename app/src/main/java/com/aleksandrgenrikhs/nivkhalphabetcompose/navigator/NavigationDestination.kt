package com.aleksandrgenrikhs.nivkhalphabetcompose.navigator

import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FIRST_TASK_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FOURTH_TASK_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.LETTERS_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.REVISION_TASKS_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.SECOND_TASK_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.SPLASH_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.TASKS_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.THIRD_TASK_SCREEN

sealed class NavigationDestination(val destination: String) {
    data object SplashScreen : NavigationDestination(SPLASH_SCREEN)
    data object LettersScreen : NavigationDestination(LETTERS_SCREEN)
    data object TasksScreen : NavigationDestination(TASKS_SCREEN)
    data object FirstTaskScreen : NavigationDestination(FIRST_TASK_SCREEN)
    data object SecondTaskScreen : NavigationDestination(SECOND_TASK_SCREEN)
    data object ThirdTaskScreen : NavigationDestination(THIRD_TASK_SCREEN)
    data object FourthTaskScreen : NavigationDestination(FOURTH_TASK_SCREEN)
    data object RevisionTaskScreen : NavigationDestination(REVISION_TASKS_SCREEN)
}