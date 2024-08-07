package com.myrran.infraestructure.view.ui.misc

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

    fun setTextColor(color: Color) {

        style.fontColor = color
    }

    fun align(alignment: Int): TextView<T> =
        super.setAlignment(alignment).let { this }

    override fun draw(batch: Batch, alpha: Float) {

        val originalColor = Color(color)

        if (shadowTickness != 0f) {

            color = Color.BLACK
            moveBy(shadowTickness, -shadowTickness)
            super.draw(batch, alpha)
        }

        color = originalColor
        moveBy(-shadowTickness, shadowTickness)
        super.draw(batch, alpha)
    }
}
