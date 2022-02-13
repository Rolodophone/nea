package io.github.rolodophone.comboking.screen

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
	init {
		subScreen = MainState(game)
	}

	override fun show() {
		//set camera
		with(viewport.camera as OrthographicCamera) {
			zoom = 1/2f
			position.set(viewport.worldWidth * 1/4f, viewport.worldHeight * 1/4f, 0f)
			update()
		}

		super.show() //show subScreen
	}
}

/**
 * The main menu state.
 */
private class MainState(game: ComboKing): ComboKingScreen(game) {
	override fun show() {
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

		//buttons
		engine.entity {
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
		}
		engine.entity {
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
				onPress = { }
			}
		}
		engine.entity {
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
				onPress = { }
			}
		}
	}

	override fun hide() {
		engine.removeAllEntities()
	}
}