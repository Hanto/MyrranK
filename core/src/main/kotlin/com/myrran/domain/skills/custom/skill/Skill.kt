package com.myrran.domain.skills.custom.skill

import com.myrran.domain.Identifiable
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.BuffSkillStatUpgradedEvent
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.events.SkillStatUpgradedEvent
import com.myrran.domain.events.SubSkillChangedEvent
import com.myrran.domain.events.SubSkillStatUpgradedEvent
import com.myrran.domain.skills.custom.buff.BuffSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlotContent
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.stat.Stats
import com.myrran.domain.skills.custom.stat.StatsI
import com.myrran.domain.skills.custom.stat.UpgradeCost
import com.myrran.domain.skills.custom.subskill.SubSkill
import com.myrran.domain.skills.custom.subskill.SubSkillSlot
import com.myrran.domain.skills.custom.subskill.SubSkillSlotContent
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId
import com.myrran.domain.skills.custom.subskill.SubSkillSlots
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.spells.spell.SkillType
import com.myrran.domain.utils.observer.JavaObservable
import com.myrran.domain.utils.observer.Observable

data class Skill(

    override val id: SkillId,
    val templateId: SkillTemplateId,
    val type: SkillType,
    val name: SkillName,
    private val stats: Stats,
    private val slots: SubSkillSlots,
    private val observable: Observable<SkillEvent> = JavaObservable()

): StatsI by stats, Observable<SkillEvent> by observable, Identifiable<SkillId>
{
    // SUBSKILLS
    //--------------------------------------------------------------------------------------------------------

    fun getSubSkillSlots(): Collection<SubSkillSlot> =

        slots.getSubSkillSlots()

    fun getSubSkill(subSkillSlotId: SubSkillSlotId): SubSkillSlotContent =

        slots.getSubSkill(subSkillSlotId)

    fun removeSubSkill(subSkillSlotId: SubSkillSlotId): SubSkillSlotContent? =

        slots.removeSubSkill(subSkillSlotId)
            .also { notify(SubSkillChangedEvent(subSkillSlotId)) }

    fun setSubSkill(subSkillSlotId: SubSkillSlotId, subSkill: SubSkill) =

        slots.setSubSkill(subSkillSlotId, subSkill)
            .also { notify(SubSkillChangedEvent(subSkillSlotId)) }

    // BUFFSKILL
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slots.getBuffSkill(subSkillSlotId, buffSkillSlotId)

    fun removeBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent? =

        slots.removeBuffSKill(subSkillSlotId, buffSkillSlotId)
            .also { notify(BuffSkillChangedEvent(subSkillSlotId, buffSkillSlotId)) }

    fun setBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkill: BuffSkill) =

        slots.setBuffSkill(subSkillSlotId, buffSkillSlotId, buffSkill)
            .also { notify(BuffSkillChangedEvent(subSkillSlotId, buffSkillSlotId)) }

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    override fun upgrade(statId: StatId, upgradeBy: NumUpgrades) =

        stats.upgrade(statId, upgradeBy)
            .also { notify(SkillStatUpgradedEvent(id, statId, upgradeBy)) }

    fun upgrade(subSkillSlotId: SubSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        slots.upgrade(subSkillSlotId, statId, upgradeBy)
            .also { notify(SubSkillStatUpgradedEvent(id, subSkillSlotId, statId, upgradeBy)) }

    fun upgrade(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        slots.upgrade(subSkillSlotId, buffSkillSlotId, statId, upgradeBy)
            .also { notify(BuffSkillStatUpgradedEvent(id, subSkillSlotId, buffSkillSlotId, statId, upgradeBy)) }

    fun totalCost(): UpgradeCost =

        stats.statCost() + slots.totalCost()
}
