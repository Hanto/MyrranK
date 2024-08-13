package com.myrran.infraestructure.view.ui

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener

class UIClickListener(

    private vararg val buttons: Button,
    private val action: () -> Unit,

): InputListener() {

    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {

        if (buttons.any { it.gdxButton == button } || buttons.isEmpty() )
            action.invoke()
        return true
    }

    enum class Button(val gdxButton: Int?) {

        RIGHT_BUTTON(Input.Buttons.RIGHT),
        LEFT_BUTTON(Input.Buttons.LEFT),
        MIDDLE_BUTTON(Input.Buttons.BACK),
        BACK_BUTTON(Input.Buttons.BACK),
        FORWARD_BUTTON(Input.Buttons.FORWARD),

    }
}

