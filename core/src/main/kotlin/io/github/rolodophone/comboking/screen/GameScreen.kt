package io.github.rolodophone.comboking.screen

import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.component.GraphicsComponent
import io.github.rolodophone.comboking.component.PlayerComponent
import io.github.rolodophone.comboking.component.TransformComponent
import ktx.ashley.entity
import ktx.ashley.with

class GameScreen(game: ComboKing): ComboKingScreen(game) {

	@Suppress("UNUSED_VARIABLE")
	override fun show() {

		// add entities

		val player = engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.prototype_player)
				rect.setPosition(120f, 5f)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.prototype_player)
			}
			with<PlayerComponent> {}
		}

		engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.prototype_ground)
				rect.setPosition(0f, 0f)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.prototype_ground)
			}
		}

		engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.prototype_platform)
				rect.setPosition(0f, 90f)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.prototype_platform)
			}
		}

		engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.prototype_stairs)
				rect.setPosition(190f, 5f)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.prototype_stairs)
			}
		}
	}
}