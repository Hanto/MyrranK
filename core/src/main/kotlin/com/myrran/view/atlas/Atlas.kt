package com.myrran.view.atlas

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.utils.ElapsedTime
import com.myrran.domain.utils.mapttl.MapTTL
import java.util.concurrent.TimeUnit.MINUTES

class Atlas(

    private val atlastConfiguration: AtlasConfiguration,
    private val fonts: MapTTL<String, BitmapFont> = MapTTL(defaultTTL = ElapsedTime.of(10, MINUTES))

) : Disposable
{
    fun retrieveFont(name: String): BitmapFont =

        fonts[name] ?: addFont(name)

    private fun addFont(name: String): BitmapFont {

        val font = BitmapFont(Gdx.files.internal("${atlastConfiguration.fontsDirectory}/$name.fnt"), false)
        fonts[name] = font
        return font
    }

    override fun dispose() {

        fonts.dispose()
    }
}
