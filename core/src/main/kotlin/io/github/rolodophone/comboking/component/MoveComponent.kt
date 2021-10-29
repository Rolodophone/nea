package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import io.github.rolodophone.comboking.MoveAction
import ktx.ashley.mapperFor

class MoveComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<MoveComponent>()
	}

	var moveAction = MoveAction.STOP

	var runSpeed = 100f

	override fun reset() {
		moveAction = MoveAction.STOP

		runSpeed = 100f
	}
}