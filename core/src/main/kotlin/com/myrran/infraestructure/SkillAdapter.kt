package com.myrran.infraestructure

import com.myrran.domain.skills.skills.buff.BuffSkill
import com.myrran.domain.skills.skills.buff.BuffSkillId
import com.myrran.domain.skills.skills.buff.BuffSkillName
import com.myrran.domain.skills.skills.buff.BuffSkillSlot
import com.myrran.domain.skills.skills.buff.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.skills.buff.BuffSkillSlotId
import com.myrran.domain.skills.skills.buff.BuffSkillSlotName
import com.myrran.domain.skills.skills.buff.BuffSkillSlots
import com.myrran.domain.skills.skills.skill.Skill
import com.myrran.domain.skills.skills.skill.SkillId
import com.myrran.domain.skills.skills.skill.SkillName
import com.myrran.domain.skills.skills.stat.BonusPerUpgrade
import com.myrran.domain.skills.skills.stat.Stat
import com.myrran.domain.skills.skills.stat.StatBonus
import com.myrran.domain.skills.skills.stat.StatFixed
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.stat.StatName
import com.myrran.domain.skills.skills.stat.StatUpgradeable
import com.myrran.domain.skills.skills.stat.Stats
import com.myrran.domain.skills.skills.stat.UpgradeCost
import com.myrran.domain.skills.skills.stat.Upgrades
import com.myrran.domain.skills.skills.subskill.SubSkill
import com.myrran.domain.skills.skills.subskill.SubSkillId
import com.myrran.domain.skills.skills.subskill.SubSkillName
import com.myrran.domain.skills.skills.subskill.SubSkillSlot
import com.myrran.domain.skills.skills.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.domain.skills.skills.subskill.SubSkillSlotId
import com.myrran.domain.skills.skills.subskill.SubSkillSlotName
import com.myrran.domain.skills.skills.subskill.SubSkillSlots
import com.myrran.domain.skills.templates.Lock
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import java.util.UUID

class SkillAdapter
{
    fun fromDomain(domain: Skill): SkillEntity =

        SkillEntity(
            id = domain.id.value.toString(),
            templateId = domain.templateId.value,
            type = domain.type,
            name = domain.name.value,
            stats = domain.getStats().map { fromDomain(it) },
            slots = domain.getSubSkillSlots().map { fromDomain(it) }
        )

    fun toDomain(entity: SkillEntity): Skill =

        Skill(
            id = SkillId(UUID.fromString(entity.id)),
            templateId = SkillTemplateId(entity.templateId),
            type = entity.type,
            name = SkillName(entity.name),
            stats = Stats(entity.stats.map { toDomain(it) }.associateBy { it.id } ),
            slots = SubSkillSlots(entity.slots.map { toDomain(it) }.associateBy { it.id } )
        )

    private fun fromDomain(domain: SubSkillSlot): SubSkillSlotEntity =

        SubSkillSlotEntity(
            id = domain.id.value,
            name = domain.name.value,
            lock = domain.lock.openedBy.toList(),
            content = when (val content = domain.content) {

                NoSubSkill -> null
                is SubSkill -> fromDomain(content)
            }
        )

    private fun toDomain(entity: SubSkillSlotEntity): SubSkillSlot =

        SubSkillSlot(
            id = SubSkillSlotId(entity.id),
            name = SubSkillSlotName(entity.name),
            lock = Lock(entity.lock),
            content = when (val content = entity.content) {

                null -> NoSubSkill
                else -> toDomain(content)
            }
        )

    private fun fromDomain(domain: SubSkill): SubSkillEntity =

        SubSkillEntity(
            id = domain.id.value.toString(),
            templateId = domain.templateId.value,
            type = domain.type,
            name = domain.name.value,
            stats = domain.getStats().map { fromDomain(it) },
            slots = domain.slots.getBuffSkillSlots().map { fromDomain(it) },
            keys = domain.keys
        )

    private fun toDomain(entity: SubSkillEntity): SubSkill =

        SubSkill(
            id = SubSkillId(UUID.fromString(entity.id)),
            templateId = SubSkillTemplateId(entity.templateId),
            type = entity.type,
            name = SubSkillName(entity.name),
            stats = Stats(entity.stats.map { toDomain(it) }.associateBy { it.id } ),
            slots = BuffSkillSlots(entity.slots.map { toDomain(it) }.associateBy { it.id } ),
            keys = entity.keys
        )

    private fun fromDomain(domain: BuffSkillSlot): BuffSKillSlotEntity =

        BuffSKillSlotEntity(
            id = domain.id.value,
            name = domain.name.value,
            lock = domain.lock.openedBy.toList(),
            content = when (val content = domain.content) {

                NoBuffSkill -> null
                is BuffSkill -> fromDomain(content)
            }
        )

    private fun toDomain(entity: BuffSKillSlotEntity): BuffSkillSlot =

        BuffSkillSlot(
            id = BuffSkillSlotId(entity.id),
            name = BuffSkillSlotName(entity.name),
            lock = Lock(entity.lock),
            content = when (val content = entity.content) {

                null -> NoBuffSkill
                else -> toDomain(content)
            }
        )

    private fun fromDomain(domain: BuffSkill): BuffSkillEntity =

        BuffSkillEntity(
            id = domain.id.value.toString(),
            templateId = domain.templateId.value,
            type = domain.type,
            name = domain.name.value,
            stats = domain.getStats().map { fromDomain(it) },
            keys = domain.keys.toList()
        )

    private fun toDomain(entity: BuffSkillEntity): BuffSkill =

        BuffSkill(
            id = BuffSkillId(UUID.fromString(entity.id)),
            templateId = BuffSkillTemplateId(entity.templateId),
            type = entity.type,
            name = BuffSkillName(entity.name),
            stats = Stats(entity.stats.map { toDomain(it) }.associateBy { it.id } ),
            keys = entity.keys
        )

    private fun fromDomain(domain: Stat): StatEntity =

        when (domain) {

            is StatFixed -> StatEntity(
                id = domain.id.value,
                name = domain.name.value,
                baseBonus = domain.baseBonus.value,
            )

            is StatUpgradeable -> StatEntity(
                id = domain.id.value,
                name = domain.name.value,
                baseBonus = domain.baseBonus.value,
                actual = domain.actual.value,
                maximum = domain.maximum.value,
                upgradeCost = domain.upgradeCost.value,
                bonusPerUpgrade = domain.bonusPerUpgrade.value
            )
        }

    private fun toDomain(entity: StatEntity): Stat =

        when (entity.actual == null) {

            true -> StatFixed(
                id = StatId(entity.id),
                name = StatName(entity.name),
                baseBonus = StatBonus(entity.baseBonus)
            )

            false -> StatUpgradeable(
                id = StatId(entity.id),
                name = StatName(entity.name),
                baseBonus = StatBonus(entity.baseBonus),
                actual = Upgrades(entity.actual),
                maximum = Upgrades(entity.maximum!!),
                upgradeCost = UpgradeCost(entity.upgradeCost!!),
                bonusPerUpgrade = BonusPerUpgrade(entity.bonusPerUpgrade!!),
            )
        }
}
