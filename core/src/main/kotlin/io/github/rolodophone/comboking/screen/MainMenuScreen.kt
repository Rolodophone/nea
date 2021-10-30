package io.github.rolodophone.comboking.screen

import com.badlogic.gdx.graphics.OrthographicCamera
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.component.ButtonComponent
import io.github.rolodophone.comboking.component.GraphicsComponent
import io.github.rolodophone.comboking.component.TransformComponent
import ktx.ashley.entity
import ktx.ashley.with

class MainMenuScreen(game: ComboKing): ComboKingScreen(game) {
	override fun show() {
		//set camera
		with(viewport.camera as OrthographicCamera) {
			zoom = 0.5f
			position.set(viewport.worldWidth / 4f, viewport.worldHeight / 4f, 0f)
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
				x = 70f
				y = 43f
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
				x = 111f
				y = 43f
				setSizeFromTexture(textures.btn_skills)
			}
			with<GraphicsComponent> {
				textureRegion = textures.btn_skills
			}
			with<ButtonComponent> {
				onPress = { }
			}
		}
		engine.entity {
			with<TransformComponent> {
				x = 70f
				y = 21f
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
				x = 111f
				y = 21f
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