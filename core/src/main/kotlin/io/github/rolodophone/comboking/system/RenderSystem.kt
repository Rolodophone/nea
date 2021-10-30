package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.component.GraphicsComponent
import io.github.rolodophone.comboking.component.TransformComponent
import io.github.rolodophone.comboking.util.getNotNull
import io.github.rolodophone.comboking.util.setBounds
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.logger

private val log = logger<RenderSystem>()

/**
 * Renders the entities on the screen
 */
class RenderSystem(
	private val batch: Batch,
	private val viewport: Viewport
): SortedIteratingSystem(
	allOf(TransformComponent::class, GraphicsComponent::class).get(),
	compareBy { entity -> entity.getNotNull(TransformComponent.mapper).z }
) {
	override fun update(deltaTime: Float) {
		viewport.apply()
		batch.use(viewport.camera.combined) {
			super.update(deltaTime)
		}
	}

	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transformComp = entity.getNotNull(TransformComponent.mapper)
		val graphicsComp = entity.getNotNull(GraphicsComponent.mapper)

		if (graphicsComp.sprite.texture == null) {
			log.error { "Entity $entity has no texture for rendering" }
			return
		}

		if (!graphicsComp.visible) return

		graphicsComp.sprite.setBounds(transformComp.x, transformComp.y, transformComp.width, transformComp.height)
		graphicsComp.sprite.draw(batch)
	}
}