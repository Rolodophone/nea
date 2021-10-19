package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
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
	private val gameViewport: Viewport
): SortedIteratingSystem(
	allOf(TransformComponent::class, GraphicsComponent::class).get(),
	compareBy { entity -> entity[TransformComponent.mapper] }
) {
	override fun update(deltaTime: Float) {
//		(gameViewport.camera as OrthographicCamera).zoom = 0.5f
//		gameViewport.camera.update()
//		(gameViewport.camera as OrthographicCamera).translate(gameViewport.worldWidth / 2f, gameViewport.worldHeight /
//				2f)
//		gameViewport.camera.update()
		gameViewport.apply()
		batch.use(gameViewport.camera.combined) {
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

		graphicsComp.sprite.setBounds(transformComp.rect)
		graphicsComp.sprite.rotation = transformComp.rotation
		graphicsComp.sprite.draw(batch)
	}
}