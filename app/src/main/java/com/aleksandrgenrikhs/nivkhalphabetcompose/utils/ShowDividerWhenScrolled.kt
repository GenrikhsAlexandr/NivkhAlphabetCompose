package com.aleksandrgenrikhs.nivkhalphabetcompose.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import javax.inject.Inject

@Composable
fun ShowDividerWhenScrolled(
    onDividerVisibilityChange: (Boolean) -> Unit,
    scrollableState: ScrollableState,
) {
    var previousVisibilityState by remember { mutableStateOf(false) }

    LaunchedEffect(scrollableState) {
        snapshotFlow { scrollableState.firstVisibleItemScrollOffset }
            .collect { offset ->
                val isCurrentlyVisible = offset > 0
                if (isCurrentlyVisible != previousVisibilityState) {
                    onDividerVisibilityChange(isCurrentlyVisible)
                    previousVisibilityState = isCurrentlyVisible
                }
            }
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