package com.myrran.domain.events

import com.myrran.domain.mob.MobId
import com.myrran.domain.mob.metrics.Meter
import com.myrran.domain.mob.metrics.Position
import com.myrran.domain.skills.created.skill.SkillId

sealed interface WorldEvent: Event {

    data class PlayerSpellCastedEvent(

        val mobId: MobId,
        val skillId: SkillId,
        val origin: Position<Meter>,
        val target: Position<Meter>

    ): WorldEvent

    data class MobRemovedEvent(

        val mobId: MobId

    ): WorldEvent
}
