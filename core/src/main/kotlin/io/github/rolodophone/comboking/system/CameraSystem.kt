package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.component.TransformComponent
import io.github.rolodophone.comboking.util.getNotNull


/**
 * Pans the camera to keep the player entity visible on the screen.
 */
class CameraSystem(
	private val viewport: Viewport,
	private val player: Entity
): EntitySystem() {

	companion object {
		const val PLAYER_TRACK_LEFT_MARGIN = 120f
		const val PLAYER_TRACK_RIGHT_MARGIN = 120f
	}

	override fun update(deltaTime: Float) {
		val playerTransformComp = player.getNotNull(TransformComponent.mapper)

		if (viewport.camera.position.x > playerTransformComp.x
				+ viewport.worldWidth / 2f - PLAYER_TRACK_LEFT_MARGIN) {
			// player is too close to left side of screen
			viewport.camera.position.x = (playerTransformComp.x
				+ viewport.worldWidth / 2f - PLAYER_TRACK_LEFT_MARGIN)
		}
		else if (viewport.camera.position.x < playerTransformComp.x + playerTransformComp.width
				- viewport.worldWidth / 2f + PLAYER_TRACK_RIGHT_MARGIN) {
			// player is too close to right side of screen
			viewport.camera.position.x = (playerTransformComp.x + playerTransformComp.width
				- viewport.worldWidth / 2f + PLAYER_TRACK_RIGHT_MARGIN)
		}
	}
}