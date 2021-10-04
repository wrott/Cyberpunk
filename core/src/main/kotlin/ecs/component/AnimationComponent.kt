package com.wrott.kitkatt.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

enum class AnimationType(
    val atlasKey: String,
    val playMode: Animation.PlayMode = Animation.PlayMode.LOOP,
    val offsetInPxY: Float = 0f,
) {
    PLAYER_IDLE("idle", Animation.PlayMode.LOOP, 1f),
    PLAYER_JUMP("jump", Animation.PlayMode.NORMAL, 1f),
    PLAYER_RUN("run", Animation.PlayMode.LOOP, 1f),
    PLAYER_FALL("shoot", Animation.PlayMode.NORMAL, 1f),
}

class AnimationComponent : Component, Pool.Poolable {
    var stateTime = 0f
    var dirty = true
    var type = AnimationType.PLAYER_IDLE
        set(value) {
            dirty = true
            field = value
        }
    lateinit var gdxAnimation: Animation<TextureRegion>

    override fun reset() {
        stateTime = 0f
        dirty = true
        type = AnimationType.PLAYER_IDLE
    }

    companion object {
        val mapper = mapperFor<AnimationComponent>()
    }
}
