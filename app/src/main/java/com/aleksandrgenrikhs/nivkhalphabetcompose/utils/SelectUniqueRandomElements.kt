package com.aleksandrgenrikhs.nivkhalphabetcompose.utils

import kotlin.random.Random

inline fun <reified T> selectUniqueRandomElements(allElements: List<T>, count: Int): List<T> {
    val selectedIndices = mutableSetOf<Int>()
    val resultList = mutableListOf<T>()

    while (selectedIndices.size < count) {
        val index = Random.nextInt(allElements.size)
        if (index !in selectedIndices) {
            selectedIndices.add(index)
            resultList.add(allElements[index])
        }
    }
    return resultList
}