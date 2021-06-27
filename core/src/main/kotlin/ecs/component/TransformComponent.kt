package com.wrott.kitkatt.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor

class TransformComponent : Component, Comparable<TransformComponent>, Pool.Poolable {

    companion object {
        val mapper = mapperFor<TransformComponent>()
    }
    val bounds = Rectangle()

    override fun compareTo(other: TransformComponent): Int {
        TODO("Not yet implemented")
    }

    override fun reset() {
        TODO("Not yet implemented")
    }
}

val Entity.transfCmp: TransformComponent
    get() = this[TransformComponent.mapper]
        ?: throw KotlinNullPointerException("Trying to access a transform component which is null")
