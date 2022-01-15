package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class HitboxComp : Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<HitboxComp>()
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

	val left: Float
		get() = x

	val bottom: Float
		get() = y

	val right: Float
		get() = x + width

	val top: Float
		get() = y + height

	override fun reset() {
		rect.set(0f, 0f, 0f, 0f)
	}

	fun overlaps(otherHitboxComp: HitboxComp) = rect.overlaps(otherHitboxComp.rect)
}