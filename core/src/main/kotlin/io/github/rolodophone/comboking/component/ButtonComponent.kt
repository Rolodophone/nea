package io.github.rolodophone.comboking.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class ButtonComponent : Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<ButtonComponent>()
	}

	var onPress: () -> Unit = {}

	override fun reset() {
		onPress = {}
	}
}