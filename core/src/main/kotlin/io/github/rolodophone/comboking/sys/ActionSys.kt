package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.utils.TimeUtils
import io.github.rolodophone.comboking.comp.*
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf
import ktx.ashley.get
import kotlin.math.abs

/**
 * Acts on entities according to their current action as specified by their [ActionComp].
 */
class ActionSys(
	private val player: Entity
) : IteratingSystem(
	allOf(TransformComp::class, ActionComp::class, HitboxComp::class).get(), 10
) {
	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transformComp = entity.getNotNull(TransformComp.mapper)
		val hitboxComp = entity.getNotNull(HitboxComp.mapper)
		val actionComp = entity.getNotNull(ActionComp.mapper)

		when (actionComp.action) {
			Action.IDLE -> {}
			Action.RUN -> {
				transformComp.x += actionComp.facing.sign * actionComp.runSpeed * deltaTime
				hitboxComp.x += actionComp.facing.sign * actionComp.runSpeed * deltaTime
			}
			Action.PUNCH -> {
				val currentTime = TimeUtils.millis()

				//stop punching after punch frame ends
				if (currentTime > actionComp.actionStartTime + 693L) {
					actionComp.startAction(actionComp.returnToAction)
				}

				// perform punch effect when switches to punch frame
				else if (actionComp.actionState == 0 && currentTime > actionComp.actionStartTime + 231L) {

					val target = findClosestNearbyEntityInDirection(entity, actionComp.facing)

					if (target != null) {
						val targetHPComp = target.getNotNull(HPComp.mapper)
						targetHPComp.hp -= 25f
					}

					actionComp.actionState = 1
				}
			}
			Action.SPIN_PUNCH -> {
				val currentTime = TimeUtils.millis()

				//stop punching after punch frame ends
				if (currentTime > actionComp.actionStartTime + 231*5L) {
					actionComp.startAction(actionComp.returnToAction)
				}

				// perform punch effect when switches to punch frame
				else if (actionComp.actionState == 0 && currentTime > actionComp.actionStartTime + 231*2L) {

					val target = findClosestNearbyEntityInDirection(entity, actionComp.facing.reverse())

					if (target != null) {
						val targetHPComp = target.getNotNull(HPComp.mapper)
						targetHPComp.hp -= 50f
					}

					actionComp.actionState = 1
				}
			}
			Action.PUSH -> {
				val playerTransformComp = player.getNotNull(TransformComp.mapper)
				val playerHitboxComp = player.getNotNull(HitboxComp.mapper)

				when (actionComp.facing) {
					Facing.LEFT -> {
						val deltaX = (hitboxComp.x - playerHitboxComp.width) - playerHitboxComp.x
						if (deltaX < 0) {
							playerHitboxComp.x += deltaX + 1
							playerTransformComp.x += deltaX + 1
						}
					}
					Facing.RIGHT -> {
						val deltaX = (hitboxComp.x + hitboxComp.width) - playerHitboxComp.x
						if (deltaX > 0) {
							playerHitboxComp.x += deltaX - 1
							playerTransformComp.x += deltaX - 1
						}
					}
				}
			}
			Action.HIT_KB -> {
				val currentTime = TimeUtils.millis()

				// perform effect when switches to hit frame
				if (currentTime > actionComp.actionStartTime + 462L * (actionComp.actionState*2 + 1)) {

					val target = player

					val targetHPComp = target.getNotNull(HPComp.mapper)
					targetHPComp.hp -= 10f

					actionComp.actionState++
				}
			}
			Action.UP_STAIRS -> {
				transformComp.y = 95f
				hitboxComp.y = 98f
				actionComp.startAction(Action.IDLE)
			}
			Action.DOWN_STAIRS -> {
				transformComp.y = 5f
				hitboxComp.y = 8f
				actionComp.startAction(Action.IDLE)
			}
		}
	}

	private fun findClosestNearbyEntityInDirection(entity: Entity, facing: Facing): Entity? {
		val hitboxComp = entity.getNotNull(HitboxComp.mapper)

		var target: Entity? = null

		for (possibleTarget in entities) {
			//ignore itself
			if (possibleTarget == entity) continue

			val otherHitboxComp = possibleTarget[HitboxComp.mapper]
			val otherHPComp = possibleTarget[HPComp.mapper]

			//ignore entities without a hitbox or without health
			if (otherHitboxComp == null || otherHPComp == null) continue

			//ignore entities that are too high or too low
			if (otherHitboxComp.top < hitboxComp.bottom || otherHitboxComp.bottom > hitboxComp.top) continue

			if (facing == Facing.LEFT) {
				// ignore entities that are on the right
				if (otherHitboxComp.left > hitboxComp.left) continue

				//ignore enemies that are too far away to the left
				if (otherHitboxComp.right < hitboxComp.left - 20) continue

				//store closest valid target
				if (target == null) {
					target = possibleTarget
				}
				else {
					val targetHitboxComp = target.getNotNull(HitboxComp.mapper)
					if (otherHitboxComp.right > targetHitboxComp.right) {
						target = possibleTarget
					}
				}
			}
			else { //facing right
				// ignore entities that are on the left
				if (otherHitboxComp.right < hitboxComp.right) continue

				//ignore enemies that are too far away to the right
				if (otherHitboxComp.left > hitboxComp.right + 20) continue

				//store closest valid target
				if (target == null) {
					target = possibleTarget
				}
				else {
					val targetHitboxComp = target.getNotNull(HitboxComp.mapper)
					if (otherHitboxComp.left < targetHitboxComp.left) {
						target = possibleTarget
					}
				}
			}
		}

		return target
	}
}