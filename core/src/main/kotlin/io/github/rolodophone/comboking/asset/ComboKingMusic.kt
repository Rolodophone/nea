package io.github.rolodophone.comboking.asset

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.utils.Disposable

/**
 * Stores and manages the game's music assets.
 */
class ComboKingMusic: Disposable {
	private val game: Music = Gdx.audio.newMusic(Gdx.files.internal("audio/game.ogg"))
	private val menu: Music = Gdx.audio.newMusic(Gdx.files.internal("audio/menu.ogg"))

	init {
		game.isLooping = true
		menu.isLooping = true
	}

	fun playGame() {
		menu.stop()
		game.play()
	}

	fun playMenu() {
		game.stop()
		menu.play()
	}

	fun setVolume(volume: Float) {
		game.volume = volume
		menu.volume = volume
	}

	override fun dispose() {
		game.dispose()
		menu.dispose()
	}
}