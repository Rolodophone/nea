package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import io.github.rolodophone.comboking.comp.HPComp
import io.github.rolodophone.comboking.comp.ScoreComp
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf

/**
 * Deletes entities with a HP of 0 or less, if automatic deletion is enabled in their [HPComp].
 */
class KillingSys(
	private val scoreEntity: Entity
): IteratingSystem(
	allOf(HPComp::class).get(), 15
) {
	override fun processEntity(entity: Entity, deltaTime: Float) {
		val hpComp = entity.getNotNull(HPComp.mapper)

		if (hpComp.deleteWhenHP0 && hpComp.hp <= 0) {
			scoreEntity.getNotNull(ScoreComp.mapper).killsScore += 1000
			engine.removeEntity(entity)
		}
	}
}