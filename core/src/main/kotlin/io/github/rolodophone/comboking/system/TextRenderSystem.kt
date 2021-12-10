package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.component.TextComponent
import io.github.rolodophone.comboking.component.TransformComponent
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf
import ktx.graphics.use

class TextRenderSystem(
	private val batch: Batch,
	private val viewport: Viewport,
): IteratingSystem(
	allOf(TransformComponent::class, TextComponent::class).get()
) {
	private lateinit var font: BitmapFont

	override fun addedToEngine(engine: Engine) {
		font = BitmapFont()
		super.addedToEngine(engine)
	}

	override fun update(deltaTime: Float) {
		// draw text using just projection matrix so that coordinates are interpreted as screen coordinates,
		// not game world coordinates
		batch.use(viewport.camera.projection) {
			super.update(deltaTime)
		}
	}

	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transformComp = entity.getNotNull(TransformComponent.mapper)
		val textComp = entity.getNotNull(TextComponent.mapper)

		font.color = Color.WHITE
		font.draw(batch, textComp.text, 50f, 50f/*transformComp.x, transformComp.y*/)
	}
}