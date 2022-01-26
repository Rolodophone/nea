package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.asset.ComboKingMusic
import io.github.rolodophone.comboking.screen.ComboKingScreen
import io.github.rolodophone.comboking.screen.GameScreen

/**
 * Handles playing the game music.
 */
class MusicSys(
	private val music: ComboKingMusic,
	private val game: ComboKing
) : EntitySystem(40) {

	private var prevScreen: ComboKingScreen? = null

	override fun update(deltaTime: Float) {
		if (game.shownScreen != prevScreen) {
			when (game.shownScreen) {
				is GameScreen -> {
					music.menu.stop()
					music.game.isLooping = true
					music.game.play()
				}
				else -> {
					music.game.stop()
					music.menu.isLooping = true
					music.menu.play()
				}
			}

			prevScreen = game.shownScreen
		}
	}
}