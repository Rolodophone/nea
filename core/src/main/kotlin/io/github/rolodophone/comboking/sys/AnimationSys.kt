package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.utils.TimeUtils
import io.github.rolodophone.comboking.comp.AIComp
import io.github.rolodophone.comboking.comp.AnimationComp
import io.github.rolodophone.comboking.comp.GraphicsComp
import io.github.rolodophone.comboking.comp.MoveComp
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
		val moveComp = entity[MoveComp.mapper]

		//determine the animation loop we should be on
		val newAnimationLoopIndex = animationComp.determineAnimationLoop(
			aiComp?.state,
			moveComp?.moveAction
		)

		val animLoop = animationComp.animationLoops[newAnimationLoopIndex]

		if (newAnimationLoopIndex == animationComp.animationLoop) {
			//animation loop hasn't changed
			if (TimeUtils.millis() > animationComp.timeOfLastFrameChange + animLoop.frameDuration) {
				//frame increment is due
				animationComp.frameIndex = (animationComp.frameIndex + 1) % animLoop.frames.count()
				animationComp.timeOfLastFrameChange = TimeUtils.millis()
				graphicsComp.textureRegion = animLoop.frames[animationComp.frameIndex]
			}
		}
		else {
			//animation loop has changed
			animationComp.frameIndex = 0
			animationComp.timeOfLastFrameChange = TimeUtils.millis()
			animationComp.animationLoop = newAnimationLoopIndex
			graphicsComp.textureRegion = animLoop.frames[0]
		}
	}
}