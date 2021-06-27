package com.wrott.kitkatt.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.wrott.kitkatt.ecs.component.MoveComponent
import com.wrott.kitkatt.ecs.component.TransformComponent
import ktx.ashley.allOf

class MoveSystem  : IteratingSystem(allOf(TransformComponent::class, MoveComponent::class).get()) {
    override fun processEntity(entity: Entity?, deltaTime: Float) {
        TODO("Not yet implemented")
    }
}