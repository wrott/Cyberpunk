package com.wrott.kitkatt.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.wrott.kitkatt.ecs.component.RenderComponent
import com.wrott.kitkatt.ecs.component.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get

class RenderSystem (
    private val batch: Batch,
    private val font: BitmapFont,
    private val camera: OrthographicCamera
) : SortedIteratingSystem(allOf(TransformComponent::class, RenderComponent::class).get(),
// compareBy is used to render entities by their z-index (=bucket is drawn in the background; raindrops are drawn in the foreground)
compareBy { entity: Entity -> entity[RenderComponent.mapper]?.z }) {
    override fun processEntity(entity: Entity?, deltaTime: Float) {
        TODO("Not yet implemented")
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
    }
}