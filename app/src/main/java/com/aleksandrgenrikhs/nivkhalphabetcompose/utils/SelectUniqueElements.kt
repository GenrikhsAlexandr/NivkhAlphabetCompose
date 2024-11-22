package com.aleksandrgenrikhs.nivkhalphabetcompose.utils

import javax.inject.Inject

interface SelectUniqueElements {
    operator fun <T> invoke(
        allElements: List<T>, count: Int, selector: (T) -> Any
    ): List<T>
}

class DefaultSelectUniqueElements
@Inject constructor() : SelectUniqueElements {
    override fun <T> invoke(
        allElements: List<T>,
        count: Int,
        selector: (T) -> Any
    ): List<T> {
        return allElements.shuffled()
            .distinctBy { selector(it) }
            .take(count)
    }
}