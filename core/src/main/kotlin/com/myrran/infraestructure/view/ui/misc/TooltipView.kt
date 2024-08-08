package com.myrran.infraestructure.view.ui.misc

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.utils.Null

class TooltipView<T>(

    text: T,
    font: BitmapFont,
    background: NinePatchDrawable,
    color: Color = Color.WHITE,
    private val formater: (T) -> String = { "  $it  " }

): TextTooltip(formater.invoke(text), TextTooltipStyle(LabelStyle(font, color), background))
{
    override fun enter(event: InputEvent, x: Float, y: Float, pointer: Int, @Null fromActor: Actor?) {
        super.enter(event, x, y + 70, pointer, fromActor)
    }
}
