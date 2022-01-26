package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Entities with an [AnimationComp] have an animated texture.
 */
class AnimationComp : Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<AnimationComp>()
	}

	class AnimationLoop(val frameDuration: Float, val frames: List<TextureRegion>) {
		override fun toString() = "AnimationLoop(${frames.joinToString(", ")})"
	}

	var animationLoops = listOf<AnimationLoop>()
	var determineAnimationLoop: (state: Int?, action: Action?) -> Int = { _, _ -> 0 }
	var flipWhenFacingLeft = true

	//for use of AnimationSys
	var animationLoop = 0
	var timeOfLastFrameChange = 0f
	var frameIndex = 0

	override fun reset() {
		animationLoops = listOf()
		determineAnimationLoop = { _, _ -> 0 }
		flipWhenFacingLeft = true

		animationLoop = 0
		timeOfLastFrameChange = 0f
		frameIndex = 0
	}
}