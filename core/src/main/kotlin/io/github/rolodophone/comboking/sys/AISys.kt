package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import io.github.rolodophone.comboking.comp.AIComp
import io.github.rolodophone.comboking.comp.ActionComp
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf

class AISys(
	private val player: Entity
): IteratingSystem(
	allOf(AIComp::class, ActionComp::class).get(), 10
) {
	override fun processEntity(entity: Entity, deltaTime: Float) {
		val entityAIComp = entity.getNotNull(AIComp.mapper)
		val entityMoveComp = entity.getNotNull(ActionComp.mapper)

		entityAIComp.state = entityAIComp.determineState(entity, player)
		entityMoveComp.action = entityAIComp.determineAction(entity, player, entityAIComp.state)
		entityMoveComp.facing = entityAIComp.determineFacing(entity, player, entityAIComp.state)
	}
}