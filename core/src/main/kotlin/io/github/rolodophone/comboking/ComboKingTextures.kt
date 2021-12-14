package io.github.rolodophone.comboking

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Disposable

/**
 * Stores the game's textures.
 */
@Suppress("unused","PropertyName")
class ComboKingTextures: Disposable {
	private val graphicsAtlas = TextureAtlas(Gdx.files.internal("graphics/sprites.atlas"))

	val prototype_player = loadTextureRegion("prototype_player")
	val main_menu_bg = loadTextureRegion("main_menu_bg")
	val btn_play = loadTextureRegion("btn_play")
	val btn_options = loadTextureRegion("btn_options")
	val btn_credits = loadTextureRegion("btn_credits")
	val game_bg = loadTextureRegion("game_bg")

	override fun dispose() {
		graphicsAtlas.dispose()
	}
	
	private fun loadTextureRegion(name: String): TextureAtlas.AtlasRegion {
		val region = graphicsAtlas.findRegion(name)
		
		if (region == null) {
			throw AssertionError("Region $name not found in texture atlas.")
		}
		
		else return region
	}
}