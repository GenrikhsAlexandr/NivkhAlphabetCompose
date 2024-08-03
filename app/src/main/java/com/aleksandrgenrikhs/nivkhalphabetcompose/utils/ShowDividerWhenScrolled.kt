package com.aleksandrgenrikhs.nivkhalphabetcompose.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import javax.inject.Inject

@Composable
fun ShowDividerWhenScrolled(
    onDividerVisibilityChange: (Boolean) -> Unit,
    scrollableState: ScrollableState,
) {
    var initialScrollOffset by remember { mutableIntStateOf(0) }
    var currentScrollOffset by remember { mutableIntStateOf(0) }

    LaunchedEffect(scrollableState) {
        snapshotFlow { scrollableState.firstVisibleItemScrollOffset }
            .collect { offset ->
                currentScrollOffset = offset
                if (currentScrollOffset == initialScrollOffset) {
                    onDividerVisibilityChange(false)
                } else {
                    onDividerVisibilityChange(true)
                }
            }
    }

    // Устанавливаем начальную позицию прокрутки
    LaunchedEffect(Unit) {
        initialScrollOffset = scrollableState.firstVisibleItemScrollOffset
    }
}

interface ScrollableState {
    val firstVisibleItemScrollOffset: Int
}

class LazyListScrollableState
@Inject constructor(private val state: LazyListState) : ScrollableState {
    override val firstVisibleItemScrollOffset: Int
        get() = state.firstVisibleItemScrollOffset
}

class LazyGridScrollableState
@Inject constructor(private val state: LazyGridState) : ScrollableState {
    override val firstVisibleItemScrollOffset: Int
        get() = state.firstVisibleItemScrollOffset
}