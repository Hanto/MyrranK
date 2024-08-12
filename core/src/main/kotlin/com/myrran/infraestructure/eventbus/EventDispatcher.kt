package com.myrran.infraestructure.eventbus

import com.badlogic.gdx.ai.msg.MessageDispatcher
import com.badlogic.gdx.ai.msg.Telegraph
import com.myrran.domain.events.EffectSkillChangedEvent
import com.myrran.domain.events.EffectSkillRemovedEvent
import com.myrran.domain.events.EffectSkillStatUpgradedEvent
import com.myrran.domain.events.Event
import com.myrran.domain.events.FormSkillChangedEvent
import com.myrran.domain.events.FormSkillRemovedEvent
import com.myrran.domain.events.FormSkillStatUpgradedEvent
import com.myrran.domain.events.SkillCreatedEvent
import com.myrran.domain.events.SkillRemovedEvent
import com.myrran.domain.events.SkillStatUpgradedEvent
import com.myrran.domain.events.WorldEvent.PlayerSpellCastedEvent
import com.myrran.domain.events.WorldEvent.RemoveMobEvent
import com.myrran.infraestructure.eventbus.EventDispatcher.MsgCode.*

class EventDispatcher(

    private val messageDispatcher: MessageDispatcher
)
{
    fun sendEvent(event: Event) =

        messageDispatcher.dispatchMessage(0f, event.toMsgCode().value, event)

    fun <T: Event>sendEvent(event: T, target: Telegraph) =

        messageDispatcher.dispatchMessage(0f, target, event.toMsgCode().value, event)

    fun addListener(observer: Telegraph, vararg events: MsgCode) {

        val msgCodes = events.map { it.value }.toIntArray()
        messageDispatcher.addListeners(observer, *msgCodes )
    }

    fun removeListener(observer: Telegraph) {

        val msgCodes = entries.map { it.value }.toIntArray()
        messageDispatcher.removeListener(observer, *msgCodes)
    }

    private fun Event.toMsgCode(): MsgCode =

        when (this) {
            is SkillCreatedEvent -> SkillCreatedMsg
            is EffectSkillChangedEvent -> EffectSkillChangedMsg
            is EffectSkillRemovedEvent -> EffectSkillRemovedMsg
            is EffectSkillStatUpgradedEvent -> EffectSkillStatUpgradedMsg
            is FormSkillChangedEvent -> FormSkillChangedMsg
            is FormSkillRemovedEvent -> FormSkillRemovedMsg
            is FormSkillStatUpgradedEvent -> FormSkillStatUpgradedMsg
            is SkillRemovedEvent -> SkillRemovedMsg
            is SkillStatUpgradedEvent -> SkillStatUpgradedMsg
            is PlayerSpellCastedEvent -> PlayerSpellCastedMsg
            is RemoveMobEvent -> RemoveMobEvent
            else -> SkillCreatedMsg
        }


    enum class MsgCode(val value: Int) {

        SkillCreatedMsg(0),
        EffectSkillChangedMsg(1),
        EffectSkillRemovedMsg(2),
        EffectSkillStatUpgradedMsg(3),
        FormSkillChangedMsg(4),
        FormSkillRemovedMsg(5),
        FormSkillStatUpgradedMsg(6),
        SkillRemovedMsg(7),
        SkillStatUpgradedMsg(8),
        PlayerSpellCastedMsg(10),
        RemoveMobEvent(11),
    }
}
