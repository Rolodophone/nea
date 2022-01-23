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

	var distance = 0f
	var kills = 0
	var time = 0f
	var score = 0

	override fun reset() {
		distance = 0f
		kills = 0
		time = 0f
		score = 0
	}
}