package com.wrott.kitkatt.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.wrott.kitkatt.Game
import com.wrott.kitkatt.ai.PlayerState
import com.wrott.kitkatt.ecs.component.*
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.body
import ktx.box2d.box

class MainScreen(
    private val engine: Engine,
    private val world: World,
) : KtxScreen {
    override fun show() {
        spawnPlayer()
        spawnTerrain()
    }

    private fun spawnPlayer() {
        engine.entity {
            with<PlayerComponent>()
            val transform = with<TransformComponent> {
                moveImmediately(8f, 3f)
                size.set(0.5f, 0.85f)
            }
            with<PhysicComponent> {
                body = world.body(BodyDef.BodyType.DynamicBody) {
                    position.set(transform.position.x, transform.position.y)
                    fixedRotation = true
                    allowSleep = false
                    box(transform.size.x, transform.size.y) {
                        friction = 0f
                        filter.categoryBits = Game.ENTITY_CATEGORY_CHARACTER
                        filter.maskBits = Game.ENTITY_CATEGORY_TERRAIN
                    }
                }
            }
            with<AnimationComponent>()
            with<RenderComponent> {
                offsetInPx.x = 3f
            }
            with<MoveComponent> {
                maxSpeed = 4f
            }
            with<AIComponent> { state = PlayerState.Idle }
        }
    }

    private fun spawnTerrain() {
        // floor
        engine.entity {
            with<TransformComponent>()
            with<PhysicComponent> {
                body = world.body(BodyDef.BodyType.StaticBody) {
                    position.set(8f, 0.5f)
                    fixedRotation = true
                    box(14f, 1f)
                }
            }
        }
        // left wall
        engine.entity {
            with<TransformComponent>()
            with<PhysicComponent> {
                body = world.body(BodyDef.BodyType.StaticBody) {
                    position.set(0.5f, 4.5f)
                    fixedRotation = true
                    box(1f, 7f)
                }
            }
        }
        // right wall
        engine.entity {
            with<TransformComponent>()
            with<PhysicComponent> {
                body = world.body(BodyDef.BodyType.StaticBody) {
                    position.set(15.5f, 4.5f)
                    fixedRotation = true
                    box(1f, 7f)
                }
            }
        }
        // platform
        engine.entity {
            with<TransformComponent>()
            with<PhysicComponent> {
                body = world.body(BodyDef.BodyType.StaticBody) {
                    position.set(4.65f, 2.8f)
                    fixedRotation = true
                    box(3.4f, 0.3f)
                }
            }
        }
        engine.entity {
            with<TransformComponent>()
            with<PhysicComponent> {
                body = world.body(BodyDef.BodyType.StaticBody) {
                    position.set(4.6f, 2.35f)
                    fixedRotation = true
                    box(1.4f, 0.5f)
                }
            }
        }
    }

    override fun hide() {
        engine.removeAllEntities()
    }

    override fun render(delta: Float) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            hide()
            show()
        }
        engine.update(delta)
    }
}

