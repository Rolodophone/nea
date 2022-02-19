package io.github.rolodophone.comboking.asset

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.utils.Disposable

/**
 * Stores and manages the game's sound assets.
 */
@Suppress("unused","PropertyName")
class ComboKingSounds: Disposable {
	private val hitKb: Sound = Gdx.audio.newSound(Gdx.files.internal("audio/hit_kb.ogg"))
	private val punchHard: Sound = Gdx.audio.newSound(Gdx.files.internal("audio/punch_hard.ogg"))
	private val punchSoft: Sound = Gdx.audio.newSound(Gdx.files.internal("audio/punch_soft.ogg"))

	private var volume = 1f

	fun playHitKb() = hitKb.play(volume)
	fun playPunchHard() = punchHard.play(volume)
	fun playPunchSoft() = punchSoft.play(volume)

	fun setVolume(volume: Float) {
		if (volume in 0f..1f) {
			this.volume = volume
		}
	}

	override fun dispose() {
		hitKb.dispose()
		punchHard.dispose()
		punchSoft.dispose()
	}
}