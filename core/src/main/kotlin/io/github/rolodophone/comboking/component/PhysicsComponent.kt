package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class PhysicsComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<PhysicsComponent>()
	}

	var body: Body? = null

	override fun reset() {
		body = null
	}
}