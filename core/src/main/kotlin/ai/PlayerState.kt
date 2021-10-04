package com.wrott.kitkatt.ai

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.ai.msg.Telegram
import com.wrott.kitkatt.Game
import com.wrott.kitkatt.ecs.component.AIComponent
import com.wrott.kitkatt.ecs.component.AnimationType
import com.wrott.kitkatt.ecs.component.JumpComponent
import com.wrott.kitkatt.ecs.component.PhysicComponent
import com.wrott.kitkatt.ecs.system.KeyExtraInfo
import ktx.ashley.configureEntity
import ktx.ashley.get
import ktx.ashley.with
import ktx.log.logger

sealed class PlayerState(animationType: AnimationType) : AnimatedState(animationType) {
    override fun enter(entity: Entity, ai: AIComponent) {
        super.enter(entity, ai)
        LOG.info { "Player '$entity' is entering state '${this::class.simpleName}'" }
    }

    object Idle : PlayerState(AnimationType.PLAYER_IDLE), EntityState {
        override fun update(entity: Entity, ai: AIComponent) {
            entity[PhysicComponent.mapper]?.let { physic ->
                when {
                 physic.body.linearVelocity.y < 0f -> ai.stateMachine.changeState(Fall)
                  physic.body.linearVelocity.x != 0f -> ai.stateMachine.changeState(Run)
                }
            }
        }
        override fun onMessage(entity: Entity, ai: AIComponent, telegram: Telegram): Boolean {
            val extraInfo = telegram.extraInfo
            if (extraInfo is KeyExtraInfo.Down) {
                when (extraInfo.keycode) {
//                    Input.Keys.A -> ai.stateMachine.changeState(Attack1)
                    Input.Keys.SPACE -> ai.stateMachine.changeState(Jump)
                }
                return true
            }
            return false
        }
    }
    object Jump : PlayerState(AnimationType.PLAYER_JUMP) {
        override fun enter(entity: Entity, ai: AIComponent) {
            super.enter(entity, ai)
            ((Gdx.app.applicationListener) as Game).ecsEngine.configureEntity(entity) {
                with<JumpComponent> {
                    maxHeight = 2f
                }
            }
        }

        override fun update(entity: Entity, ai: AIComponent) {
            val physic = entity[PhysicComponent.mapper]
            if (physic == null) {
                ai.stateMachine.changeState(Idle)
            } else if (entity[JumpComponent.mapper] == null && physic.body.linearVelocity.y <= 0f) {
                ai.stateMachine.changeState(Fall)
            }
        }
    }
    object Run : PlayerState(AnimationType.PLAYER_RUN) {
        override fun update(entity: Entity, ai: AIComponent) {
            entity[PhysicComponent.mapper]?.let { physic ->
                when {
                    physic.body.linearVelocity.y < 0f -> ai.stateMachine.changeState(Fall)
                    physic.body.linearVelocity.x == 0f -> ai.stateMachine.changeState(Idle)
                }
            }
        }


        override fun onMessage(entity: Entity, ai: AIComponent, telegram: Telegram): Boolean {
            val extraInfo = telegram.extraInfo
            if (extraInfo is KeyExtraInfo.Down) {
                when (extraInfo.keycode) {
                    Input.Keys.SPACE -> ai.stateMachine.changeState(Jump)
                }
                return true
            }
            return false
        }

    }
    object Fall : PlayerState(AnimationType.PLAYER_FALL) {
        override fun update(entity: Entity, ai: AIComponent) {
            val physic = entity[PhysicComponent.mapper]
            if (physic == null) {
                ai.stateMachine.changeState(Idle)
            } else if (physic.body.linearVelocity.y >= 0f) {
                ai.stateMachine.changeState(Idle)
            }
        }
    }


    companion object {
        private val LOG = logger<PlayerState>()
    }
}
