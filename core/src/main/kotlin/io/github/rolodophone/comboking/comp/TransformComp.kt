package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Entities with a [TransformComp] have a size and position in the game world.
 */
class TransformComp: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<TransformComp>()
	}

	private val rect = Rectangle()

	var x: Float
		set(value) { rect.x = value }
		get() = rect.x

	var y: Float
		set(value) { rect.y = value }
		get() = rect.y

	var width: Float
		set(value) { rect.width = value }
		get() = rect.width

	var height: Float
		set(value) { rect.height = value }
		get() = rect.height

	/**
	 * The sprite will be drawn after all sprites with a lower [z]
	 */
	var z = 0f

	override fun reset() {
		rect.set(0f, 0f, 0f, 0f)
		z = 0f
	}

	fun setSizeFromTexture(texture: TextureRegion) {
		rect.setSize(texture.regionWidth.toFloat(), texture.regionHeight.toFloat())
	}

	fun contains(x: Float, y: Float) = rect.contains(x, y)
}