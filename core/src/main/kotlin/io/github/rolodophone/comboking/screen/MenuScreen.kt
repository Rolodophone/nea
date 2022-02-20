package io.github.rolodophone.comboking.screen

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.comp.*
import io.github.rolodophone.comboking.sys.TextRenderSys
import ktx.ashley.entity
import ktx.ashley.with

/**
 * The screen that's showing when the user is navigating the menus.
 */
class MenuScreen(game: ComboKing): ComboKingScreen(game) {
	private val menuContentEntities = mutableListOf<Entity>()

	private lateinit var textRenderSys: TextRenderSys

	private lateinit var creditsTextComp: TextComp
	private val credits = listOf(
		"Programming\nSergi\nLange-Soler",
		"Artwork\nSergi\nLange-Soler",
		"\"Spy Glass\"\nKevin MacLeod\nCC BY 4.0",
		"\"In a\nHeartbeat\"\nKevin MacLeod\nCC BY 4.0",
		"\"Plastic\nImpact 1\"\nSophia_C\nFreesound\nCC BY 3.0",
		"\"PUNCH-BOXING-\n04.wav\"\nnewagesoup\nFreesound\nCC0 1.0",
		"\"Punch2.wav\"\nMerrick079\nFreesound\nCC0 1.0",
		"\"Visitor\"\nBrian Kent",
		"\"Setback\"\nBrian Kent"
	)
	private var creditIndex = 0

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

		textRenderSys = TextRenderSys(batch, viewport, fonts)
		engine.addSystem(textRenderSys)

		showMainMenu()
	}

	private fun showMainMenu() {
		menuContentEntities.forEach { engine.removeEntity(it) }
		menuContentEntities.clear()

		menuContentEntities.add(engine.entity {
			with<InfoComp> {
				name = "HighscoreText"
			}
			with<TransformComp> {
				x = 66f
				y = 71f
			}
			with<TextComp> {
				colour = Color(95/255f, 205/255f, 228/255f, 1f)
				text = "HS: " + game.ckPrefs.getHighscore()
			}
		})
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

		val sliderTextures = listOf(game.comboKingTextures.slider0, game.comboKingTextures.slider1,
			game.comboKingTextures.slider2, game.comboKingTextures.slider3, game.comboKingTextures.slider4)

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
		menuContentEntities.add(engine.entity {
			with<InfoComp> {
				name = "SFXSlider"
			}
			with<TransformComp> {
				x = 66f
				y = 41f
				setSizeFromTexture(textures.slider0)
			}
			with<GraphicsComp> {
				textureRegion = sliderTextures[(game.ckPrefs.getSFXVolume() * 4).toInt()]
			}
			with<SliderComp> {
				textures.addAll(sliderTextures)
				onChange = { progress ->
					sounds.setVolume(progress)
					game.ckPrefs.putSFXVolume(progress)
				}
			}
		})
		menuContentEntities.add(engine.entity {
			with<InfoComp> {
				name = "MusicSlider"
			}
			with<TransformComp> {
				x = 66f
				y = 15f
				setSizeFromTexture(textures.slider0)
			}
			with<GraphicsComp> {
				textureRegion = sliderTextures[(game.ckPrefs.getMusicVolume() * 4).toInt()]
			}
			with<SliderComp> {
				textures.addAll(sliderTextures)
				onChange = { progress ->
					music.setVolume(progress)
					game.ckPrefs.putMusicVolume(progress)
				}
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
		menuContentEntities.add(engine.entity {
			with<InfoComp> {
				name = "PrevCreditButton"
			}
			with<TransformComp> {
				x = 118f
				y = 67f
				setSizeFromTexture(textures.btn_back)
			}
			with<GraphicsComp> {
				textureRegion = textures.btn_back
			}
			with<ButtonComp> {
				onPress = {
					creditIndex -= 1
					if (creditIndex == -1) creditIndex = credits.size - 1
					creditsTextComp.text = credits[creditIndex]
				}
			}
		})
		menuContentEntities.add(engine.entity {
			with<InfoComp> {
				name = "NextCreditButton"
			}
			with<TransformComp> {
				x = 134f
				y = 67f
				setSizeFromTexture(textures.btn_back)
			}
			with<GraphicsComp> {
				textureRegion = textures.btn_back
				flippedHorizontally = true
			}
			with<ButtonComp> {
				onPress = {
					creditIndex += 1
					if (creditIndex == credits.size) creditIndex = 0
					creditsTextComp.text = credits[creditIndex]
				}
			}
		})
		menuContentEntities.add(engine.entity {
			with<InfoComp> {
				name = "CreditsText"
			}
			with<TransformComp> {
				x = 65f
				y = 58f
			}
			creditsTextComp = with {
				colour = Color(95/255f, 205/255f, 228/255f, 1f)
				text = credits[creditIndex]
			}
		})
	}

	override fun hide() {
		engine.removeAllEntities()
		engine.removeSystem(textRenderSys)
	}
}