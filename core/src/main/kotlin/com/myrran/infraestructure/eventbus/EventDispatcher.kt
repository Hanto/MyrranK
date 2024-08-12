package com.myrran.infraestructure.eventbus

import com.badlogic.gdx.ai.msg.MessageDispatcher
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.ai.msg.Telegraph
import com.myrran.domain.events.Event
import kotlin.reflect.KClass

class EventDispatcher(

    private val messageDispatcher: MessageDispatcher
)
{
    private val eventToMsgMap: MutableMap<KClass<out Event>, Int> = mutableMapOf()

    fun sendEvent(event: Event) {

        val msgCode = eventToMsgMap[event::class]!!
        messageDispatcher.dispatchMessage(0f, msgCode , event)
    }

    fun sendEvent(event: Event, target: Telegraph) {

        val msgCode = eventToMsgMap[event::class]!!
        messageDispatcher.dispatchMessage(0f, target, msgCode, event)
    }

    fun addListener(listener: EventListener, vararg events: KClass<out Event>) {

        val msgCodes = events.map { eventToMsgMap.computeIfAbsent(it) { nextMsgCode() } }.toIntArray()

        messageDispatcher.addListeners(listener, *msgCodes )
    }

    fun removeListener(listener: EventListener) {

        val msgCodes =eventToMsgMap.values.toIntArray()
        messageDispatcher.removeListener(listener, *msgCodes)
    }

    fun update() =

        messageDispatcher.update()

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
