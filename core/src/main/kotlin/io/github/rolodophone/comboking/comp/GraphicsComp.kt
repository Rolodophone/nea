package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Entities with a [GraphicsComp] and a [TransformComp] are drawn on the screen.
 */
class GraphicsComp: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<GraphicsComp>()
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