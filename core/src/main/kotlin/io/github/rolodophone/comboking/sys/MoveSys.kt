package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import io.github.rolodophone.comboking.comp.Action
import io.github.rolodophone.comboking.comp.ActionComp
import io.github.rolodophone.comboking.comp.Facing
import io.github.rolodophone.comboking.comp.TransformComp
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf

/**
 * Moves entities according to their current move action as specified by their [ActionComp].
 */
class MoveSys: IteratingSystem(
	allOf(TransformComp::class, ActionComp::class).get(), 10
) {
	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transformComp = entity.getNotNull(TransformComp.mapper)
		val actionComp = entity.getNotNull(ActionComp.mapper)

		when (actionComp.action) {
			Action.IDLE -> {}
			Action.RUN -> {
				when (actionComp.facing) {
					Facing.LEFT -> transformComp.x -= actionComp.runSpeed * deltaTime
					Facing.RIGHT -> transformComp.x += actionComp.runSpeed * deltaTime
				}
			}
		}
	}
}