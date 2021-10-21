package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.component.ButtonComponent
import io.github.rolodophone.comboking.component.TransformComponent
import io.github.rolodophone.comboking.util.getNotNull
import io.github.rolodophone.comboking.util.unprojectX
import io.github.rolodophone.comboking.util.unprojectY
import ktx.ashley.allOf

class ButtonSystem(
	private val viewport: Viewport
) : IteratingSystem(
	allOf(ButtonComponent::class, TransformComponent::class).get()
) {
	override fun update(deltaTime: Float) {
		if (Gdx.input.justTouched()) {
			super.update(deltaTime)
		}
	}

	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transformComp = entity.getNotNull(TransformComponent.mapper)

		if (transformComp.rect.contains(
			viewport.unprojectX(Gdx.input.x.toFloat()),
			viewport.unprojectY(Gdx.input.y.toFloat())
		)) {
			val buttonComp = entity.getNotNull(ButtonComponent.mapper)
			buttonComp.onPress()
		}
	}
}