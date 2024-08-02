package com.myrran.view.ui.misc

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label

class TextView<T>(

    text: T,
    font: BitmapFont,
    color: Color = Color.WHITE,
    val shadowTickness: Float = 0f,
    private val formater: (T) -> String =  { it.toString() }

): Label(formater.invoke(text), LabelStyle(font, color))
{
    fun setText(text: T) {

        val newText = formater.invoke(text)
        super.setText(newText)
    }

    fun setText(text: T, formater: (T) -> String) {

        val newText = formater.invoke(text)
        super.setText(newText)
    }

    fun align(alignment: Int): TextView<T> =
        super.setAlignment(alignment).let { this }

    override fun draw(batch: Batch, parentAlpha: Float) {

        if (shadowTickness != 0f) {

            color = Color.BLACK
            moveBy(shadowTickness, -shadowTickness)
            super.draw(batch, parentAlpha)
        }

        color = Color.WHITE
        moveBy(-shadowTickness, shadowTickness)
        super.draw(batch, parentAlpha)
    }
}
