package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Not yet implemented.
 */
class PlayerComponent : Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<PlayerComponent>()
	}

	override fun reset() {}
}
