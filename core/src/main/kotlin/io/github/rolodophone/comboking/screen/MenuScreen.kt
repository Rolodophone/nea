package io.github.rolodophone.comboking.screen

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.OrthographicCamera
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.comp.ButtonComp
import io.github.rolodophone.comboking.comp.GraphicsComp
import io.github.rolodophone.comboking.comp.InfoComp
import io.github.rolodophone.comboking.comp.TransformComp
import ktx.ashley.entity
import ktx.ashley.with

/**
 * The screen that's showing when the user is navigating the menus.
 */
class MenuScreen(game: ComboKing): ComboKingScreen(game) {
	private val menuContentEntities = mutableListOf<Entity>()

	override fun show() {
		//set camera
		with(viewport.camera as OrthographicCamera) {
			zoom = 1/2f
			position.set(viewport.worldWidth * 1/4f, viewport.worldHeight * 1/4f, 0f)
			update()
		}

		// background
		engine.entity {
			with<InfoComp> {
				name = "MainMenuBackground"
			}
			with<TransformComp> {
				x = 0f
				y = 0f
				width = textures.main_menu_bg.regionWidth * 2f
				height = textures.main_menu_bg.regionHeight * 2f
			}
			with<GraphicsComp> {
				textureRegion = textures.main_menu_bg
			}
		}

		showMainMenu()
	}

	private fun showMainMenu() {
		menuContentEntities.forEach { engine.removeEntity(it) }
		menuContentEntities.clear()

		menuContentEntities.add(engine.entity {
			with<InfoComp> {
				name = "PlayButton"
			}
			with<TransformComp> {
				x = 89f
				y = 47f
				setSizeFromTexture(textures.btn_play)
			}
			with<GraphicsComp> {
				textureRegion = textures.btn_play
			}
			with<ButtonComp> {
				onPress = { game.setScreen<GameScreen>() }
			}
		})
		menuContentEntities.add(engine.entity {
			with<InfoComp> {
				name = "OptionsButton"
			}
			with<TransformComp> {
				x = 77f
				y = 29f
				setSizeFromTexture(textures.btn_options)
			}
			with<GraphicsComp> {
				textureRegion = textures.btn_options
			}
			with<ButtonComp> {
				onPress = { showOptionsMenu() }
			}
		})
		menuContentEntities.add(engine.entity {
			with<InfoComp> {
				name = "CreditsButton"
			}
			with<TransformComp> {
				x = 77f
				y = 11f
				setSizeFromTexture(textures.btn_credits)
			}
			with<GraphicsComp> {
				textureRegion = textures.btn_credits
			}
			with<ButtonComp> {
				onPress = { showCreditsMenu() }
			}
		})
	}

	private fun showOptionsMenu() {
		menuContentEntities.forEach { engine.removeEntity(it) }
		menuContentEntities.clear()

		menuContentEntities.add(engine.entity {
			with<InfoComp> {
				name = "OptionsContent"
			}
			with<TransformComp> {
				x = 62f
				y = 8f
				setSizeFromTexture(textures.content_options)
			}
			with<GraphicsComp> {
				textureRegion = textures.content_options
			}
		})
		menuContentEntities.add(engine.entity {
			with<InfoComp> {
				name = "BackButton"
			}
			with<TransformComp> {
				x = 65f
				y = 67f
				setSizeFromTexture(textures.btn_back)
			}
			with<GraphicsComp> {
				textureRegion = textures.btn_back
			}
			with<ButtonComp> {
				onPress = { showMainMenu() }
			}
		})
	}

	private fun showCreditsMenu() {
		menuContentEntities.forEach { engine.removeEntity(it) }
		menuContentEntities.clear()

		menuContentEntities.add(engine.entity {
			with<InfoComp> {
				name = "BackButton"
			}
			with<TransformComp> {
				x = 65f
				y = 67f
				setSizeFromTexture(textures.btn_back)
			}
			with<GraphicsComp> {
				textureRegion = textures.btn_back
			}
			with<ButtonComp> {
				onPress = { showMainMenu() }
			}
		})
	}

	override fun hide() {
		engine.removeAllEntities()
	}
}