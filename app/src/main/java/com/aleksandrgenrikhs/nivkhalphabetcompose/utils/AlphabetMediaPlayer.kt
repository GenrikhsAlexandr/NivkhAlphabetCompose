package com.aleksandrgenrikhs.nivkhalphabetcompose.utils

import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object AlphabetMediaPlayer {

    private var mediaPlayer: MediaPlayer? = null
    private var showToast: Boolean = false

    fun initPlayer(context: Context, url: String): MediaPlayer? {
        return try {
            val afd = context.assets.openFd("$url.mp3")
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                    prepare()
                }
            }
            mediaPlayer
        } catch (e: Exception) {
            if (!showToast) {
                Toast.makeText(
                    context,
                    context.getString(R.string.noSoundtrack),
                    Toast.LENGTH_SHORT
                ).show()
                showToast = true
            }
            null
        }
    }

    fun play() {
        mediaPlayer?.start()
    }

    fun isPlayingPlayer(): Flow<Boolean> = flow {
        if (mediaPlayer != null) {
            try {
                while (true) {
                    if (!mediaPlayer!!.isPlaying) {
                        emit(false)
                        break
                    }
                }
            } catch (e: IllegalStateException) {
                emit(false)
            }
        } else {
            emit(false)
        }
    }.flowOn(Dispatchers.Default)

    fun destroyPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
        showToast = false
    }
}
