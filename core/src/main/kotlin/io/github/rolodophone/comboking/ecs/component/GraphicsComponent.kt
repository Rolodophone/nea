package io.github.rolodophone.comboking.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class GraphicsComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<GraphicsComponent>()
	}

	val sprite = Sprite()
	var visible = true

	override fun reset() {
		//not resetting anything because I'm assuming I'll always set a new texture when I use this component
		//if you don't set the texture you'll get the wrong texture instead of no texture
		visible = true
	}
}