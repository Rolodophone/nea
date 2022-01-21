package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import com.badlogic.gdx.utils.TimeUtils
import ktx.ashley.mapperFor

/**
 * Entities with an [ActionComp] can move like a human (e.g. running) and perform combat.
 */
class ActionComp: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<ActionComp>()
	}

	//these may be specified when creating the entity. If not specified they'll have the default value defined below
	var runSpeed = 100f

	//these shouldn't be specified when creating the entity, as they will be controlled by ActionSys
	var action = Action.IDLE
		private set
	var facing = Facing.RIGHT
	var actionStartTime = 0L
	var returnToAction = Action.IDLE
	var actionState = 0

	override fun reset() {
		runSpeed = 100f

		action = Action.IDLE
		facing = Facing.RIGHT
		actionStartTime = 0L
		returnToAction = Action.IDLE
		actionState = 0
	}

	fun startAction(value: Action) {
		if (value != action) {
			actionStartTime = TimeUtils.millis()
			action = value
			actionState = 0
		}
	}
}

enum class Action {
	IDLE, RUN, PUNCH, SPIN_PUNCH, PUSH, HIT_KB, UP_STAIRS, DOWN_STAIRS
}

enum class Facing(val sign: Int) {
	LEFT(-1), RIGHT(1);

	fun reverse() = when (this) {
		LEFT -> RIGHT
		RIGHT -> LEFT
	}
}