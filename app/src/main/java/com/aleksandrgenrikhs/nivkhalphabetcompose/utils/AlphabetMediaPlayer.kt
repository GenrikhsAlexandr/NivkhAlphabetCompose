package com.aleksandrgenrikhs.nivkhalphabetcompose.utils

import android.app.Application
import android.media.MediaPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object AlphabetMediaPlayer {

    private var mediaPlayer: MediaPlayer? = null
    fun initPlayer(application: Application, url: String): MediaPlayer? {
        return try {
            val afd = application.assets.openFd("$url.mp3")
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                    prepare()
                }
            }
            mediaPlayer
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun play() {
        mediaPlayer?.start()
    }

    fun isPlayingPlayer(): Flow<Boolean> = flow {
        mediaPlayer?.let {
            while (true) {
                if (!it.isPlaying) {
                    emit(false)
                    break
                }
            }
        }
    }.flowOn(Dispatchers.Default)

    fun destroyPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}