package io.github.rolodophone.comboking

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Disposable

@Suppress("unused","PropertyName")
class ComboKingTextures: Disposable {
	private val graphicsAtlas = TextureAtlas(Gdx.files.internal("graphics/graphics.atlas"))

//	val ball = graphicsAtlas.findRegion("ball")!!
//	val brick_black = graphicsAtlas.findRegion("brick_black")!!
//	val brick_dark_blue = graphicsAtlas.findRegion("brick_dark_blue")!!
//	val brick_dark_green = graphicsAtlas.findRegion("brick_dark_green")!!
//	val brick_light_blue = graphicsAtlas.findRegion("brick_light_blue")!!
//	val brick_light_green = graphicsAtlas.findRegion("brick_light_green")!!
//	val brick_orange = graphicsAtlas.findRegion("brick_orange")!!
//	val brick_pink = graphicsAtlas.findRegion("brick_pink")!!
//	val brick_purple = graphicsAtlas.findRegion("brick_purple")!!
//	val brick_red = graphicsAtlas.findRegion("brick_red")!!
//	val brick_white = graphicsAtlas.findRegion("brick_white")!!
//	val brick_yellow = graphicsAtlas.findRegion("brick_yellow")!!
//	val firing_line = graphicsAtlas.findRegion("firing_line")!!
//	val paddle_normal = graphicsAtlas.findRegion("paddle_normal")!!
//	val background = graphicsAtlas.findRegion("background")!!

	override fun dispose() {
		graphicsAtlas.dispose()
	}
}