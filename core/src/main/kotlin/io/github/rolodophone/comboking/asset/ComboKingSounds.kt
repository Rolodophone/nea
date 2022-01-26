package io.github.rolodophone.comboking.asset

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.utils.Disposable

/**
 * Stores and manages the game's sound assets.
 */
@Suppress("unused","PropertyName")
class ComboKingSounds: Disposable {
	val hit_kb: Sound = Gdx.audio.newSound(Gdx.files.internal("audio/hit_kb.ogg"))
	val punch_hard: Sound = Gdx.audio.newSound(Gdx.files.internal("audio/punch_hard.ogg"))
	val punch_soft: Sound = Gdx.audio.newSound(Gdx.files.internal("audio/punch_soft.ogg"))

	override fun dispose() {
		hit_kb.dispose()
		punch_hard.dispose()
		punch_soft.dispose()
	}
}