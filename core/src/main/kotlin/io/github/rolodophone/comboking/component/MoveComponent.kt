package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Entities with a [MoveComponent] can move like a human (e.g. running, jumping).
 */
class MoveComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<MoveComponent>()
		const val ACC_GRAVITY = -2000f
	}

	enum class MoveAction {
		STOP, RUN_LEFT, RUN_RIGHT, JUMP
	}

	//these may be specified when creating the entity. If not specified they'll have the default value defined below
	var runSpeed = 100f
	var jumpSpeed = 600f

	//these shouldn't be specified when creating the entity, as they will be controlled by MoveSystem
	var moveAction = MoveAction.STOP
	var yVelocity = 0f

	override fun reset() {
		runSpeed = 100f
		jumpSpeed = 600f

		moveAction = MoveAction.STOP
		yVelocity = 0f
	}
}