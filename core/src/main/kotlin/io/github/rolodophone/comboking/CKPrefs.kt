package io.github.rolodophone.comboking

import com.badlogic.gdx.Gdx

/**
 * Manages putting values to and getting values from the persistent storage.
 */
class CKPrefs {
	private val prefs = Gdx.app.getPreferences("io.github.rolodophone.comboking")

	fun putSFXVolume(value: Float) {
		prefs.putFloat("sfx_volume", value)
		prefs.flush()
	}

	fun putMusicVolume(value: Float) {
		prefs.putFloat("music_volume", value)
		prefs.flush()
	}

	fun putHighscore(value: Int) {
		prefs.putInteger("highscore", value)
		prefs.flush()
	}

	fun getSFXVolume() = prefs.getFloat("sfx_volume", 1f)

	fun getMusicVolume() = prefs.getFloat("music_volume", 1f)

	fun getHighscore() = prefs.getInteger("highscore", 0)
}