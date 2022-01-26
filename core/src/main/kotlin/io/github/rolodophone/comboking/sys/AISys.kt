package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import io.github.rolodophone.comboking.comp.AIComp
import io.github.rolodophone.comboking.comp.ActionComp
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf

/**
 * Chooses an action for each entity based on the current game state and the rules specified by the entity's [AIComp].
 */
class AISys(
	private val player: Entity,
	private val timeSys: TimeSys
): IteratingSystem(
	allOf(AIComp::class, ActionComp::class).get(), 10
) {
	override fun processEntity(entity: Entity, deltaTime: Float) {
		val entityAIComp = entity.getNotNull(AIComp.mapper)
		val entityMoveComp = entity.getNotNull(ActionComp.mapper)

		entityAIComp.state = entityAIComp.determineState(entity, player)
		entityMoveComp.startAction(entityAIComp.determineAction(entity, player, entityAIComp.state), timeSys.appUptime)
		entityMoveComp.facing = entityAIComp.determineFacing(entity, player, entityAIComp.state)
	}
}