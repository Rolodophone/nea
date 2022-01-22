package io.github.rolodophone.comboking.asset

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.utils.Disposable

@Suppress("unused","PropertyName")
class ComboKingSounds: Disposable {
	val punch_hard: Sound = Gdx.audio.newSound(Gdx.files.internal("audio/punch_hard.ogg"))
	val punch_soft: Sound = Gdx.audio.newSound(Gdx.files.internal("audio/punch_soft.ogg"))

	override fun dispose() {
		punch_hard.dispose()
		punch_soft.dispose()
	}
}