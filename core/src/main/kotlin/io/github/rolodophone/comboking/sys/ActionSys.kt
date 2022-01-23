package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import io.github.rolodophone.comboking.asset.ComboKingSounds
import io.github.rolodophone.comboking.comp.*
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf
import ktx.ashley.get

/**
 * Acts on entities according to their current action as specified by their [ActionComp].
 */
class ActionSys(
	private val player: Entity,
	private val sounds: ComboKingSounds,
	private val timeSys: TimeSys
) : IteratingSystem(
	allOf(TransformComp::class, ActionComp::class, HitboxComp::class).get(), 10
) {
	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transformComp = entity.getNotNull(TransformComp.mapper)
		val hitboxComp = entity.getNotNull(HitboxComp.mapper)
		val actionComp = entity.getNotNull(ActionComp.mapper)

		when (actionComp.action) {
			Action.IDLE -> {}
			Action.WALK -> {
				transformComp.x += actionComp.facing.sign * actionComp.walkSpeed * deltaTime
				hitboxComp.x += actionComp.facing.sign * actionComp.walkSpeed * deltaTime
			}
			Action.RUN -> {
				transformComp.x += actionComp.facing.sign * actionComp.runSpeed * deltaTime
				hitboxComp.x += actionComp.facing.sign * actionComp.runSpeed * deltaTime
			}
			Action.PUNCH -> {
				//stop punching after punch frame ends
				if (timeSys.appUptime > actionComp.actionStartTime + 0.693f) {
					actionComp.startAction(actionComp.returnToAction, timeSys.appUptime)
				}

				// perform punch effect when switches to punch frame
				else if (actionComp.actionState == 0 && timeSys.appUptime > actionComp.actionStartTime + 0.231f) {

					val target = findClosestNearbyEntityInDirection(entity, actionComp.facing)

					if (target != null) {
						val targetHPComp = target.getNotNull(HPComp.mapper)
						targetHPComp.hp -= 25f
						sounds.punch_soft.play()
					}

					actionComp.actionState = 1
				}
			}
			Action.SPIN_PUNCH -> {
				//stop punching after punch frame ends
				if (timeSys.appUptime > actionComp.actionStartTime + 0.231f*5) {
					actionComp.startAction(actionComp.returnToAction, timeSys.appUptime)
				}

				// perform punch effect when switches to punch frame
				else if (actionComp.actionState == 0 && timeSys.appUptime > actionComp.actionStartTime + 0.231f*2) {

					val target = findClosestNearbyEntityInDirection(entity, actionComp.facing.reverse())

					if (target != null) {
						val targetHPComp = target.getNotNull(HPComp.mapper)
						targetHPComp.hp -= 50f
						sounds.punch_hard.play()
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
				// perform effect when switches to hit frame
				if (timeSys.appUptime > actionComp.actionStartTime + 0.462f * (actionComp.actionState*2 + 1)) {

					val target = player

					val targetHPComp = target.getNotNull(HPComp.mapper)
					targetHPComp.hp -= 10f

					sounds.hit_kb.play()

					actionComp.actionState++
				}
			}
			Action.UP_STAIRS -> {
				transformComp.y = 95f
				hitboxComp.y = 98f
				actionComp.startAction(Action.IDLE, timeSys.appUptime)
			}
			Action.DOWN_STAIRS -> {
				transformComp.y = 5f
				hitboxComp.y = 8f
				actionComp.startAction(Action.IDLE, timeSys.appUptime)
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