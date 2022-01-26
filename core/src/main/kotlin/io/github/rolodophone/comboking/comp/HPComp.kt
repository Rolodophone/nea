package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Entities with an [HPComp] can take damage and have a finite number of health points.
 */
class HPComp : Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<HPComp>()
	}

	var deleteWhenHP0 = false
	var hp = 1f

	override fun reset() {
		deleteWhenHP0 = false
		hp = 1f
	}
}