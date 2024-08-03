package com.myrran.view.ui.misc

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import ktx.math.minus

class ActorMoveListener2(

    private val parent: Actor

) : DragListener() {

    // CONSTRUCTOR:
    //--------------------------------------------------------------------------------------------------------

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

        val scrollX = dragX - touchDownX
        val scrollY = dragY - touchDownY

        val dragger = event.listenerActor
        //parent.moveBy(scrollX, scrollY)




        //println("${draggerPos.x}  ${parentPos.x}")



        //println("OFFSET: ${offset.x}")

        val uiHeight = Gdx.graphics.height
        val uiWidth = Gdx.graphics.width

        val offset = parent.localToStageCoordinates(Vector2())
            .minus(Vector2(parent.x, parent.y))

        parent.moveBy(scrollX, scrollY)
        val draggerPos = dragger.localToStageCoordinates(Vector2())
        val parentPos = parent.localToStageCoordinates(Vector2())

        val offsetBetwenDraggerAndParent = dragger.localToStageCoordinates(Vector2())
            .minus(parent.localToStageCoordinates(Vector2()))

        println("DRAG-PARENT OFFSET:$offsetBetwenDraggerAndParent")

        println("DRAGGER: ${draggerPos.x}  PARENT: ${parent.getX()}  PAREN_STAGE: ${parentPos.x}   OFFSET: ${offset.x}")

        if (parent.x < - offset.x)
            parent.x = - offset.x

        if (parent.x + dragger.width + offset.x > uiWidth)
            parent.x = uiWidth - dragger.width - offset.x

        if (draggerPos.y < 0)
            parent.y = - offsetBetwenDraggerAndParent.y - offset.y

        if (draggerPos.y + dragger.height > uiHeight)
            parent.y = uiHeight - dragger.height - offsetBetwenDraggerAndParent.y - offset.y

        println("DRAGGER: ${draggerPos.x}  PARENT: ${parent.getX()}  PAREN_STAGE: ${parentPos.x}   OFFSET: ${offset.x}")
    }
}
