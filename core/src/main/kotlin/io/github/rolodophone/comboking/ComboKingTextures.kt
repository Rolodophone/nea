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

	val btn_credits = loadTextureRegion("btn_credits")
	val btn_options = loadTextureRegion("btn_options")
	val btn_play = loadTextureRegion("btn_play")
	val game_bg = loadTextureRegion("game_bg")
	val main_menu_bg = loadTextureRegion("main_menu_bg")
	val office_worker_enter_door = loadTextureRegion("office_worker_enter_door")
	val office_worker_exit_door_kb = loadTextureRegion("office_worker_exit_door_kb")
	val office_worker_exit_door = loadTextureRegion("office_worker_exit_door")
	val office_worker_hit_kb0 = loadTextureRegion("office_worker_hit_kb0")
	val office_worker_hit_kb1 = loadTextureRegion("office_worker_hit_kb1")
	val office_worker_idle_kb = loadTextureRegion("office_worker_idle_kb")
	val office_worker_idle = loadTextureRegion("office_worker_idle")
	val office_worker_push = loadTextureRegion("office_worker_push")
	val player_enter_door = loadTextureRegion("player_enter_door")
	val player_exit_door = loadTextureRegion("player_exit_door")
	val player_idle0 = loadTextureRegion("player_idle0")
	val player_idle1 = loadTextureRegion("player_idle1")
	val player_punch0 = loadTextureRegion("player_punch0")
	val player_punch1 = loadTextureRegion("player_punch1")
	val player_spinning_punch0 = loadTextureRegion("player_spinning_punch0")
	val player_spinning_punch1 = loadTextureRegion("player_spinning_punch1")
	val player_spinning_punch2 = loadTextureRegion("player_spinning_punch2")

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