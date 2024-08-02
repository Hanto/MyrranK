package com.myrran.domain.skills.skills.skill

import com.myrran.domain.Identifiable
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.StatUpgradedEvent
import com.myrran.domain.events.SubSkillChangedEvent
import com.myrran.domain.skills.skills.buff.BuffSkill
import com.myrran.domain.skills.skills.buff.BuffSkillSlotContent
import com.myrran.domain.skills.skills.buff.BuffSkillSlotId
import com.myrran.domain.skills.skills.stat.NumUpgrades
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.stat.Stats
import com.myrran.domain.skills.skills.stat.StatsI
import com.myrran.domain.skills.skills.stat.UpgradeCost
import com.myrran.domain.skills.skills.subskill.SubSkill
import com.myrran.domain.skills.skills.subskill.SubSkillSlot
import com.myrran.domain.skills.skills.subskill.SubSkillSlotContent
import com.myrran.domain.skills.skills.subskill.SubSkillSlotId
import com.myrran.domain.skills.skills.subskill.SubSkillSlots
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
    private val observable: Observable = JavaObservable()

): StatsI by stats, Observable by observable, Identifiable<SkillId>
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

    fun upgrade(subSkillSlotId: SubSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        slots.upgrade(subSkillSlotId, statId, upgradeBy)
            .also { notify(StatUpgradedEvent) }

    fun upgrade(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        slots.upgrade(subSkillSlotId, buffSkillSlotId, statId, upgradeBy)
            .also { notify(StatUpgradedEvent) }

    override fun upgrade(statId: StatId, upgradeBy: NumUpgrades) =

        stats.upgrade(statId, upgradeBy)
            .also { notify(StatUpgradedEvent) }

    fun totalCost(): UpgradeCost =

        stats.statCost() + slots.totalCost()
}
