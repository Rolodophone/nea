package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import io.github.rolodophone.comboking.component.AnimationComponent
import io.github.rolodophone.comboking.component.GraphicsComponent
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf

class AnimationSystem : IteratingSystem(
	allOf(AnimationComponent::class, GraphicsComponent::class).get()
) {


	override fun processEntity(entity: Entity, deltaTime: Float) {
		val animationComp = entity.getNotNull(AnimationComponent.mapper)
		val graphicsComp = entity.getNotNull(GraphicsComponent.mapper)
	}
}