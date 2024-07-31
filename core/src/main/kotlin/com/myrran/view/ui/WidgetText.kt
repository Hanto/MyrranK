package com.myrran.view.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label

class WidgetText<T>(

    text: T,
    font: BitmapFont,
    textColor: Color = Color.WHITE,
    val shadowTickness: Float = 1f,
    private val formater: (T) -> String =  { it.toString() }

): Label(formater.invoke(text), LabelStyle(font, textColor))
{
    fun setText(text: T) {

        val newText = formater.invoke(text)
        super.setText(newText)
    }

    fun setText(text: T, formater: (T) -> String) {

        val newText = formater.invoke(text)
        super.setText(newText)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {

        color = Color.BLACK
        moveBy(shadowTickness, -shadowTickness)
        super.draw(batch, parentAlpha)

        color = Color.WHITE
        moveBy(-shadowTickness, shadowTickness)
        super.draw(batch, parentAlpha)
    }
}
