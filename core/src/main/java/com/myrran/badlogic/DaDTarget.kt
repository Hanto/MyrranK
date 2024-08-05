package com.myrran.badlogic

import com.badlogic.gdx.scenes.scene2d.Actor

interface DaDTarget {

    fun drag(source: DaDSource, payload: Payload, x: Float, y: Float, pointer: Int): Boolean
    fun reset(source: DaDSource, payload: Payload) {}
    fun drop(source: DaDSource, payload: Payload, x: Float, y: Float, pointer: Int)
    fun getActor(): Actor
    fun notifyNewPayload(payload: Payload)
    fun notifyNoPayload()

}
