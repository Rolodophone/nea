package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.comp.HPComp
import io.github.rolodophone.comboking.comp.ScoreComp
import io.github.rolodophone.comboking.event.GameEvent
import io.github.rolodophone.comboking.event.GameEventManager
import io.github.rolodophone.comboking.screen.GameOverScreen
import io.github.rolodophone.comboking.screen.MainMenuScreen
import io.github.rolodophone.comboking.util.getNotNull

class GameOverSys(
	private val game: ComboKing,
	private val gameEventManager: GameEventManager,
	private val player: Entity,
	private val scoreEntity: Entity
) : EntitySystem(20) {
	override fun update(deltaTime: Float) {
		val playerHPComp = player.getNotNull(HPComp.mapper)

		if (playerHPComp.hp <= 0) {
			game.setScreen<GameOverScreen>()
			GameEvent.GameOverEvent.scoreComp = scoreEntity.getNotNull(ScoreComp.mapper)
			gameEventManager.trigger(GameEvent.GameOverEvent)
		}
	}
}