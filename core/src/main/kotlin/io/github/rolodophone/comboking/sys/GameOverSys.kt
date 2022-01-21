package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.comp.HPComp
import io.github.rolodophone.comboking.screen.GameOverScreen
import io.github.rolodophone.comboking.screen.MainMenuScreen
import io.github.rolodophone.comboking.util.getNotNull

class GameOverSys(
	private val game: ComboKing,
	private val player: Entity
) : EntitySystem(20) {
	override fun update(deltaTime: Float) {
		val playerHPComp = player.getNotNull(HPComp.mapper)

		if (playerHPComp.hp <= 0) {
			game.addScreen(GameOverScreen(game))
			game.setScreen<GameOverScreen>()
		}
	}
}