package com.wrott.kitkatt.ecs.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.gdx.assets.AssetManager

class SpawnSystem(assets: AssetManager) : IntervalSystem(1f) {
    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        // spawn an initial drop when the system is added to the engine
        updateInterval()
    }

    override fun updateInterval() {
        TODO("Not yet implemented")
    }
}
