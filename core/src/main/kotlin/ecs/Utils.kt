package com.wrott.kitkatt.ecs

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.Viewport
import com.wrott.kitkatt.ecs.system.*
import ktx.assets.loadOnDemand

class Utils

fun createDefaultEngine(
    batch: Batch,
    viewport: Viewport,
    world: World,
    assets: AssetManager,
) = PooledEngine().apply {
    addSystem(AISystem())
    addSystem(MoveSystem())
    addSystem(JumpSystem(world))
    addSystem(PhysicSystem(world))
    addSystem(AnimationSystem(assets.loadOnDemand<TextureAtlas>("player.atlas").asset))
    addSystem(
        RenderSystem(
            assets.loadOnDemand<TextureAtlas>("assets.atlas").asset.findRegion("background"),
            batch,
            viewport
        )
    )
}
