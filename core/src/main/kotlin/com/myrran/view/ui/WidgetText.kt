package com.myrran.view.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup

class WidgetText<T>(

    text: T,
    font: BitmapFont,
    textColor: Color = Color.WHITE,
    shadowColor: Color = Color.BLACK,
    shadowTickness: Float = 1f,
    private val formater: (T) -> String =  { it.toString() }

): WidgetGroup()
{
    private val textLabel: WidgetLabel
    private val shadowLabel: WidgetLabel

    init {

        val textString = formater.invoke(text)
        textLabel = WidgetLabel(textString, LabelStyle(font, textColor))
        shadowLabel = WidgetLabel(textString, LabelStyle(font, shadowColor))
        shadowLabel.setPosition(x + shadowTickness, y - shadowTickness )
        addActor(shadowLabel)
        addActor(textLabel)
    }

    fun setText(text: T) {

        val newText = formater.invoke(text)

        textLabel.setText(newText)
        shadowLabel.setText(newText)
    }

    fun setText(text: T, formater: (T) -> String) {

        val newText = formater.invoke(text)

        textLabel.setText(newText)
        shadowLabel.setText(newText)
    }
}
