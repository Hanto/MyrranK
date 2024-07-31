package com.myrran.view.atlas

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.utils.ElapsedTime.Companion.of
import com.myrran.domain.utils.mapttl.MapMapTTL
import java.util.concurrent.TimeUnit.MINUTES

class Atlas(

    private val assetManager: AssetManager,
    private val textureRegions: MapMapTTL<String, String, TextureRegion> = MapMapTTL(defaultTTL = of(10, MINUTES)),
    private val ninePatches: MapMapTTL<String, String, NinePatch> = MapMapTTL(defaultTTL = of(10, MINUTES)),

) : Disposable
{
    companion object {
        const val ATLAS_FOLDER = "atlas/"
        const val FONTS_FOLDER = "fonts/"
    }

    fun load(configuration: AssetsCollection) {

        configuration.atlas.forEach {

            assetManager.load("$ATLAS_FOLDER$it", TextureAtlas::class.java)
        }

        configuration.fonts.forEach {

            assetManager.load("$FONTS_FOLDER$it", BitmapFont::class.java)
        }
    }

    fun unload(configuration: AssetsCollection) {

        configuration.atlas.forEach {

            assetManager.unload("$ATLAS_FOLDER$it")
            textureRegions.remove(it)
        }

        configuration.fonts.forEach {

            assetManager.unload("$FONTS_FOLDER$it")
        }
    }

    // MANAGED BY ASSETMANAGER
    //--------------------------------------------------------------------------------------------------------

    fun getFont(fontName: String): BitmapFont =

        assetManager.get("$FONTS_FOLDER$fontName", BitmapFont::class.java)

    private fun getAtlas(atlasName: String): TextureAtlas =

        assetManager.get("$ATLAS_FOLDER$atlasName", TextureAtlas::class.java)

    // CACHED WITH TTL
    //--------------------------------------------------------------------------------------------------------

    fun getTextureRegion(atlasName: String, textureName: String): TextureRegion =

        textureRegions[atlasName, textureName] ?: addAndReturnTextureRegion(atlasName, textureName)

    private fun addAndReturnTextureRegion(atlasName: String, textureName: String): TextureRegion =

        getAtlas(atlasName)
            .let { it.findRegion(textureName) }
            .let { TextureRegion(it) }
            .also { textureRegions[atlasName, textureName] = it }

    fun getNinePatch(atlasName: String, ninePatchName: String): NinePatch =

        ninePatches[atlasName, ninePatchName] ?: addAndReturnNinePatch(atlasName, ninePatchName)

    fun getNinePatchDrawable(atlasName: String, ninePatchName: String, color: Color, alpha: Float): NinePatchDrawable =

        getNinePatch(atlasName, ninePatchName)
            .let { NinePatchDrawable(it).tint(Color(color.r, color.g, color.b, alpha)) }

    private fun addAndReturnNinePatch(atlasName: String, ninePatchName: String): NinePatch =

        getAtlas(atlasName)
            .let { it.createPatch(ninePatchName) }
            .also { ninePatches[atlasName, ninePatchName] = it }


    // MISC:
    //--------------------------------------------------------------------------------------------------------

    fun finishLoading() =

        assetManager.finishLoading()

    override fun dispose() =

        assetManager.dispose()
}
