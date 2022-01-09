package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.utils.TimeUtils
import io.github.rolodophone.comboking.comp.*
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf
import ktx.ashley.get

class AnimationSys : IteratingSystem(
	allOf(AnimationComp::class, GraphicsComp::class).get(), 20
) {
	override fun processEntity(entity: Entity, deltaTime: Float) {
		val animationComp = entity.getNotNull(AnimationComp.mapper)
		val graphicsComp = entity.getNotNull(GraphicsComp.mapper)
		val aiComp = entity[AIComp.mapper]
		val actionComp = entity[ActionComp.mapper]

		//determine the animation loop we should be on
		val newAnimationLoopIndex = animationComp.determineAnimationLoop(
			aiComp?.state,
			actionComp?.action
		)

		val animLoop = animationComp.animationLoops[newAnimationLoopIndex]
		val currentTime = TimeUtils.millis()

		if (newAnimationLoopIndex == animationComp.animationLoop) {
			//animation loop hasn't changed
			if (currentTime > animationComp.timeOfLastFrameChange + animLoop.frameDuration) {
				//frame increment is due
				animationComp.frameIndex = (animationComp.frameIndex + 1) % animLoop.frames.count()
				animationComp.timeOfLastFrameChange = currentTime
				graphicsComp.textureRegion = animLoop.frames[animationComp.frameIndex]
			}
		}
		else {
			//animation loop has changed
			animationComp.frameIndex = 0
			animationComp.timeOfLastFrameChange = currentTime
			animationComp.animationLoop = newAnimationLoopIndex
			graphicsComp.textureRegion = animLoop.frames[0]
		}

		if (animationComp.flipWhenFacingLeft && actionComp != null) {
			graphicsComp.flippedHorizontally = when (actionComp.facing) {
				Facing.LEFT -> true
				Facing.RIGHT -> false
			}
		}
	}
}