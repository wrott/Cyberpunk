package com.wrott.kitkatt.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.wrott.kitkatt.Game
import com.wrott.kitkatt.ecs.component.JumpComponent
import ktx.ashley.allOf
import ktx.ashley.get
import kotlin.math.sqrt

class JumpSystem(
    private val world: World
) : IteratingSystem(allOf(JumpComponent::class).get()) {
    private val gravityPerStep = Vector2()

    private fun getJumpVelocity(desiredHeight: Float): Float {
        if (desiredHeight <= 0) {
            return 0f
        }

        gravityPerStep.set(world.gravity).scl(Game.PHYSIC_TIME_STEP).scl(Game.PHYSIC_TIME_STEP)

        val a = 0.5f / gravityPerStep.y
        val b = 0.5f

        val quadraticSolution1 = (-b - sqrt(b * b - 4 * a * desiredHeight)) / (2 * a)
        val quadraticSolution2 = (-b + sqrt(b * b - 4 * a * desiredHeight)) / (2 * a)

        return if (quadraticSolution1 < 0) {
            quadraticSolution2 / Game.PHYSIC_TIME_STEP
        } else {
            quadraticSolution1 / Game.PHYSIC_TIME_STEP
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val jump = entity[JumpComponent.mapper]
        requireNotNull(jump) { "JumpComponent missing for entity '$entity'" }

        jump.jumpVelocity = getJumpVelocity(jump.maxHeight)
    }
}
