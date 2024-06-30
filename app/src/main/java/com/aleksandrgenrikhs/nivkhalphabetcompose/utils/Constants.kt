package com.aleksandrgenrikhs.nivkhalphabetcompose.utils

object Constants {
    // Константы для навигации по экранам
    const val LETTERS_SCREEN = "letters"
    const val TASKS_SCREEN = "list_tasks"
    const val FIRST_TASK_SCREEN = "first_task"
    const val SECOND_TASK_SCREEN = "second_task"
    const val THIRD_TASK_SCREEN = "third_task"
    const val FOURTH_TASK_SCREEN = "fourth_task"
    const val FIFTH_TASK_SCREEN = "fifth_task"

    //Костанты для передачи аргументов
    const val LETTER_KEY = "letter"
    const val TASK_KEY = "task"

    //URI константы
    const val WORDS_FIRST_TASK = "firsttask/words/words.json"
    const val ICON_WORD_FIRST_TASK = "file:///android_asset/firsttask/image/"
    const val IMAGE_URL =
        "http://bibl-nogl-dictionary.ru/data/alphabet/"
    const val LETTER_AUDIO_FIRST_TASK = "firsttask/audio/letters/"
    const val WORDS_AUDIO_FIRST_TASK = "firsttask/audio/words/"

    //SharedPreferences константы
    const val PREF_KEY_LETTER = "completed_letters"
    const val PREF_KEY_TASK = "completed_tasks"
}