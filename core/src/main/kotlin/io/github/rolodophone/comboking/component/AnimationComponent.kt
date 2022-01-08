package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class AnimationComponent : Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<AnimationComponent>()
	}

	class AnimationLoop(val frameDuration: Float, val frames: List<TextureRegion>)

	var animationLoops = listOf<AnimationLoop>()
	var determineAnimationLoop: (state: Int) -> Int = { _ -> 0 }

	override fun reset() {
		animationLoops = listOf()
		determineAnimationLoop = { _ -> 0 }
	}
}