package com.wrott.kitkatt.ai

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import com.wrott.kitkatt.ecs.component.AIComponent
import com.wrott.kitkatt.ecs.component.AnimationComponent
import com.wrott.kitkatt.ecs.component.AnimationType
import ktx.ashley.get

sealed interface EntityState : State<Entity> {
    private fun entityAi(entity: Entity): AIComponent {
        val ai = entity[AIComponent.mapper]
        requireNotNull(ai) { "AIComponent missing for entity '$entity'" }
        return ai
    }

    override fun enter(entity: Entity) {
        val ai = entityAi(entity)
        ai.stateTime = 0f
        ai.state = this
        enter(entity, ai)
    }

    override fun update(entity: Entity) {
        update(entity, entityAi(entity))
    }

    override fun exit(entity: Entity) {
        exit(entity, entityAi(entity))
    }

    override fun onMessage(entity: Entity, telegram: Telegram): Boolean {
        return onMessage(entity, entityAi(entity), telegram)
    }

    fun enter(entity: Entity, ai: AIComponent) = Unit
    fun update(entity: Entity, ai: AIComponent) = Unit
    fun exit(entity: Entity, ai: AIComponent) = Unit
    fun onMessage(entity: Entity, ai: AIComponent, telegram: Telegram): Boolean = false
}

object DefaultEntityState : EntityState

abstract class AnimatedState(
    private val animationType: AnimationType,
) : EntityState {
    private fun entityAnimation(entity: Entity): AnimationComponent {
        val animation = entity[AnimationComponent.mapper]
        requireNotNull(animation) { "AnimationComponent missing for entity '$entity'" }
        return animation
    }

    override fun enter(entity: Entity, ai: AIComponent) {
        val animation = entityAnimation(entity)
        animation.type = animationType
        animation.stateTime = 0f
    }
}