package com.wrott.kitkatt

import com.wrott.kitkatt.screen.GameScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.inject.Context
import ktx.log.logger

private val log = logger<Game>()

class Game : KtxGame<KtxScreen>() {
    private val context = Context()
    override fun create() {
       // setScreen<GameScreen>()
        super.create()

    }

    override fun dispose() {
        super.dispose()
    }
}