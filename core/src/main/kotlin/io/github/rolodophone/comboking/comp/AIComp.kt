package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import io.github.rolodophone.comboking.comp.MoveComp.MoveAction

/**
 * Entities with an [AIComp] move and fight according to a rule-based AI
 */
class AIComp: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<AIComp>()
	}

	var determineState: (enemy: Entity, player: Entity) -> Int
			= { _, _ -> 0 }
	var determineMoveAction: (enemy: Entity, player: Entity, state: Int) -> MoveAction
			= { _, _, _ -> MoveAction.STOP }

	override fun reset() {
		determineState = { _, _ -> 0 }
		determineMoveAction = { _, _, _ -> MoveAction.STOP }
	}
}