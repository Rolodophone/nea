package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Entities with a [TextComponent] can specify some text that will be drawn on the screen.
 *
 * The coordinates where the text is drawn is specified by a [TransformComponent]. The x and y coordinates define the
 * location of the bottom-left corner of the text. The width and height is fixed for any given text.
 */
class TextComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<TextComponent>()
		const val FONT_HEIGHT = 5f
	}

	var text = ""

	override fun reset() {
		text = ""
	}
}