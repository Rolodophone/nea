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
	var actionExecuted = false
	var returnToAction = Action.IDLE

	override fun reset() {
		runSpeed = 100f

		action = Action.IDLE
		facing = Facing.RIGHT
		actionStartTime = 0L
		actionExecuted = false
		returnToAction = Action.IDLE
	}

	fun startAction(value: Action) {
		if (value != action) {
			actionStartTime = TimeUtils.millis()
			action = value
		}
	}
}

enum class Action {
	IDLE, RUN, PUNCH, SPIN_PUNCH, PUSH, HIT_KB, USE_DOOR
}

enum class Facing(val sign: Int) {
	LEFT(-1), RIGHT(1);

	fun reverse() = when (this) {
		LEFT -> RIGHT
		RIGHT -> LEFT
	}
}