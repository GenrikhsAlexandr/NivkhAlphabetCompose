package com.aleksandrgenrikhs.nivkhalphabetcompose.utils

object Constants {
    // Константы для навигации по экранам
    const val LETTERS_SCREEN = "letters"
    const val TASKS_SCREEN = "list_tasks"
    const val TASK_LEARN_LETTER_SCREEN = "task_learn_letter"
    const val TASK_FIND_WORD_SCREEN = "task_find_word"
    const val TASK_MATCH_WORDS_SCREEN = "task_match_words"
    const val TASK_WRITE_WORD_SCREEN = "task_write_word"
    const val SPLASH_SCREEN = "splash_screen"
    const val REVISION_TASKS_SCREEN = "revision_screen"
    const val REVISION_LISTEN_AND_CHOOSE_SCREEN = "revision_listen_and_choose_screen"
    const val REVISION_CHOOSE_RIGHT_WORD_SCREEN = "revision_choose_right_word_screen"
    const val REVISION_COMPLETE_TABLE_SCREEN = "revision_complete_table_screen"
    const val ABOUT_SCREEN = "about_screen"
    const val CERTIFICATE_SCREEN = "certificate_screen"

    // Костанты для передачи аргументов
    const val LETTER_KEY = "letter"
    const val NAME_KEY = "name"

    // URI константы
    const val WORDS_URL = "words/words.json"
    const val IMAGE_URL = "file:///android_asset/image/"
    const val LETTER_AUDIO = "audio/letters/"
    const val WORDS_AUDIO = "audio/words/"
    const val ERROR_AUDIO = "audio/error"
    const val FINISH_AUDIO = "audio/finish"

    // Количество букв в алфавите
    const val COUNT_LETTERS = 46

    // PDF константы
    const val FILE_NAME = "certificate.pdf"
    const val CONTENT_TYPE_PDF = "application/pdf"
}
