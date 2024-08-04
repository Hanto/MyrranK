package com.myrran.infraestructure.adapters

import com.myrran.domain.skills.custom.buff.BuffSkillName
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.buff.BuffSkillSlotName
import com.myrran.domain.skills.custom.skill.SkillName
import com.myrran.domain.skills.custom.stat.BonusPerUpgrade
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatBonus
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.stat.StatName
import com.myrran.domain.skills.custom.stat.UpgradeCost
import com.myrran.domain.skills.custom.subskill.SubSkillName
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId
import com.myrran.domain.skills.custom.subskill.SubSkillSlotName
import com.myrran.domain.skills.templates.Lock
import com.myrran.domain.skills.templates.buff.BuffSkillSlotTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.stat.StatFixedTemplate
import com.myrran.domain.skills.templates.stat.StatTemplate
import com.myrran.domain.skills.templates.stat.StatUpgradeableTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillSlotTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.infraestructure.BuffSKillTemplateEntity
import com.myrran.infraestructure.SkillTemplateEntity
import com.myrran.infraestructure.SlotTemplateEntity
import com.myrran.infraestructure.StatTemplateEntity
import com.myrran.infraestructure.SubSkillTemplateEntity

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
            slots = entity.slots.map { toDomainSubSkillSlot(it) }
        )

    fun fromDomain(domain: SubSkillTemplate): SubSkillTemplateEntity =

        SubSkillTemplateEntity(
            id = domain.id.value,
            type = domain.type,
            name = domain.name.value,
            stats = domain.stats.map { fromDomain(it) },
            slots = domain.slots.map { fromDomain(it) },
            keys = domain.keys.toList()
        )

    fun toDomain(entity: SubSkillTemplateEntity): SubSkillTemplate =

        SubSkillTemplate(
            id = SubSkillTemplateId(entity.id),
            type = entity.type,
            name = SubSkillName(entity.name),
            stats = entity.stats.map { toDomain(it) },
            slots = entity.slots.map { toDomainBuffSkillSlot(it) },
            keys = entity.keys
        )

    fun fromDomain(domain: BuffSkillTemplate): BuffSKillTemplateEntity =

        BuffSKillTemplateEntity(
            id = domain.id.value,
            type = domain.type,
            name = domain.name.value,
            stats = domain.stats.map { fromDomain(it) },
            keys = domain.keys.toList()
        )

    fun toDomain(entity: BuffSKillTemplateEntity): BuffSkillTemplate =

        BuffSkillTemplate(
            id = BuffSkillTemplateId(entity.id),
            type = entity.type,
            name = BuffSkillName(entity.name),
            stats = entity.stats.map { toDomain(it) },
            keys = entity.keys
        )

    private fun fromDomain(domain: SubSkillSlotTemplate): SlotTemplateEntity =

        SlotTemplateEntity(
            id = domain.id.value,
            name = domain.name.value,
            lock = domain.lock.openedBy.toList()
        )

    private fun fromDomain(domain: BuffSkillSlotTemplate): SlotTemplateEntity =

        SlotTemplateEntity(
            id = domain.id.value,
            name = domain.name.value,
            lock = domain.lock.openedBy.toList()
        )

    private fun toDomainSubSkillSlot(entity: SlotTemplateEntity): SubSkillSlotTemplate =

        SubSkillSlotTemplate(
            id = SubSkillSlotId(entity.id),
            name = SubSkillSlotName(entity.name),
            lock = Lock(entity.lock)
        )

    private fun toDomainBuffSkillSlot(entity: SlotTemplateEntity): BuffSkillSlotTemplate =

        BuffSkillSlotTemplate(
            id = BuffSkillSlotId(entity.id),
            name = BuffSkillSlotName(entity.name),
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
