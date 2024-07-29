package com.myrran.view.atlas

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.utils.ElapsedTime
import com.myrran.domain.utils.mapttl.MapTTL
import java.util.concurrent.TimeUnit.MINUTES

class Atlas(

    private val assetManager: AssetManager,
    private val textureRegions: MapTTL<String, TextureRegion> = MapTTL(defaultTTL = ElapsedTime.of(10, MINUTES))

) : Disposable
{
    fun load(configuration: AtlasConfiguration) {

        configuration.atlas.forEach {

            assetManager.load("atlas/$it", TextureAtlas::class.java)
        }

        configuration.fonts.forEach {

            assetManager.load("fonts/$it", BitmapFont::class.java)
        }
    }

    fun getFont(name: String): BitmapFont =

        assetManager.get("fonts/$name.fnt", BitmapFont::class.java)

    fun getTextureRegion(atlas: Atlas, texture: String): TextureRegion {

        TODO()
    }

    fun addTextureRegion(atlasName: String, textureName: String): TextureRegion {

        val textureAtlas = assetManager.get(atlasName, TextureAtlas::class.java)
        val texture = textureAtlas.findRegion(textureName)
        val textureRegion = TextureRegion(texture)
        return textureRegion
    }

    fun finishLoading() {

        assetManager.finishLoading()
    }

    override fun dispose() {

        assetManager.dispose()
    }
}
