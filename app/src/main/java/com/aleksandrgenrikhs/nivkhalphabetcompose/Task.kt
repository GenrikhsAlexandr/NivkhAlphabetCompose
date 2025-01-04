package com.aleksandrgenrikhs.nivkhalphabetcompose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.REVISION_CHOOSE_RIGHT_WORD_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.REVISION_COMPLETE_TABLE_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.REVISION_LISTEN_AND_CHOOSE_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.TASK_FIND_WORD_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.TASK_LEARN_LETTER_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.TASK_MATCH_WORDS_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.TASK_WRITE_WORD_SCREEN

enum class Task(
    val stableId: Int,
    @StringRes
    val titleResId: Int,
    @DrawableRes
    val icon: Int,
    val route: String
) {
    LEARN_LETTER(
        stableId = 1,
        titleResId = R.string.firstTask,
        icon = R.drawable.ic_task1,
        route = TASK_LEARN_LETTER_SCREEN
    ),
    FIND_WORD(
        stableId = 2,
        titleResId = R.string.secondTask,
        icon = R.drawable.ic_task2,
        route = TASK_FIND_WORD_SCREEN
    ),
    MATCH_WORDS(
        stableId = 3,
        titleResId = R.string.thirdTask,
        icon = R.drawable.ic_task3,
        route = TASK_MATCH_WORDS_SCREEN
    ),
    WRITE_WORD(
        stableId = 4,
        titleResId = R.string.fourthTask,
        icon = R.drawable.ic_task4,
        route = TASK_WRITE_WORD_SCREEN
    )
}

enum class RevisionTask(
    val stableId: Int,
    @StringRes
    val titleResId: Int,
    @DrawableRes
    val icon: Int,
    val route: String
) {
    LISTEN_AND_CHOOSE(
        stableId = 5,
        titleResId = R.string.revisionFirst,
        icon = R.drawable.ic_revision_task_first,
        route = REVISION_LISTEN_AND_CHOOSE_SCREEN
    ),
    CHOOSE_RIGHT_WORD(
        stableId = 5,
        titleResId = R.string.revisionSecond,
        icon = R.drawable.ic_revision_task_second,
        route = REVISION_CHOOSE_RIGHT_WORD_SCREEN
    ),
    COMPLETE_TABLE(
        stableId = 7,
        titleResId = R.string.revisionThird,
        icon = R.drawable.ic_revision_task_third,
        route = REVISION_COMPLETE_TABLE_SCREEN
    )
}
