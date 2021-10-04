package com.wrott.kitkatt.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.viewport.Viewport
import com.wrott.kitkatt.Game
import com.wrott.kitkatt.ecs.component.RenderComponent
import com.wrott.kitkatt.ecs.component.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.logger

class RenderSystem(
    private val backGround: TextureRegion,
    private val batch: Batch,
    private val viewport: Viewport,
    private val camera: Camera = viewport.camera,
) : SortedIteratingSystem(
    allOf(TransformComponent::class, RenderComponent::class).get(),
    compareBy { it[TransformComponent.mapper] },
) {
    override fun update(deltaTime: Float) {
        forceSort()

        viewport.apply()
        batch.use(camera) {
            batch.draw(backGround, 0f, 0f, viewport.worldWidth, viewport.worldHeight)
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        val render = entity[RenderComponent.mapper]
        requireNotNull(transform) { "TransformComponent missing for entity '$entity'" }
        requireNotNull(render) { "RenderComponent missing for entity '$entity'" }

        if (render.sprite.texture == null) {
            LOG.error { "Texture not correctly initialized for entity '$entity'" }
            return
        }

        render.sprite.run {
            setBounds(
                transform.interpolatedPosition.x - transform.size.x * 0.5f - render.offsetInPx.x * Game.UNIT_SCALE + (1.0f - width) * width * 0.5f,
                transform.interpolatedPosition.y - transform.size.y * 0.5f - render.offsetInPx.y * Game.UNIT_SCALE * transform.size.y,
                width,
                height,
            )
            draw(batch)
        }
    }

    companion object {
        val LOG = logger<RenderSystem>()
    }
}