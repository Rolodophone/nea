package io.github.rolodophone.comboking

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Disposable

@Suppress("unused","PropertyName")
class ComboKingTextures: Disposable {
	private val graphicsAtlas = TextureAtlas(Gdx.files.internal("graphics/sprites.atlas"))

	val prototype_player = graphicsAtlas.findRegion("prototype_player")!!
	val prototype_ground = graphicsAtlas.findRegion("prototype_ground")!!
	val prototype_platform = graphicsAtlas.findRegion("prototype_platform")!!
	val prototype_stairs = graphicsAtlas.findRegion("prototype_stairs")!!

	override fun dispose() {
		graphicsAtlas.dispose()
	}
}