package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Used by UI elements that slide between different values.
 */
class SliderComp: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<SliderComp>()
	}

	var onChange: (progress: Float) -> Unit = {}
	var progress = 0f
	var textures = mutableListOf<TextureRegion>()

	override fun reset() {
		onChange = {}
		progress = 0f
	}
}