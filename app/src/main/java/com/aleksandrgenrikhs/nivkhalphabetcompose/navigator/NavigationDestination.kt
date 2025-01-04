package com.aleksandrgenrikhs.nivkhalphabetcompose.navigator

import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ABOUT_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.CERTIFICATE_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.LETTERS_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.REVISION_CHOOSE_RIGHT_WORD_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.REVISION_COMPLETE_TABLE_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.REVISION_LISTEN_AND_CHOOSE_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.REVISION_TASKS_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.SPLASH_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.TASKS_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.TASK_FIND_WORD_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.TASK_LEARN_LETTER_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.TASK_MATCH_WORDS_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.TASK_WRITE_WORD_SCREEN

sealed class NavigationDestination(val destination: String) {
    data object SplashScreen : NavigationDestination(SPLASH_SCREEN)
    data object LettersScreen : NavigationDestination(LETTERS_SCREEN)
    data object TasksScreen : NavigationDestination(TASKS_SCREEN)
    data object TaskLearnLetterScreen : NavigationDestination(TASK_LEARN_LETTER_SCREEN)
    data object TaskFindWordScreen : NavigationDestination(TASK_FIND_WORD_SCREEN)
    data object TaskMatchWordsScreen : NavigationDestination(TASK_MATCH_WORDS_SCREEN)
    data object TaskWriteWordScreen : NavigationDestination(TASK_WRITE_WORD_SCREEN)
    data object RevisionTasksScreen : NavigationDestination(REVISION_TASKS_SCREEN)
    data object RevisionListenAndChooseScreen :
        NavigationDestination(REVISION_LISTEN_AND_CHOOSE_SCREEN)

    data object RevisionChooseRightWordScreen :
        NavigationDestination(REVISION_CHOOSE_RIGHT_WORD_SCREEN)

    data object RevisionCompleteTableScreen : NavigationDestination(REVISION_COMPLETE_TABLE_SCREEN)
    data object AboutScreen : NavigationDestination(ABOUT_SCREEN)
    data object CertificateScreen : NavigationDestination(CERTIFICATE_SCREEN)
}
