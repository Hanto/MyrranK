package com.myrran.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.Disposable
import com.myrran.infraestructure.AtlasConfiguration

class Atlas(

    atlastConfiguration: AtlasConfiguration

) : Disposable
{
    private val fonts: MutableMap<String, BitmapFont> = HashMap()

    init {

        atlastConfiguration.fonts.forEach{ addFont(it) }
    }

    private fun addFont(name: String) {

        val font = BitmapFont(Gdx.files.internal("fonts/$name.fnt"), false)
        fonts[name] = font
    }

    fun retrieveFont(name: String): BitmapFont =

        fonts[name]!!

    override fun dispose() {

        fonts.values.forEach{ it.dispose() }
    }
}
