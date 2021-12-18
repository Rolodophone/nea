package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * The score entity has a [ScoreComponent] so it can store the current score.
 */
class ScoreComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<ScoreComponent>()
	}

	var score = 0

	override fun reset() {
		score = 0
	}
}