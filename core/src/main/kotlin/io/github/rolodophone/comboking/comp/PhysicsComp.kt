package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Not yet implemented.
 */
class PhysicsComp: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<PhysicsComp>()
	}

	var body: Body? = null

	override fun reset() {
		body = null
	}
}