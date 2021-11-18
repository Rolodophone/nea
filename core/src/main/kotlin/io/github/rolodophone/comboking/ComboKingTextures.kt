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
	val prototype_ground = loadTextureRegion("prototype_ground")
	val prototype_platform = loadTextureRegion("prototype_platform")
	val prototype_stairs = loadTextureRegion("prototype_stairs")
	val main_menu_bg = loadTextureRegion("main_menu_bg")
	val btn_play = loadTextureRegion("btn_play")
	val btn_skills = loadTextureRegion("btn_skills")
	val btn_options = loadTextureRegion("btn_options")
	val btn_credits = loadTextureRegion("btn_credits")

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