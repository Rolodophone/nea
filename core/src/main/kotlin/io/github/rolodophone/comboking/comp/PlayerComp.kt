package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Not yet implemented.
 */
class PlayerComp : Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<PlayerComp>()
	}

	override fun reset() {}
}
