package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class ScoreComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<ScoreComponent>()
	}

	var score = 0f

	override fun reset() {
		score = 0f
	}
}