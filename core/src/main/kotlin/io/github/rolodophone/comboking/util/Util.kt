@file:Suppress("unused")

package io.github.rolodophone.comboking.util

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.get

private val tempVector = Vector2()

/**
 * Transforms the specified screen x coordinate to world coordinates.
 */
fun Viewport.unprojectX(x: Float): Float {
	tempVector.x = x
	return unproject(tempVector).x
}

/**
 * Transforms the specified y screen coordinate to world coordinates.
 */
fun Viewport.unprojectY(y: Float): Float {
	tempVector.y = y
	return unproject(tempVector).y
}

fun Viewport.halfWorldWidth() = worldWidth / 2f
fun Viewport.halfWorldHeight() = worldHeight / 2f

fun Rectangle.halfWidth() = width / 2
fun Rectangle.halfHeight() = height / 2

operator fun Vector2.plus(vec: Vector2): Vector2 = this.add(vec)

operator fun Vector2.times(scalar: Float): Vector2 = this.scl(scalar)
operator fun Float.times(vec: Vector2) = vec * this

/**
 * Get the specified [Component] from an [Entity], throwing an error if the [Component] isn't found.
 */
fun <T : Component> Entity.getNotNull(mapper: ComponentMapper<T>): T {
	val component = this[mapper]
	requireNotNull(component) { "Entity $this hasn't got the requested component" }
	return component
}