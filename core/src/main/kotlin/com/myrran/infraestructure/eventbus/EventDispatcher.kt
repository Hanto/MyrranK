package com.myrran.infraestructure.eventbus

import com.badlogic.gdx.ai.msg.MessageDispatcher
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.ai.msg.Telegraph
import com.myrran.domain.events.Event
import kotlin.reflect.KClass

class EventDispatcher(

    private val messageDispatcher: MessageDispatcher

): EventSender
{
    private val eventToMsgMap: MutableMap<KClass<out Event>, Int> = mutableMapOf()

    override fun sendEvent(event: Event) {

        val msgCode = retrieveMsgCode(event::class)
        messageDispatcher.dispatchMessage(0f, msgCode , event)
    }

    override fun sendEvent(event: Event, target: EventListener) {

        val msgCode = retrieveMsgCode(event::class)
        messageDispatcher.dispatchMessage(0f, target, msgCode, event)
    }

    override fun addListener(listener: EventListener, vararg events: KClass<out Event>) {

        val msgCodes = events.map { retrieveMsgCode(it) }.toIntArray()

        messageDispatcher.addListeners(listener, *msgCodes )
    }

    override fun removeListener(listener: EventListener) {

        val msgCodes = eventToMsgMap.values.toIntArray()
        messageDispatcher.removeListener(listener, *msgCodes)
    }

    override fun update() =

        messageDispatcher.update()

    // MSG CODE CREATION:
    //--------------------------------------------------------------------------------------------------------

    private fun retrieveMsgCode(event: KClass<out Event>): Int =

        eventToMsgMap.computeIfAbsent(event) { nextMsgCode() }

    private fun nextMsgCode() =

        eventToMsgMap.values.maxOfOrNull { it + 1 } ?: 0
}

interface EventListener: Telegraph {

    override fun handleMessage(msg: Telegram): Boolean {

        val event = msg.extraInfo as Event
        handleEvent(event)
        return true
    }

    fun handleEvent(event: Event): Unit?
}

interface EventSender {

    fun sendEvent(event: Event)
    fun sendEvent(event: Event, target: EventListener)
    fun addListener(listener: EventListener, vararg events: KClass<out Event>)
    fun removeListener(listener: EventListener)
    fun update()
}
