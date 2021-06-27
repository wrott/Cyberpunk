package com.wrott.kitkatt.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.assets.AssetManager
import com.wrott.kitkatt.ecs.component.CollisionComponent
import com.wrott.kitkatt.ecs.component.TransformComponent
import ktx.ashley.allOf

class CollisionSystem(bucket: Entity, assets: AssetManager) :
    IteratingSystem(allOf(TransformComponent::class, CollisionComponent::class).get())  {
    override fun processEntity(entity: Entity?, deltaTime: Float) {
        TODO("Not yet implemented")
    }
}