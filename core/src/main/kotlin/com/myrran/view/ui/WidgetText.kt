package com.myrran.view.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup

class WidgetText(

    text: CharSequence,
    font: BitmapFont,
    textColor: Color,
    shadowColor: Color,
    shadowTickness: Int

): WidgetGroup()
{
    private val textLabel = WidgetLabel(text, LabelStyle(font, textColor))
    private val shadowLabel = WidgetLabel(text, LabelStyle(font, shadowColor))

    init {

        shadowLabel.setPosition(x + shadowTickness, y - shadowTickness )
        this.addActor(shadowLabel)
        this.addActor(textLabel)
    }

    fun setText(newText: String) {

        textLabel.setText(newText)
        shadowLabel.setText(newText)
    }
}
