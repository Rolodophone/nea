package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Entities with a [TextComp] can specify some text that will be drawn on the screen.
 *
 * The coordinates where the text is drawn is specified by a [TransformComp]. The x and y coordinates define the
 * location of the bottom-left corner of the text. The width and height is fixed for any given text.
 */
class TextComp: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<TextComp>()
		const val FONT_HEIGHT = 5f
	}

	var text = ""
	var colour: Color = Color.WHITE

	override fun reset() {
		text = ""
		colour = Color.WHITE
	}
}