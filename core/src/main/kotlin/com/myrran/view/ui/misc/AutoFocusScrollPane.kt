package com.myrran.view.ui.misc

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane

class AutoFocusScrollPane(actor: Actor): ScrollPane(actor)
{
    init {

        addListener(object: InputListener() {

            override fun enter(event: InputEvent, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                stage?.setScrollFocus(this@AutoFocusScrollPane)
            }

            override fun exit(event: InputEvent, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                stage?.setScrollFocus(null)
            }
        })
    }
}
