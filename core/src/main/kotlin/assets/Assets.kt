package com.wrott.kitkatt.assets

import com.badlogic.gdx.assets.loaders.ParticleEffectLoader
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.maps.tiled.TiledMap
import ktx.assets.async.AssetStorage

enum class MusicAssets(val filePath: String, val volumeScale: Float = 0.15f) {

}
fun AssetStorage.loadAsync(asset: MusicAssets) = loadAsync<Music>(asset.filePath)
operator fun AssetStorage.get(asset: MusicAssets) = get<Music>(asset.filePath)

enum class SoundAssets(val filePath: String, val volumeScale: Float = 1f) {}

fun AssetStorage.loadAsync(asset: SoundAssets) = loadAsync<Sound>(asset.filePath)
operator fun AssetStorage.get(asset: SoundAssets) = get<Sound>(asset.filePath)

enum class TextureAtlasAssets(val filePath: String) {}

fun AssetStorage.loadAsync(asset: TextureAtlasAssets) = loadAsync<TextureAtlas>(asset.filePath)
operator fun AssetStorage.get(asset: TextureAtlasAssets) = get<TextureAtlas>(asset.filePath)

enum class MapAssets(val filePath: String) {}

fun AssetStorage.loadAsync(asset: MapAssets) = loadAsync<TiledMap>(asset.filePath)
operator fun AssetStorage.get(asset: MapAssets) = get<TiledMap>(asset.filePath)

enum class ParticleAssets(val filePath: String, val scale: Float = 1f,
                          val sound: SoundAssets) {}
fun AssetStorage.loadAsync(asset: ParticleAssets, params: ParticleEffectLoader.ParticleEffectParameter) =
    loadAsync<ParticleEffect>(asset.filePath, params)

operator fun AssetStorage.get(asset: ParticleAssets) = get<ParticleEffect>(asset.filePath)
