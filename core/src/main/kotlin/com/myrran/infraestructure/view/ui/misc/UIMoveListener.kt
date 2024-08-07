package com.myrran.infraestructure.view.ui.misc

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import ktx.math.minus

class UIMoveListener(

    private val parent: Actor

) : DragListener() {

    init {

        tapSquareSize = 0f
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {

        super.touchDown(event, x, y, pointer, button)
        parent.toFront()
        return true
    }

    override fun drag(event: InputEvent, x: Float, y: Float, pointer: Int) {

        val scroll = Vector2(dragX - touchDownX, dragY - touchDownY)
        val screenSize = Vector2(event.stage.width, event.stage.height)

        parent.moveBy(scroll.x, scroll.y)

        val dragger = event.listenerActor
        val draggerPos = dragger.localToStageCoordinates(Vector2())
        val offsetToParent = parent.localToStageCoordinates(Vector2()).minus(dragger.localToStageCoordinates(Vector2()))
        val offsetToLocal = (Vector2(parent.x, parent.y)).minus(parent.localToStageCoordinates(Vector2()))

        if (draggerPos.x < 0)
            parent.x = 0 + offsetToParent.x + offsetToLocal.x

        if (draggerPos.x + dragger.width > screenSize.x)
            parent.x = screenSize.x - dragger.width + offsetToParent.x + offsetToLocal.x

        if (draggerPos.y < 0)
            parent.y = 0 + offsetToParent.y + offsetToLocal.y

        if (draggerPos.y + dragger.height > screenSize.y)
            parent.y = screenSize.y - dragger.height + offsetToParent.y + offsetToLocal.y
    }
}
