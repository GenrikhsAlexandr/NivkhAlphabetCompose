package com.aleksandrgenrikhs.nivkhalphabetcompose.utils

inline fun <reified T> selectUniqueElements(
    allElements: List<T>,
    count: Int,
    crossinline selector: (T) -> Any,
): List<T> {
    return allElements.shuffled()
        .distinctBy { selector(it) }
        .take(count)
}
