package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class GraphicsComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<GraphicsComponent>()
	}

	var textureRegion: TextureRegion? = null
	var visible = true

	override fun reset() {
		textureRegion = null
		visible = true
	}
}