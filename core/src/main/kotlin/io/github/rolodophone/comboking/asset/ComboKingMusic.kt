package io.github.rolodophone.comboking.asset

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Disposable

class ComboKingMusic: Disposable {
	val game = Gdx.audio.newMusic(Gdx.files.internal("audio/game.ogg"))
	val menu = Gdx.audio.newMusic(Gdx.files.internal("audio/menu.ogg"))

	override fun dispose() {
		game.dispose()
		menu.dispose()
	}
}