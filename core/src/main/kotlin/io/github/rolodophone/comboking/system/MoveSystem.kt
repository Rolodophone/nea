package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import io.github.rolodophone.comboking.MoveAction
import io.github.rolodophone.comboking.component.MoveComponent
import io.github.rolodophone.comboking.component.TransformComponent
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf

/**
 * Moves entities according to their current move action as specified by their [MoveComponent].
 */
class MoveSystem: IteratingSystem(
	allOf(TransformComponent::class, MoveComponent::class).get()
) {
	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transformComp = entity.getNotNull(TransformComponent.mapper)
		val moveComp = entity.getNotNull(MoveComponent.mapper)

		when (moveComp.moveAction) {
			MoveAction.STOP -> {}
			MoveAction.RUN_LEFT -> {
				transformComp.x -= moveComp.runSpeed * deltaTime
			}
			MoveAction.RUN_RIGHT -> {
				transformComp.x += moveComp.runSpeed * deltaTime
			}
		}
	}
}