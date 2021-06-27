package com.wrott.kitkatt.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import ktx.app.KtxScreen

class GameScreen (private val batch: Batch,
                   private val font: BitmapFont,
                   private val assets: AssetManager,
                   private val camera: OrthographicCamera,
                   private val engine: PooledEngine
 ): KtxScreen {
    override fun show() {
        TODO("Not yet implemented")
    }

    override fun render(delta: Float) {
        TODO("Not yet implemented")
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }
}


