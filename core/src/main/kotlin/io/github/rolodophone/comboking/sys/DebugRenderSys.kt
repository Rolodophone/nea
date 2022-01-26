package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.comp.HitboxComp
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf
import ktx.graphics.use

/**
 * Draw entities' hitboxes (for debugging).
 */
class DebugRenderSys(
	private val viewport: Viewport
) : IteratingSystem(
	allOf(HitboxComp::class).get(), 35
) {
	private lateinit var shapeRenderer: ShapeRenderer

	override fun addedToEngine(engine: Engine) {
		shapeRenderer = ShapeRenderer()
		super.addedToEngine(engine)
	}

	override fun update(deltaTime: Float) {
		shapeRenderer.color = Color.BLACK

		shapeRenderer.use(ShapeRenderer.ShapeType.Line, viewport.camera.combined) {
			super.update(deltaTime)
		}
	}

	override fun processEntity(entity: Entity, deltaTime: Float) {
		val hitboxComp = entity.getNotNull(HitboxComp.mapper)
		shapeRenderer.rect(hitboxComp.x, hitboxComp.y, hitboxComp.width, hitboxComp.height)
	}
}