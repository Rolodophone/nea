package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * The score entity has a [ScoreComp] so it can store the current score.
 */
class ScoreComp: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<ScoreComp>()
	}

	var distScore = 0
	var killsScore = 0
	var totalScore = 0

	override fun reset() {
		distScore = 0
		killsScore = 0
		totalScore = 0
	}
}