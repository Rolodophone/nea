package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.physics.box2d.World

private const val TIME_STEP = 1/45f

/**
 * How accurate the velocity calculations are. LibGDX recommends a value of 6.
 */
private const val VELOCITY_ITERATIONS = 6

/**
 * How accurate the position calculations are. LibGDX recommends a value of 2.
 */
private const val POSITION_ITERATIONS = 2

/**
 * Controls smooth movement of entities.
 */
class PhysicsSystem(private val world: World): EntitySystem() {
	private var accumulator = 0f

	override fun update(deltaTime: Float) {
		accumulator += deltaTime

		while (accumulator >= 0) {
			world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
			accumulator -= TIME_STEP
		}
	}
}