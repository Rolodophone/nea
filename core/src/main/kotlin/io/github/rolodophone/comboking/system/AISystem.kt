package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import io.github.rolodophone.comboking.component.AIComponent
import io.github.rolodophone.comboking.component.MoveComponent
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf

class AISystem(
	private val player: Entity
): IteratingSystem(
	allOf(AIComponent::class, MoveComponent::class).get()
) {
	override fun processEntity(entity: Entity, deltaTime: Float) {
		val entityAIComp = entity.getNotNull(AIComponent.mapper)
		val entityMoveComp = entity.getNotNull(MoveComponent.mapper)

		val state = entityAIComp.determineState(entity, player)
		entityMoveComp.moveAction = entityAIComp.determineMoveAction(entity, player, state)
	}
}