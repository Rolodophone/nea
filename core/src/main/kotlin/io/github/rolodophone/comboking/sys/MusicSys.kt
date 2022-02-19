package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.CKPrefs
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.asset.ComboKingMusic
import io.github.rolodophone.comboking.screen.ComboKingScreen
import io.github.rolodophone.comboking.screen.GameScreen

/**
 * Handles playing the game music.
 */
class MusicSys(
	private val music: ComboKingMusic,
	private val game: ComboKing,
	private val ckPrefs: CKPrefs
) : EntitySystem(40) {

	private var prevScreen: ComboKingScreen? = null

	override fun addedToEngine(engine: Engine) {
		music.setVolume(ckPrefs.getMusicVolume())
	}

	override fun update(deltaTime: Float) {
		if (game.shownScreen != prevScreen) {
			when (game.shownScreen) {
				is GameScreen -> music.playGame()
				else -> music.playMenu()
			}

			prevScreen = game.shownScreen
		}
	}
}