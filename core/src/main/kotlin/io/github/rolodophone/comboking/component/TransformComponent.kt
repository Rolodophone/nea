package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class TransformComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<TransformComponent>()
	}

	inner class CompareByZ: Comparable<TransformComponent> {
		override fun compareTo(other: TransformComponent): Int {
			return z - other.z
		}
	}

	val rect = Rectangle()

	/**
	 * The sprite will be drawn after all sprites with a lower [z]
	 */
	var z = 0

	override fun reset() {
		//Note the rect is not reset. If you don't define the rect when using this component behaviour is undefined
		z = 0
	}

	fun setSizeFromTexture(texture: TextureRegion) {
		rect.setSize(texture.regionWidth.toFloat(), texture.regionHeight.toFloat())
	}
}