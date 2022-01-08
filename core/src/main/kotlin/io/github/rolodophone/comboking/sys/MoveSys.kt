package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import io.github.rolodophone.comboking.comp.MoveComp.MoveAction
import io.github.rolodophone.comboking.comp.MoveComp
import io.github.rolodophone.comboking.comp.TransformComp
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf

/**
 * Moves entities according to their current move action as specified by their [MoveComp].
 */
class MoveSys: IteratingSystem(
	allOf(TransformComp::class, MoveComp::class).get()
) {
	private var playerIsJumping = false

	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transformComp = entity.getNotNull(TransformComp.mapper)
		val moveComp = entity.getNotNull(MoveComp.mapper)

		when (moveComp.moveAction) {
			MoveAction.STOP -> {}
			MoveAction.RUN_LEFT -> {
				transformComp.x -= moveComp.runSpeed * deltaTime
			}
			MoveAction.RUN_RIGHT -> {
				transformComp.x += moveComp.runSpeed * deltaTime
			}
			MoveAction.JUMP -> {
				//TODO temporary way to detect when the player has just jumped. I'll redesign the way moving works once
				//TODO I start enemy AI because I will know what I need better then. Maybe I should have 2 different
				//TODO enums: one for player input and one for any sentient entity's current movement.

				if (playerIsJumping) {
					moveComp.yVelocity += MoveComp.ACC_GRAVITY * deltaTime
					transformComp.y += moveComp.yVelocity * deltaTime
				}
				else {
					moveComp.yVelocity = moveComp.jumpSpeed
					playerIsJumping = true
				}
			}
		}

		if (moveComp.moveAction != MoveAction.JUMP) playerIsJumping = false
	}
}