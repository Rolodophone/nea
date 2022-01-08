package io.github.rolodophone.comboking.comp

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * Entities with a [ButtonComp] can be clicked on by the user.
 */
class ButtonComp : Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<ButtonComp>()
	}

	var onPress: () -> Unit = {}

	override fun reset() {
		onPress = {}
	}
}