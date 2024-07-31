package com.myrran.view.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
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

    override fun draw(batch: Batch, parentAlpha: Float) {

        color = Color.BLACK
        moveBy(2f, -2f)
        super.draw(batch, parentAlpha)

        color = Color.WHITE
        moveBy(-2f, 2f)
        super.draw(batch, parentAlpha)
    }
}
