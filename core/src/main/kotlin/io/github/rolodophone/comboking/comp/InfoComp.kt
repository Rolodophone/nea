package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class InfoComp : Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<InfoComp>()
	}

	var tags = mutableListOf<String>()
	var name = "Unnamed"

	override fun reset() {
		tags = mutableListOf()
		name = "Unnamed"
	}
}