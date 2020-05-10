package com.example.awesomefish.shared

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_MUSIC
import android.media.AudioAttributes.USAGE_GAME
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.util.SparseArray
import androidx.annotation.RawRes
import androidx.annotation.RequiresApi
import com.example.awesomefish.R

class SoundManager private constructor(val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    private var mediaPlayerReleased = true

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private var audioAttributes = AudioAttributes.Builder()
        .setContentType(CONTENT_TYPE_MUSIC)
        .setUsage(USAGE_GAME)
        .build()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private var soundPool: SoundPool? =
        SoundPool.Builder().setMaxStreams(MAX_SOUND).setAudioAttributes(audioAttributes).build()

    private var audioManager: AudioManager? =
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    private var soundPoolMap: SparseArray<Int>? = SparseArray()

    private var isSoundTurnedOff = false

    fun playLongTrack(backgroundSound: BackgroundSound) {
        mediaPlayer = MediaPlayer.create(context, backgroundSound.resId)
        mediaPlayer?.start()
        mediaPlayer?.isLooping = true
        mediaPlayerReleased = false
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun playShortSound(sound: ShortSound, loop: Loop) {
        if (isSoundTurnedOff) return
        val soundId = soundPool?.load(context, sound.resId, 1)!!
        soundPool?.setOnLoadCompleteListener(object : SoundPool.OnLoadCompleteListener {
            override fun onLoadComplete(soundPool: SoundPool?, sampleId: Int, status: Int) {
                val streamVolume: Float? =
                    audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC)?.toFloat()
                soundPool?.play(soundId, streamVolume!!, streamVolume, 0, loop.value, 1F)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun unload(index: Int) {
        soundPool?.unload(index)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun unloadAll(vararg indexes: Int) {
        for (index in indexes) {
            soundPool?.unload(index)
        }
    }

    fun stopBackgroundSound() {
        if (mediaPlayerReleased.not()) {
            val theMediaPlayer = mediaPlayer
            if (theMediaPlayer != null && theMediaPlayer.isPlaying) {
                theMediaPlayer.stop()
                theMediaPlayer.release()
                mediaPlayerReleased = true
            }
        }
    }

    fun pauseBackgroundSound() {
        if (!mediaPlayerReleased) {
            val theMediaPlayer = mediaPlayer
            if (theMediaPlayer != null && theMediaPlayer.isPlaying) {
                theMediaPlayer.pause()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun stopSound(index: Int) {
        soundPool?.stop(index)
    }

    fun resume() {
        if (mediaPlayer != null && mediaPlayerReleased.not()) {
            mediaPlayer?.start()
        }
    }

    fun turnOff() {
        isSoundTurnedOff = true
    }

    fun tournOn() {
        isSoundTurnedOff = false
    }

    companion object {
        const val MAX_SOUND = 12

        // Sounds (We are not using all the sounds now, we will be using everything or most of it
        //         subsequently)
        var MENU_1 = 0
        var MENU_2 = 1
        var MENU_LOOPING = 2
        var PAUSE_SCREEN = 3
        var LEVEL_UP = 4
        var IN_GAME_1 = 5
        var IN_GAME_2 = 6
        var IN_GAME_3 = 7
        var IN_GAME_4 = 8
        var FOOD_EATEN = 9
        var DAMAGE = 10
        var STAGE_ONE = 11
        var REVEAL_ONE = 12
        var REVEAL_TWO = 13
        var CLICK = 14

        @Volatile
        private var soundManager: SoundManager? = null

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun clear() {
            if (soundManager != null) {
                soundManager?.soundPool?.release()
                soundManager?.soundPool = null
                soundManager?.audioManager = null
                soundManager?.soundPoolMap = null
            }
            val theMediaPlayer = soundManager?.mediaPlayer
            if (theMediaPlayer != null) {
                if (theMediaPlayer.isPlaying) {
                    theMediaPlayer.stop()
                }
                soundManager?.mediaPlayer = null
            }
            soundManager = null
        }


        fun instance(context: Context): SoundManager {
            val theSoundManager = soundManager
            if (theSoundManager != null) {
                return theSoundManager
            } else {
                synchronized(SoundManager::class) {
                    if (soundManager == null) {
                        soundManager = SoundManager(context)
                    }
                }
            }

            return soundManager!!
        }
    }

    enum class Loop(val value: Int) {
        LOOP(-1), DONT_LOOP(0)
    }

    enum class BackgroundSound(@RawRes val resId: Int) {
        MENU_SCREEN_ONE(R.raw.menu_screen_1),
        WELCOME_SCREEN(R.raw.welcome_screen),
        WELCOME_SCREEN_2(R.raw.welcome_screen_2),
        MENU_SCREEN(R.raw.menu_screen_2),
        HOME_SOUND(R.raw.home_sound),
    }

    enum class ShortSound(@RawRes val resId: Int) {
        CLICK(R.raw.click),
        REVEAL_ONE(R.raw.reveal_one),
        REVEAL_TWO(R.raw.reveal_2),
        DAMAGE(R.raw.damage)
    }
}
