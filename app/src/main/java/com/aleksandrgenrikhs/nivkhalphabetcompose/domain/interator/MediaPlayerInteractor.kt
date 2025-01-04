package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import android.content.Context
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.PrefRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.AlphabetMediaPlayer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaPlayerInteractor
@Inject constructor(
    private val mediaPlayer: AlphabetMediaPlayer,
    private val prefRepository: PrefRepository
) {

    fun initPlayer(context: Context, url: String) {
        mediaPlayer.initPlayer(context, url)
        mediaPlayer.play()
    }

    fun isPlaying(): Flow<Boolean> = mediaPlayer.isPlayingPlayer()

    fun playerDestroy() {
        mediaPlayer.destroyPlayer()
    }
    suspend fun getIsSoundEnable(): Boolean = prefRepository.getSoundEnabled()
}
