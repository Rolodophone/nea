package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Entities with a [MoveComponent] can move like a human (e.g. running, jumping).
 */
class MoveComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<MoveComponent>()
	}

	enum class MoveAction {
		STOP, RUN_LEFT, RUN_RIGHT
	}

	var moveAction = MoveAction.STOP

	var runSpeed = 100f

	override fun reset() {
		moveAction = MoveAction.STOP

		runSpeed = 100f
	}
}