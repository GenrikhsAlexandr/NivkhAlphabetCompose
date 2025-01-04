package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import android.content.Context
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.PrefRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.AlphabetMediaPlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class MediaPlayerInteractorTest {

    private val mediaPlayer: AlphabetMediaPlayer = mock()
    private val prefRepository: PrefRepository = mock()

    private val playerInteractor = MediaPlayerInteractor(mediaPlayer, prefRepository)

    @Test
    fun `WHEN call mediaplayer THEN player is initialized and play`() {
        val context: Context = mock()
        val url = "url"
        playerInteractor.initPlayer(
            context = context,
            url = url
        )
        verify(mediaPlayer).initPlayer(context, url)
    }

    @Test
    fun `WHEN isPlaying called THEN returns the playing state`() = runTest {
        val isPlayingFlow: Flow<Boolean> = flowOf(true)
        whenever(mediaPlayer.isPlayingPlayer()).thenReturn(isPlayingFlow)
        val result = playerInteractor.isPlaying().first()
        assertEquals(true, result)
        verify(mediaPlayer).isPlayingPlayer()
    }

    @Test
    fun `WHEN playerDestroy called THEN player is destroyed`() {
        playerInteractor.playerDestroy()
        verify(mediaPlayer).destroyPlayer()
    }

    @Test
    fun `WHEN getIsSoundEnable called THEN return sound enable state`() = runTest {
        whenever(prefRepository.getSoundEnabled()).thenReturn(true)
        val actual = playerInteractor.getIsSoundEnable()
        assertEquals(true, actual)
        verify(prefRepository).getSoundEnabled()
    }
}
