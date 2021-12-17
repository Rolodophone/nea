package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.component.TextComponent
import io.github.rolodophone.comboking.component.TransformComponent
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf
import ktx.graphics.use

/**
 * Draws the text of entities with a [TextComponent].
 */
class TextRenderSystem(
	private val batch: Batch,
	private val viewport: Viewport,
): IteratingSystem(
	allOf(TransformComponent::class, TextComponent::class).get()
), Disposable {
	private lateinit var font: BitmapFont
	private lateinit var textProjectionMatrix: Matrix4

	override fun addedToEngine(engine: Engine) {
		// load font
		font = BitmapFont(Gdx.files.classpath("font/visitor.fnt"), Gdx.files.classpath("font/visitor.png"), false)

		// save the un-zoomed projection matrix for using when drawing text, so that text isn't moved when the
		// camera moves. Translate it to put the origin in the bottom left.
		textProjectionMatrix = viewport.camera.projection.cpy()
			.translate(-viewport.worldWidth / 2f, -viewport.worldHeight / 2f, 0f)

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
		val transformComp = entity.getNotNull(TransformComponent.mapper)
		val textComp = entity.getNotNull(TextComponent.mapper)

		font.color = Color.WHITE

		// add the height of the font because by default text is drawn with the origin at the top left. Adding the
		// on the height means the text is drawn with the origin in the bottom left, consistent with the rest of my game

		// convert text to uppercase because this font uses the lowercase letters as slight variants of the capital
		// letters. So I'm converting all text for the letters to look consistent

		font.draw(batch, textComp.text.uppercase(), transformComp.x, transformComp.y + TextComponent.FONT_HEIGHT)
	}

	override fun dispose() {
		font.dispose()
	}
}