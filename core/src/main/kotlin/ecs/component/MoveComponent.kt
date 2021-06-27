package com.wrott.kitkatt.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor

class MoveComponent : Component,  Pool.Poolable  {
    companion object {
        val mapper = mapperFor<MoveComponent>()
    }

    val speed = Vector2()

    override fun reset() {
        TODO("Not yet implemented")
    }
}
val Entity.moveCmp: MoveComponent
    get() = this[MoveComponent.mapper]
        ?: throw KotlinNullPointerException("Trying to access a move component which is null")
