package io.github.rolodophone.comboking.asset

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.Disposable

class ComboKingFonts: Disposable {
	val visitor = BitmapFont(Gdx.files.internal("font/visitor.fnt"), Gdx.files.internal("font/visitor.png"), false)

	override fun dispose() {
		visitor.dispose()
	}
}