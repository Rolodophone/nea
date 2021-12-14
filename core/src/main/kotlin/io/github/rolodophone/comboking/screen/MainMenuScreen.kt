package io.github.rolodophone.comboking.screen

import com.badlogic.gdx.graphics.OrthographicCamera
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.component.ButtonComponent
import io.github.rolodophone.comboking.component.GraphicsComponent
import io.github.rolodophone.comboking.component.TransformComponent
import ktx.ashley.entity
import ktx.ashley.with

/**
 * The screen that's showing when the user is in the main menu.
 */
class MainMenuScreen(game: ComboKing): ComboKingScreen(game) {
	override fun show() {
		//set camera
		with(viewport.camera as OrthographicCamera) {
			zoom = 7/20f
			position.set(viewport.worldWidth * 7/40f, viewport.worldHeight * 7/40f, 0f)
			update()
		}

		// background
		engine.entity {
			with<TransformComponent> {
				x = 0f
				y = 0f
				setSizeFromTexture(textures.main_menu_bg)
			}
			with<GraphicsComponent> {
				textureRegion = textures.main_menu_bg
			}
		}

		//buttons
		engine.entity {
			with<TransformComponent> {
				x = 49f
				y = 37f
				setSizeFromTexture(textures.btn_play)
			}
			with<GraphicsComponent> {
				textureRegion = textures.btn_play
			}
			with<ButtonComponent> {
				onPress = { game.setScreen<GameScreen>() }
			}
		}
		engine.entity {
			with<TransformComponent> {
				x = 49f
				y = 26f
				setSizeFromTexture(textures.btn_options)
			}
			with<GraphicsComponent> {
				textureRegion = textures.btn_options
			}
			with<ButtonComponent> {
				onPress = { }
			}
		}
		engine.entity {
			with<TransformComponent> {
				x = 49f
				y = 15f
				setSizeFromTexture(textures.btn_credits)
			}
			with<GraphicsComponent> {
				textureRegion = textures.btn_credits
			}
			with<ButtonComponent> {
				onPress = { }
			}
		}
	}

	override fun hide() {
		engine.removeAllEntities()
	}
}