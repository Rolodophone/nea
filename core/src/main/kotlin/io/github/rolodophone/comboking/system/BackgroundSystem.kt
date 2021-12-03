package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.ComboKingTextures
import io.github.rolodophone.comboking.component.TransformComponent
import ktx.ashley.entity
import ktx.ashley.with

class BackgroundSystem(
	private val viewport: Viewport,
	private val textures: ComboKingTextures
): EntitySystem() {
	private var bgWallEntities: List<Entity>? = null

	override fun addedToEngine(engine: Engine) {
		// create background wall entities
		bgWallEntities = List(2) { i ->
			engine.entity {
				with<TransformComponent> {
					x = (textures.game_bg.regionWidth * i).toFloat()
					y = 0f
				}
			}
		}
	}
	override fun update(deltaTime: Float) {

	}
}