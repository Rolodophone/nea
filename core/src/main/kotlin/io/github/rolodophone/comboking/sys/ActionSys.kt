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
class ActionSys: IteratingSystem(
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
					actionComp.actionExecuted = false
				}

				// perform punch effect when switches to punch frame
				else if (!actionComp.actionExecuted && currentTime > actionComp.actionStartTime + 231L) {

					val target = findClosestNearbyEntityInDirection(entity, actionComp.facing)

					if (target != null) {
						val targetHPComp = target.getNotNull(HPComp.mapper)
						targetHPComp.hp -= 25f
					}

					actionComp.actionExecuted = true
				}
			}
			Action.SPIN_PUNCH -> {
				val currentTime = TimeUtils.millis()

				//stop punching after punch frame ends
				if (currentTime > actionComp.actionStartTime + 231*5L) {
					actionComp.startAction(actionComp.returnToAction)
					actionComp.actionExecuted = false
				}

				// perform punch effect when switches to punch frame
				else if (!actionComp.actionExecuted && currentTime > actionComp.actionStartTime + 231*2L) {

					val target = findClosestNearbyEntityInDirection(entity, actionComp.facing.reverse())

					if (target != null) {
						val targetHPComp = target.getNotNull(HPComp.mapper)
						targetHPComp.hp -= 50f
					}

					actionComp.actionExecuted = true
				}
			}
			Action.USE_DOOR -> {

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

	private fun findClosestDoors(entity: Entity): List<Entity> {
		val thisHitboxComp = entity.getNotNull(HitboxComp.mapper)

		var door1: Entity? = null
		var door2: Entity? = null

		for (possibleDoor in entities) {

			//ignore entities that are not doors
			if (possibleDoor[InfoComp.mapper]?.name != "Door") continue

			val possibleDoorTransformComp = possibleDoor[TransformComp.mapper] ?: continue

			if (door1 == null) {
				door1 = possibleDoor
			}
			else {
				val door1HitboxComp = door1.getNotNull(HitboxComp.mapper)

				if (abs(thisHitboxComp.x - possibleDoorTransformComp.x) <
					abs(thisHitboxComp.x - door1HitboxComp.x)) {
					door1 = possibleDoor
				}
				else if (abs(thisHitboxComp.x - possibleDoorTransformComp.x) ==
					     abs(thisHitboxComp.x - door1HitboxComp.x)) {
					door2 = possibleDoor
				}
			}
		}

		return listOf(door1!!, door2!!)
	}
}