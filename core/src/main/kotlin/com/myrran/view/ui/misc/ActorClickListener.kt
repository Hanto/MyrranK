package com.myrran.view.ui.misc

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener

class ActorClickListener(private val action: () -> Unit): InputListener() {

    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {

        action.invoke()
        return true
    }
}
