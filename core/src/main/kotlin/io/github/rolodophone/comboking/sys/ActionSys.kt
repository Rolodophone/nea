package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.utils.TimeUtils
import io.github.rolodophone.comboking.comp.*
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf
import ktx.ashley.get

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

				// perform punch effect when switches to punch frame
				if (currentTime > actionComp.actionStartTime + 231L) {

					// find entity closest to this entity in direction of punch
					var target: Entity? = null
					for (possibleTarget in entities) {
						val otherHitboxComp = possibleTarget[HitboxComp.mapper]
						val otherHPComp = possibleTarget[HPComp.mapper]

						//do not affect entities without a hitbox or without health
						if (otherHitboxComp == null || otherHPComp == null) continue

						if (actionComp.facing == Facing.LEFT) {
							// ignore entities that are too close or to the right
							if (otherHitboxComp.x + otherHitboxComp.width > hitboxComp.x) continue

							//ignore enemies that are too far to the left
							if (otherHitboxComp.x + otherHitboxComp.width < hitboxComp.x - 20) continue

							//store closest valid target
							if (target == null) {
								target = possibleTarget
							}
							else {
								val targetHitboxComp = target.getNotNull(HitboxComp.mapper)
								if (otherHitboxComp.x + otherHitboxComp.width > targetHitboxComp.x + targetHitboxComp
										.width) {
									target = possibleTarget
								}
							}
						}
						else { //facing right
							// ignore entities that are too close or to the left
							if (otherHitboxComp.x < hitboxComp.x + hitboxComp.width) continue

							//ignore enemies that are too far to the right
							if (otherHitboxComp.x > hitboxComp.x + hitboxComp.width + 20) continue

							//store closest valid target
							if (target == null) {
								target = possibleTarget
							}
							else {
								val targetHitboxComp = target.getNotNull(HitboxComp.mapper)
								if (otherHitboxComp.x < targetHitboxComp.x) {
									target = possibleTarget
								}
							}
						}
					}

					if (target != null) {
						val targetHPComp = target.getNotNull(HPComp.mapper)
						targetHPComp.hp -= 25f
					}
				}
			}
		}
	}
}