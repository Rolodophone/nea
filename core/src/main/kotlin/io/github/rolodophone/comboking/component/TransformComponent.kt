package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class TransformComponent: Component, Pool.Poolable, Comparable<TransformComponent> {
	companion object {
		val mapper = mapperFor<TransformComponent>()
	}

	/**
	 * The current position and size of the entity.
	 *
	 * If the entity contains a [MoveComponent], the sprite position will be interpolated. Therefore it will actually be drawn somewhere
	 * between the current position and the previous position.
	 */
	val rect = Rectangle()

	/**
	 * The sprite will be drawn after all sprites with a lower [z]
	 */
	var z = 0

	/**
	 * The rotation of the sprite.
	 *
	 * Measured clockwise from the upwards direction, in degrees.
	 */
	var rotation = 0f

	override fun reset() {
		//Note the rect is not reset. If you don't define the rect when using this component behaviour is undefined
		z = 0
		rotation = 0f
	}

	override fun compareTo(other: TransformComponent): Int {
		return z - other.z
	}

	fun setSizeFromTexture(texture: TextureRegion) {
		rect.setSize(texture.regionWidth.toFloat(), texture.regionHeight.toFloat())
	}
}