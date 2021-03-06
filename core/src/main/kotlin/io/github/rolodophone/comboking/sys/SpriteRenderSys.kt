package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.ckLogger
import io.github.rolodophone.comboking.comp.GraphicsComp
import io.github.rolodophone.comboking.comp.TransformComp
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf
import ktx.graphics.use

private val log = ckLogger<SpriteRenderSys>()

/**
 * Renders the entities on the screen
 */
class SpriteRenderSys(
	private val batch: Batch,
	private val viewport: Viewport
): SortedIteratingSystem(
	allOf(TransformComp::class, GraphicsComp::class).get(),
	EntityRenderingComparator(),
	30
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
		sprite.setFlip(graphicsComp.flippedHorizontally, false)
		sprite.draw(batch)
	}
}

class EntityRenderingComparator: Comparator<Entity> {
	override fun compare(p0: Entity, p1: Entity): Int {
		val p0TransformComp = p0.getNotNull(TransformComp.mapper)
		val p1TransformComp = p1.getNotNull(TransformComp.mapper)

		return when {
			p0TransformComp.z < p1TransformComp.z -> -1
			p0TransformComp.z > p1TransformComp.z -> 1
			p0TransformComp.y < p1TransformComp.y -> -1
			p0TransformComp.y > p1TransformComp.y -> 1
			else -> 0
		}
	}
}