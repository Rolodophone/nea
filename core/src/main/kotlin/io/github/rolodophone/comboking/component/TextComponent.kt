package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class TextComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<TextComponent>()
	}

	var text = ""

	override fun reset() {
		text = ""
	}
}