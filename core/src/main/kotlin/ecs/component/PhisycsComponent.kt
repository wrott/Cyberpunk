package com.wrott.kitkatt.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import ktx.math.vec2

class PhysicComponent(val impulse: Vector2 = vec2()) : Component, Pool.Poolable {
    companion object {
        val mapper = mapperFor<PhysicComponent>()
    }

    lateinit var body: Body

    override fun reset() {
        body.world.destroyBody(body)
        body.userData = null
        impulse.set(0f, 0f)
    }
}

