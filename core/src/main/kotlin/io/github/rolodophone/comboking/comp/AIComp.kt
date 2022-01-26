package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Entities with an [AIComp] move and fight according to a rule-based AI.
 */
class AIComp: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<AIComp>()
	}

	var determineState: (enemy: Entity, player: Entity) -> Int
			= { _, _ -> 0 }
	var determineAction: (enemy: Entity, player: Entity, state: Int) -> Action
			= { _, _, _ -> Action.IDLE }
	var determineFacing: (enemy: Entity, player: Entity, state: Int) -> Facing
			= { _, _, _ -> Facing.RIGHT }

	var state = 0

	override fun reset() {
		determineState = { _, _ -> 0 }
		determineAction = { _, _, _ -> Action.IDLE }
		determineFacing = { _, _, _ -> Facing.RIGHT }

		state = 0
	}
}