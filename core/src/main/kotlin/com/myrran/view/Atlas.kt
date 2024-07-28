package com.myrran.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.Disposable

class Atlas : Disposable
{
    val fonts: MutableMap<String, BitmapFont> = HashMap()

    init {

        addFont("20")
        addFont("14")
    }

    private fun addFont(name: String) {

        val font = BitmapFont(Gdx.files.internal(String.format("fonts/%s.fnt", name)), false)
        fonts[name] = font
    }

    override fun dispose() {

        fonts.values.forEach{ it.dispose() }
    }
}
