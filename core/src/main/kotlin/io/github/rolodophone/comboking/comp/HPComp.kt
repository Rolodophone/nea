package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class HPComp : Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<HPComp>()
	}

	var hp = 1f

	override fun reset() {
		hp = 1f
	}
}