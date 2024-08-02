package com.myrran.view.ui.misc

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.TextField

class TextFieldView<T>(

    text: T,
    font: BitmapFont,
    color: Color = Color.WHITE,
    val shadowTickness: Float = 0f,
    private val formater: (T) -> String =  { it.toString() }

): TextField(formater.invoke(text), TextFieldStyle(font, color, null, null, null)) {

    fun align(alignment: Int): TextFieldView<T> =
        super.setAlignment(alignment).let { this }
}
