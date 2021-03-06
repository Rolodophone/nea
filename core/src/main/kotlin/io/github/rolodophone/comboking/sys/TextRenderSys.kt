package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.asset.ComboKingFonts
import io.github.rolodophone.comboking.comp.TextComp
import io.github.rolodophone.comboking.comp.TransformComp
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf
import ktx.graphics.use

/**
 * Draws the text of entities with a [TextComp].
 */
class TextRenderSys(
	private val batch: Batch,
	private val viewport: Viewport,
	private val fonts: ComboKingFonts
): IteratingSystem(
	allOf(TransformComp::class, TextComp::class).get(), 35
) {
	private lateinit var textProjectionMatrix: Matrix4

	override fun addedToEngine(engine: Engine) {
		// save the translated projection matrix for using when drawing text, so that text isn't moved when the
		// camera moves. Translate it to put the origin in the bottom left.
		val zoom = (viewport.camera as OrthographicCamera).zoom
		textProjectionMatrix = viewport.camera.projection.cpy()
			.translate(-viewport.worldWidth / 2f * zoom, -viewport.worldHeight / 2f * zoom, 0f)

		super.addedToEngine(engine)
	}

	override fun update(deltaTime: Float) {
		// draw text using just projection matrix so that coordinates are interpreted as screen coordinates,
		// not game world coordinates.
		batch.use(textProjectionMatrix) {
			super.update(deltaTime)
		}
	}

	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transformComp = entity.getNotNull(TransformComp.mapper)
		val textComp = entity.getNotNull(TextComp.mapper)

		fonts.visitor.color = textComp.colour

		// add the height of the font because by default text is drawn with the origin at the top left. Adding the
		// on the height means the text is drawn with the origin in the bottom left, consistent with the rest of my game

		// convert text to uppercase because this font uses the lowercase letters as slight variants of the capital
		// letters. So I'm converting all text for the letters to look consistent

		fonts.visitor.draw(batch, textComp.text.uppercase(), transformComp.x, transformComp.y + TextComp.FONT_HEIGHT)
	}
}