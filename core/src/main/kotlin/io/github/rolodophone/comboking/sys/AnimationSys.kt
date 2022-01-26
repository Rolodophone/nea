package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import io.github.rolodophone.comboking.ckLogger
import io.github.rolodophone.comboking.comp.*
import io.github.rolodophone.comboking.util.getNotNull
import io.github.rolodophone.comboking.util.repr
import ktx.ashley.allOf
import ktx.ashley.get

private val log = ckLogger<AnimationSys>()

/**
 * Sets entities' textures according to the rules specified by their [AnimationComp].
 */
class AnimationSys(
	private val timeSys: TimeSys
) : IteratingSystem(
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

		if (newAnimationLoopIndex == animationComp.animationLoop) {
			//animation loop hasn't changed
			if (animLoop.frameDuration != -1f &&
				timeSys.appUptime > animationComp.timeOfLastFrameChange + animLoop.frameDuration
			) {
				//frame increment is due
				val numFrameIncrements = ((timeSys.appUptime - animationComp.timeOfLastFrameChange) /
						animLoop.frameDuration).toInt()
				animationComp.frameIndex = (animationComp.frameIndex + numFrameIncrements) % animLoop.frames.count()
				animationComp.timeOfLastFrameChange += numFrameIncrements * animLoop.frameDuration
				graphicsComp.textureRegion = animLoop.frames[animationComp.frameIndex]
			}
		}
		else {
			//animation loop has changed
			animationComp.frameIndex = 0
			animationComp.timeOfLastFrameChange = timeSys.appUptime
			animationComp.animationLoop = newAnimationLoopIndex
			graphicsComp.textureRegion = animLoop.frames[0]
			if (entity[InfoComp.mapper]?.name == "Player") {
				log.debug { "${entity.repr()} changed animation to$animLoop" }
			}
		}

		if (animationComp.flipWhenFacingLeft && actionComp != null) {
			graphicsComp.flippedHorizontally = when (actionComp.facing) {
				Facing.LEFT -> true
				Facing.RIGHT -> false
			}
		}
	}
}