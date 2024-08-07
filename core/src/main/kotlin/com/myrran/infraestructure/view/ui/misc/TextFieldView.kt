package com.myrran.infraestructure.view.ui.misc

import com.badlogic.gdx.Input.Keys.ENTER
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.BLACK
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import ktx.actors.setKeyboardFocus

class TextFieldView<T>(

    text: T,
    font: BitmapFont,
    color: Color = Color.WHITE,
    private val shadowTickness: Float = 0f,
    private val onEnter: (String) -> Unit = { },
    private val formater: (T) -> String = { it.toString() }

): TextField(formater.invoke(text), TextFieldStyle(font, color, null, null, null)) {

    init {

        addListener(object: InputListener(){

            override fun keyDown(event: InputEvent, keycode: Int): Boolean {
                if (keycode == ENTER)
                {
                    onEnter.invoke(getText())
                    setKeyboardFocus(false)
                }
                return true
            }
        })
    }

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

    fun align(alignment: Int): TextFieldView<T> =
        super.setAlignment(alignment).let { this }

    override fun draw(batch: Batch, alpha: Float) {

        val originalColor = Color(style.fontColor)

        if (shadowTickness != 0f) {

            style.fontColor = BLACK
            moveBy(shadowTickness, -shadowTickness)
            super.draw(batch, alpha)
        }

        style.fontColor = originalColor
        moveBy(-shadowTickness, shadowTickness)
        super.draw(batch, alpha)
    }
}
