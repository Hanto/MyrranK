package com.myrran.infraestructure.repositories.skilltemplate

import com.myrran.domain.skills.created.effect.EffectSkillName
import com.myrran.domain.skills.created.effect.EffectSkillSlotId
import com.myrran.domain.skills.created.effect.EffectSkillSlotName
import com.myrran.domain.skills.created.form.FormSkillName
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.form.FormSkillSlotName
import com.myrran.domain.skills.created.skill.SkillName
import com.myrran.domain.skills.created.stat.BonusPerUpgrade
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatBonus
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.stat.StatName
import com.myrran.domain.skills.created.stat.UpgradeCost
import com.myrran.domain.skills.lock.Lock
import com.myrran.domain.skills.templates.effect.EffectSlotTemplate
import com.myrran.domain.skills.templates.effect.EffectTemplate
import com.myrran.domain.skills.templates.effect.EffectTemplateId
import com.myrran.domain.skills.templates.form.FormSlotTemplate
import com.myrran.domain.skills.templates.form.FormTemplate
import com.myrran.domain.skills.templates.form.FormTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.stat.StatFixedTemplate
import com.myrran.domain.skills.templates.stat.StatTemplate
import com.myrran.domain.skills.templates.stat.StatUpgradeableTemplate

class SkillTemplateAdapter {

    fun fromDomain(domain: SkillTemplate): SkillTemplateEntity =

        SkillTemplateEntity(
            id = domain.id.value,
            type = domain.type,
            name = domain.name.value,
            stats = domain.stats.map { fromDomain(it) },
            slots = domain.slots.map { fromDomain(it) },
        )

    fun toDomain(entity: SkillTemplateEntity): SkillTemplate =

        SkillTemplate(
            id = SkillTemplateId(entity.id),
            type = entity.type,
            name = SkillName(entity.name),
            stats = entity.stats.map { toDomain(it) },
            slots = entity.slots.map { toDomainFormSkillSlot(it) }
        )

    fun fromDomain(domain: FormTemplate): FormTemplateEntity =

        FormTemplateEntity(
            id = domain.id.value,
            type = domain.type,
            name = domain.name.value,
            collisionType = domain.collisionType,
            stats = domain.stats.map { fromDomain(it) },
            slots = domain.slots.map { fromDomain(it) },
            keys = domain.keys.toList()
        )

    fun toDomain(entity: FormTemplateEntity): FormTemplate =

        FormTemplate(
            id = FormTemplateId(entity.id),
            type = entity.type,
            name = FormSkillName(entity.name),
            collisionType = entity.collisionType,
            stats = entity.stats.map { toDomain(it) },
            slots = entity.slots.map { toDomainEffectSkillSlot(it) },
            keys = entity.keys
        )

    fun fromDomain(domain: EffectTemplate): EffectTemplateEntity =

        EffectTemplateEntity(
            id = domain.id.value,
            type = domain.type,
            name = domain.name.value,
            stats = domain.stats.map { fromDomain(it) },
            keys = domain.keys.toList()
        )

    fun toDomain(entity: EffectTemplateEntity): EffectTemplate =

        EffectTemplate(
            id = EffectTemplateId(entity.id),
            type = entity.type,
            name = EffectSkillName(entity.name),
            stats = entity.stats.map { toDomain(it) },
            keys = entity.keys
        )

    private fun fromDomain(domain: FormSlotTemplate): SlotTemplateEntity =

        SlotTemplateEntity(
            id = domain.id.value,
            name = domain.name.value,
            lock = domain.lock.openedBy.toList()
        )

    private fun fromDomain(domain: EffectSlotTemplate): SlotTemplateEntity =

        SlotTemplateEntity(
            id = domain.id.value,
            name = domain.name.value,
            lock = domain.lock.openedBy.toList()
        )

    private fun toDomainFormSkillSlot(entity: SlotTemplateEntity): FormSlotTemplate =

        FormSlotTemplate(
            id = FormSkillSlotId(entity.id),
            name = FormSkillSlotName(entity.name),
            lock = Lock(entity.lock)
        )

    private fun toDomainEffectSkillSlot(entity: SlotTemplateEntity): EffectSlotTemplate =

        EffectSlotTemplate(
            id = EffectSkillSlotId(entity.id),
            name = EffectSkillSlotName(entity.name),
            lock = Lock(entity.lock)
        )

    private fun fromDomain(domain: StatTemplate): StatTemplateEntity =

        when (domain) {

            is StatFixedTemplate -> StatTemplateEntity(
                id = domain.id.value,
                name = domain.name.value,
                baseBonus = domain.baseBonus.value
            )

            is StatUpgradeableTemplate -> StatTemplateEntity(
                id = domain.id.value,
                name = domain.name.value,
                baseBonus = domain.baseBonus.value,
                maximum = domain.maximum.value,
                upgradeCost = domain.upgradeCost.value,
                bonusPerUpgrade = domain.bonusPerUpgrade.value
            )
        }

    private fun toDomain(entity: StatTemplateEntity): StatTemplate =

        when (entity.maximum == null) {

            true -> StatFixedTemplate(
                id = StatId(entity.id),
                name = StatName(entity.name),
                baseBonus = StatBonus(entity.baseBonus)
            )

            false -> StatUpgradeableTemplate(
                id = StatId(entity.id),
                name = StatName(entity.name),
                baseBonus = StatBonus(entity.baseBonus),
                maximum = NumUpgrades(entity.maximum),
                upgradeCost = UpgradeCost(entity.upgradeCost!!),
                bonusPerUpgrade = BonusPerUpgrade(entity.bonusPerUpgrade!!)
            )
        }
}
