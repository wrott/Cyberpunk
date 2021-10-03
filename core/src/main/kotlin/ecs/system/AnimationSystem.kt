package com.wrott.kitkatt.ecs.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.GdxRuntimeException
import com.wrott.kitkatt.Game
import com.wrott.kitkatt.ecs.component.AnimationComponent
import com.wrott.kitkatt.ecs.component.AnimationType
import com.wrott.kitkatt.ecs.component.RenderComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.log.logger
import java.util.*

class AnimationSystem(
    private val textureAtlas: TextureAtlas,
)
    : IteratingSystem(
    allOf(AnimationComponent::class, RenderComponent::class).get()),
    EntityListener {
    private val animationCache = EnumMap<AnimationType, Animation<TextureRegion>>(AnimationType::class.java)

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        engine.addEntityListener(family, 0, this)
    }

    override fun removedFromEngine(engine: Engine) {
        super.removedFromEngine(engine)
        engine.removeEntityListener(this)
    }

    override fun entityAdded(entity: Entity) {
        val animation = entity[AnimationComponent.mapper]
        requireNotNull(animation) { "AnimationComponent missing for entity '$entity'" }
        animation.gdxAnimation = getGdxAnimation(AnimationType.PLAYER_IDLE)
    }

    override fun entityRemoved(entity: Entity) = Unit

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val animation = entity[AnimationComponent.mapper]
        val render = entity[RenderComponent.mapper]
        requireNotNull(animation) { "AnimationComponent missing for entity '$entity'" }
        requireNotNull(render) { "RenderComponent missing for entity '$entity'" }

        if (animation.dirty) {
            animation.dirty = false
            animation.stateTime = 0f
            animation.gdxAnimation = getGdxAnimation(animation.type)
            render.offsetInPx.y = animation.type.offsetInPxY
        } else {
            animation.stateTime += deltaTime
        }

        val keyFrame = animation.gdxAnimation.getKeyFrame(animation.stateTime)

        render.sprite.run {
            val flipX = isFlipX
            val flipY = isFlipY
            setRegion(keyFrame)
            setSize(keyFrame.regionWidth * Game.UNIT_SCALE, keyFrame.regionHeight * Game.UNIT_SCALE)
            setOrigin(width * 0.5f, height * 0.5f)
            setFlip(flipX, flipY)
        }
    }

    private fun getGdxAnimation(type: AnimationType): Animation<TextureRegion> {
        return animationCache.getOrPut(type) {
            LOG.debug { "Loading animation of type '$type'" }
            val regions = textureAtlas.findRegions(type.atlasKey)
            if (regions.isEmpty) {
                LOG.error { "There are no regions for key '$type'" }
                Animation(0f, getErrorRegion())
            } else {
                Animation(1 / 8f, regions, type.playMode)
            }
        }
    }

    private fun getErrorRegion(): TextureAtlas.AtlasRegion {
        return textureAtlas.findRegion("run") ?: throw GdxRuntimeException("Missing error region")
    }

    companion object {
        private val LOG = logger<AnimationSystem>()
    }
}