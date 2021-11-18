package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Entities with a [GraphicsComponent] and a [TransformComponent] are drawn on the screen.
 */
class GraphicsComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<GraphicsComponent>()
	}

	/**
	 * The entity's image.
	 */
	var textureRegion: TextureRegion? = null
	var visible = true

	override fun reset() {
		textureRegion = null
		visible = true
	}
}