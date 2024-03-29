package com.wrott.kitkatt.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ai.fsm.DefaultStateMachine
import com.badlogic.gdx.utils.Pool
import com.wrott.kitkatt.ai.DefaultEntityState
import com.wrott.kitkatt.ai.EntityState
import ktx.ashley.mapperFor

class AIComponent : Component, Pool.Poolable {
    var stateTime: Float = 0f
    var state: EntityState = DefaultEntityState
    val stateMachine = DefaultStateMachine<Entity, EntityState>()

    override fun reset() {
        stateTime = 0f
        state = DefaultEntityState
        stateMachine.globalState = null
        stateMachine.owner = null
    }

    companion object {
        val mapper = mapperFor<AIComponent>()
    }
}
