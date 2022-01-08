package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import io.github.rolodophone.comboking.component.MoveComponent.MoveAction

/**
 * Entities with an [AIComponent] move and fight according to a rule-based AI
 */
class AIComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<AIComponent>()
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