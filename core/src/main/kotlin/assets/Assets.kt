package com.wrott.kitkatt.assets

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.maps.tiled.TiledMap
import ktx.assets.async.AssetStorage

enum class MusicAssets(val filePath: String)

operator fun AssetStorage.get(asset: MusicAssets) = get<Music>(asset.filePath)

enum class SoundAssets(val filePath: String)

operator fun AssetStorage.get(asset: SoundAssets) = get<Sound>(asset.filePath)

enum class TextureAtlasAssets(val filePath: String)

operator fun AssetStorage.get(asset: TextureAtlasAssets) = get<TextureAtlas>(asset.filePath)

enum class MapAssets(val filePath: String)

operator fun AssetStorage.get(asset: MapAssets) = get<TiledMap>(asset.filePath)

enum class ParticleAssets(val filePath: String)

operator fun AssetStorage.get(asset: ParticleAssets) = get<ParticleEffect>(asset.filePath)
