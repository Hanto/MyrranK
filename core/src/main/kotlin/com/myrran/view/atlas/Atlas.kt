package com.myrran.view.atlas

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.utils.ElapsedTime.Companion.of
import com.myrran.domain.utils.MapOfMapTTL
import java.util.concurrent.TimeUnit.MINUTES

class Atlas(

    private val assetManager: AssetManager,
    private val textureRegions: MapOfMapTTL<String, String, TextureRegion> = MapOfMapTTL(defaultTTL = of(10, MINUTES))

) : Disposable
{
    companion object {
        const val ATLAS_FOLDER = "atlas/"
        const val FONTS_FOLDER = "fonts/"
    }

    fun load(configuration: AtlasConfiguration) {

        configuration.atlas.forEach {

            assetManager.load("$ATLAS_FOLDER$it", TextureAtlas::class.java)
        }

        configuration.fonts.forEach {

            assetManager.load("$FONTS_FOLDER$it", BitmapFont::class.java)
        }
    }

    fun unload(configuration: AtlasConfiguration) {

        configuration.atlas.forEach {

            assetManager.unload("$ATLAS_FOLDER$it")
            textureRegions.remove(it)
        }

        configuration.fonts.forEach {

            assetManager.unload("$FONTS_FOLDER$it")
        }
    }

    fun getFont(name: String): BitmapFont =

        assetManager.get("$FONTS_FOLDER$name", BitmapFont::class.java)

    fun getTextureRegion(atlasName: String, textureName: String): TextureRegion {

        return textureRegions[atlasName, textureName] ?: addTextureRegion(atlasName, textureName)
    }

    private fun addTextureRegion(atlasName: String, textureName: String): TextureRegion {

        val textureAtlas = assetManager.get("$ATLAS_FOLDER$atlasName", TextureAtlas::class.java)
        val texture = textureAtlas.findRegion(textureName)
        val textureRegion = TextureRegion(texture)
        textureRegions[atlasName, textureName] = textureRegion
        return textureRegion
    }

    fun finishLoading() {

        assetManager.finishLoading()
    }

    override fun dispose() {

        assetManager.dispose()
    }
}
