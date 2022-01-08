package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.comp.GraphicsComp
import io.github.rolodophone.comboking.comp.TransformComp
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf
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
	allOf(TransformComp::class, GraphicsComp::class).get(),
	compareBy { entity -> entity.getNotNull(TransformComp.mapper).z }
) {
	private val sprite = Sprite() //reused for each entity to draw using a batch

	override fun update(deltaTime: Float) {
		viewport.apply()
		batch.use(viewport.camera.combined) {
			super.update(deltaTime)
		}
	}

	override fun processEntity(entity: Entity, deltaTime: Float) {
		val graphicsComp = entity.getNotNull(GraphicsComp.mapper)

		if (!graphicsComp.visible) return

		if (graphicsComp.textureRegion == null) {
			log.error { "Entity $entity is set to be visible but has no texture." }
			return
		}

		val transformComp = entity.getNotNull(TransformComp.mapper)

		sprite.setBounds(transformComp.x, transformComp.y, transformComp.width, transformComp.height)
		sprite.setRegion(graphicsComp.textureRegion)
		sprite.draw(batch)
	}
}