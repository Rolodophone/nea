package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.component.PlayerComponent
import io.github.rolodophone.comboking.component.TransformComponent
import io.github.rolodophone.comboking.event.GameEvent
import io.github.rolodophone.comboking.event.GameEventManager
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf

/**
 * Handles player input and triggers the appropriate [GameEvent]s.
 */
class InputSystem(
	private val gameViewport: Viewport,
	private val gameEventManager: GameEventManager
): IteratingSystem(
	allOf(PlayerComponent::class, TransformComponent::class).get()
) {
	override fun processEntity(player: Entity, deltaTime: Float) {

	}
}