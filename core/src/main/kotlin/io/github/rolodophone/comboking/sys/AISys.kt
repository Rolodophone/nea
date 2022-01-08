package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import io.github.rolodophone.comboking.comp.AIComp
import io.github.rolodophone.comboking.comp.MoveComp
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf

class AISys(
	private val player: Entity
): IteratingSystem(
	allOf(AIComp::class, MoveComp::class).get()
) {
	override fun processEntity(entity: Entity, deltaTime: Float) {
		val entityAIComp = entity.getNotNull(AIComp.mapper)
		val entityMoveComp = entity.getNotNull(MoveComp.mapper)

		val state = entityAIComp.determineState(entity, player)
		entityMoveComp.moveAction = entityAIComp.determineMoveAction(entity, player, state)
	}
}