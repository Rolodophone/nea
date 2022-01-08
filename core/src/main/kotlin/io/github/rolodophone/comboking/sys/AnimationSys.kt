package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import io.github.rolodophone.comboking.comp.AnimationComp
import io.github.rolodophone.comboking.comp.GraphicsComp
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf

class AnimationSys : IteratingSystem(
	allOf(AnimationComp::class, GraphicsComp::class).get()
) {


	override fun processEntity(entity: Entity, deltaTime: Float) {
		val animationComp = entity.getNotNull(AnimationComp.mapper)
		val graphicsComp = entity.getNotNull(GraphicsComp.mapper)
	}
}