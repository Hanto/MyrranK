package com.myrran.domain.skills.created

import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.BuffSkillRemovedEvent
import com.myrran.domain.events.BuffSkillStatUpgradedEvent
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.events.SkillStatUpgradedEvent
import com.myrran.domain.events.SubSkillChangedEvent
import com.myrran.domain.events.SubSkillRemovedEvent
import com.myrran.domain.events.SubSkillStatUpgradedEvent
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.observer.JavaObservable
import com.myrran.domain.misc.observer.Observable
import com.myrran.domain.skills.created.buff.BuffSkillSlotId
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.domain.skills.created.skill.SkillName
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.stat.Stats
import com.myrran.domain.skills.created.stat.StatsI
import com.myrran.domain.skills.created.stat.UpgradeCost
import com.myrran.domain.skills.created.subskill.SubSkillSlot
import com.myrran.domain.skills.created.subskill.SubSkillSlotId
import com.myrran.domain.skills.created.subskill.SubSkillSlots
import com.myrran.domain.skills.templates.BuffSkillTemplate
import com.myrran.domain.skills.templates.SubSkillTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.spells.spell.SkillType

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

        slots.getSubSkillSlots().map { it.copy() }

    fun getSubSkill(subSkillSlotId: SubSkillSlotId): SubSkill? =

        slots.getSubSkill(subSkillSlotId)?.copy()

    fun isSubSkillOpenedBy(subSkillSlotId: SubSkillSlotId, subSkillTemplate: SubSkillTemplate): Boolean =

        slots.getSubSkillSlots().none { it.getSubSkill()?.templateId == subSkillTemplate.id } &&
        slots.isSubSkillOpenedBy(subSkillSlotId, subSkillTemplate)

    fun removeSubSkill(subSkillSlotId: SubSkillSlotId): Collection<SubBuffSkill> =

        (slots.removeSubSkill(subSkillSlotId)?.let { subSkill -> subSkill.removeAllBuffSkills().map { it.copy() } + subSkill.copy() } ?: emptyList<SubBuffSkill>())
            .also { if (it.isNotEmpty()) notify(SubSkillRemovedEvent(id, it)) }

    fun removeAllSubSkills(): Collection<SubBuffSkill> =

        slots.removeAllSubSkills().let { subSkills -> subSkills.flatMap { it.removeAllBuffSkills() } + subSkills }
            .also { if (it.isNotEmpty()) notify(SubSkillRemovedEvent(id, it)) }

    fun setSubSkill(subSkillSlotId: SubSkillSlotId, subSkill: SubSkill) =

        slots.setSubSkill(subSkillSlotId, subSkill)
            .also { notify(SubSkillChangedEvent(id, subSkillSlotId, subSkill)) }

    // BUFFSKILL
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkill? =

        slots.getBuffSkill(subSkillSlotId, buffSkillSlotId)?.copy()

    fun isBuffSkillOpenedBy(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkillTemplate: BuffSkillTemplate): Boolean =

        slots.isBuffSkillOpenedBy(subSkillSlotId, buffSkillSlotId, buffSkillTemplate)

    fun removeBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkill? =

        slots.removeBuffSKill(subSkillSlotId, buffSkillSlotId)?.copy()
            ?.also { notify(BuffSkillRemovedEvent(id, it)) }

    fun setBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkill: BuffSkill) =

        slots.setBuffSkill(subSkillSlotId, buffSkillSlotId, buffSkill)
            .also { notify(BuffSkillChangedEvent(id, subSkillSlotId, buffSkillSlotId, buffSkill)) }

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
