package com.myrran.application

import com.myrran.domain.Quantity
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.BuffSkillRemovedEvent
import com.myrran.domain.events.BuffSkillStatUpgradedEvent
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.events.SkillStatUpgradedEvent
import com.myrran.domain.events.SubSkillChangedEvent
import com.myrran.domain.events.SubSkillRemovedEvent
import com.myrran.domain.events.SubSkillStatUpgradedEvent
import com.myrran.domain.skills.custom.BuffSkill
import com.myrran.domain.skills.custom.SubSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.skill.SkillId
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId
import com.myrran.domain.skills.templates.BuffSkillTemplate
import com.myrran.domain.skills.templates.SkillTemplate
import com.myrran.domain.skills.templates.SubSkillTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.domain.utils.observer.JavaObservable
import com.myrran.domain.utils.observer.Observable
import com.myrran.infraestructure.skill.CreatedSkillRepository

data class SpellBook(

    val created: CreatedSkillRepository,
    private val learned: LearnedSkillTemplates,
    private val observable: Observable<SkillEvent> = JavaObservable()

): Observable<SkillEvent> by observable
{

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun learn(id: SkillTemplateId) = learned.learn(id)
    fun learn(id: SubSkillTemplateId) = learned.learn(id)
    fun learn(id: BuffSkillTemplateId) = learned.learn(id)

    fun learnedSkillTemplates(): Collection<Quantity<SkillTemplate>> = learned.learnedSkillTemplates()
    fun learnedSubSkillTemplates(): Collection<Quantity<SubSkillTemplate>> = learned.learnedSubSkillTemplates()
    fun learnedBuffSkillTemplates(): Collection<Quantity<BuffSkillTemplate>> = learned.learnedBuffSkillTemplates()

    // ADD:
    //--------------------------------------------------------------------------------------------------------

    fun addSkill(id: SkillTemplateId) {

        val skillTemplate = learned.findBy(id)

        if (skillTemplate.isAvailable()) {

            val skill = skillTemplate.value.toSkill()

            created.save(skill)
            learned.decreaseAndSaveSkill(skillTemplate)
        }
    }

    fun setSubSkillTo(skillId: SkillId, subSkillSlotId: SubSkillSlotId, subSkillTemplateId: SubSkillTemplateId) {

        val skill = created.findBy(skillId)!!
        val subSkillTemplate = learned.findBy(subSkillTemplateId)

        if (skill.isSubSkillOpenedBy(subSkillSlotId, subSkillTemplate.value) && subSkillTemplate.isAvailable())
        {
            removeSubSkill(skillId, subSkillSlotId)
            val subSkill = subSkillTemplate.value.toSubSkill()
            skill.setSubSkill(subSkillSlotId, subSkill)

            created.save(skill)
            learned.decreaseAndSaveSub(subSkillTemplate)
            notify(SubSkillChangedEvent(skillId, subSkillSlotId, subSkill))
        }
    }

    fun setBuffSKillTo(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkillTemplateId: BuffSkillTemplateId) {

        val skill = created.findBy(skillId)!!
        val buffSkillTemplate = learned.findBy(buffSkillTemplateId)

        if (skill.isBuffSkillOpenedBy(subSkillSlotId, buffSkillSlotId, buffSkillTemplate.value) && buffSkillTemplate.isAvailable()) {

            removeBuffSkill(skillId, subSkillSlotId, buffSkillSlotId)
            val buffSkill = buffSkillTemplate.value.toBuffSkill()
            skill.setBuffSkill(subSkillSlotId, buffSkillSlotId, buffSkill)

            created.save(skill)
            learned.decreaseAndSaveBuff(buffSkillTemplate)
            notify(BuffSkillChangedEvent(skillId, subSkillSlotId, buffSkillSlotId, buffSkill))
        }
    }

    // REMOVE:
    //--------------------------------------------------------------------------------------------------------

    fun removeSkill(skillId: SkillId) {

        val skill = created.findBy(skillId)!!

        val removed = skill.removeAllSubSkills()
        val removedSubSkills = removed.filterIsInstance<SubSkill>()
        val removedBuffSkills = removed.filterIsInstance<BuffSkill>()

        learned.increaseAndSave(skill)
        learned.increaseAndSaveSubs(removedSubSkills)
        learned.increaseAndSaveBuffs(removedBuffSkills)
        TODO() // remove skill
    }

    fun removeSubSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId) {

        val skill = created.findBy(skillId)!!

        val removed = skill.removeSubSkill(subSkillSlotId)
        val removedSubSkills = removed.filterIsInstance<SubSkill>()
        val removedBuffSkills = removed.filterIsInstance<BuffSkill>()

        created.save(skill)
        learned.increaseAndSaveSubs(removedSubSkills)
        learned.increaseAndSaveBuffs(removedBuffSkills)
        notify(SubSkillRemovedEvent(skillId, removed))
    }

    fun removeBuffSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId) {

        val skill = created.findBy(skillId)!!

        skill.removeBuffSkill(subSkillSlotId, buffSkillSlotId)?.also { buffSkill ->

            created.save(skill)
            learned.increaseAndSave(buffSkill.templateId)
            notify(BuffSkillRemovedEvent(skillId, buffSkill))
        }
    }

    // IS OPENED:
    //--------------------------------------------------------------------------------------------------------

    fun isBuffSkillOpenedBy(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkillTemplateId: BuffSkillTemplateId): Boolean {

        val skill = created.findBy(skillId)!!
        val buffSkillTemplate = learned.findBy(buffSkillTemplateId)

        return skill.isBuffSkillOpenedBy(subSkillSlotId, buffSkillSlotId, buffSkillTemplate.value)
    }

    fun isSubSkillOpenedBy(skillId: SkillId, subSkillSlotId: SubSkillSlotId, subSkillTemplateId: SubSkillTemplateId): Boolean {

        val skill = created.findBy(skillId)!!
        val subSkillTemplate = learned.findBy(subSkillTemplateId)

        return skill.isSubSkillOpenedBy(subSkillSlotId, subSkillTemplate.value)
    }

    // UPGRADE:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(skillId: SkillId, statId: StatId, upgradeBy: NumUpgrades) {

        val skill = created.findBy(skillId)

        skill?.upgrade(statId, upgradeBy)
            ?.also { created.save(skill) }
            ?.also { notify(SkillStatUpgradedEvent(skillId, statId, upgradeBy)) }
    }

    fun upgrade(skillId: SkillId, subSkillSlotId: SubSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) {

        val skill = created.findBy(skillId)

        skill?.upgrade(subSkillSlotId, statId, upgradeBy)
            ?.also { created.save(skill) }
            ?.also { notify(SubSkillStatUpgradedEvent(skillId, subSkillSlotId, statId, upgradeBy)) }
    }

    fun upgrade(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) {

        val skill = created.findBy(skillId)

        skill?.upgrade(subSkillSlotId, buffSkillSlotId, statId, upgradeBy)
            ?.also { created.save(skill) }
            ?.also { notify(BuffSkillStatUpgradedEvent(skillId, subSkillSlotId, buffSkillSlotId, statId, upgradeBy)) }
    }
}
