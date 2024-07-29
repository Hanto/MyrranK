package com.myrran.view.ui

import com.badlogic.gdx.scenes.scene2d.ui.Label

class WidgetLabel(

    text: CharSequence,
    style: LabelStyle

): Label(text, style)
{
    override fun setText(newText: CharSequence) {

        super.setText(newText)
        setSize(prefWidth, prefHeight)
    }
}
