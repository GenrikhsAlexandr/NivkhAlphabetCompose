package com.aleksandrgenrikhs.nivkhalphabet.utils

import android.app.Application
import android.media.MediaPlayer

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

    fun destroyPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}