package com.myrran.badlogic

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent

interface DaDSource {

    fun getActor(): Actor
    fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int): Payload
    fun dragStop(event: InputEvent, x: Float, y: Float, pointer: Int, payload: Payload, target: DaDTarget?) {}


}
