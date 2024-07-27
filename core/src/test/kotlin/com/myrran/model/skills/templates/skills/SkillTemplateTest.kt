package com.myrran.model.skills.templates.skills

import com.myrran.model.skills.skills.buffSkill.BuffSkillName
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotId
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotName
import com.myrran.model.skills.skills.skill.SkillName
import com.myrran.model.skills.skills.subskill.SubSkillName
import com.myrran.model.skills.skills.subskill.SubSkillSlotId
import com.myrran.model.skills.skills.subskill.SubSkillSlotName
import com.myrran.model.skills.stat.BonusPerUpgrade
import com.myrran.model.skills.stat.StatBonus
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.StatName
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.skills.templates.Lock
import com.myrran.model.skills.templates.LockTypes
import com.myrran.model.skills.templates.stat.StatUpgradeableTemplate
import com.myrran.model.spells.buffs.BuffType
import com.myrran.model.spells.spell.SkillType
import com.myrran.model.spells.subspells.SubSkillType
import org.junit.jupiter.api.Test

class SkillTemplateTest {

    @Test
    fun pim() {

        val skillTemplate = SkillTemplate(
            id = SkillTemplateId("FIREBOLT_1"),
            type = SkillType.BOLT,
            name = SkillName("Fire bolt"),
            stats = listOf(
                StatUpgradeableTemplate(
                    id = StatId("SPEED"),
                    name = StatName("speed"),
                    baseBonus = StatBonus(10.0f),
                    maximum = Upgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)),

                StatUpgradeableTemplate(
                    id = StatId("Cooldown"),
                    name = StatName("cooldown"),
                    baseBonus = StatBonus(10.0f),
                    maximum = Upgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f))
            ),
            slots = listOf(
                SubSkillSlotTemplate(
                    id = SubSkillSlotId("IMPACT"),
                    name = SubSkillSlotName("impact"),
                    lock = Lock(listOf(LockTypes.ALPHA, LockTypes.BETA))
                )
            )
        )

        val subSkillTemplate = SubSkillTemplate(
            id = SubSkillTemplateId("EXPLOSION_1"),
            type = SubSkillType.EXPLOSION,
            name = SubSkillName("Explosion"),
            stats = listOf(
                StatUpgradeableTemplate(
                    id = StatId("RADIUS"),
                    name = StatName("radius"),
                    baseBonus = StatBonus(10.0f),
                    maximum = Upgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f))
            ),
            slots = listOf(
                BuffSkillSlotTemplate(
                    id = BuffSkillSlotId("DEBUFF_1"),
                    name = BuffSkillSlotName("Debuff 1"),
                    lock = Lock(listOf(LockTypes.GAMMA, LockTypes.EPSILON)),
                )
            ),
            keys = listOf(LockTypes.ALPHA)
        )

        val buffSkillTemplate = BuffSkillTemplate(
            id = BuffSkillTemplateId("FIRE_1"),
            type = BuffType.FIRE,
            name = BuffSkillName("Fire"),
            stats = listOf(
                StatUpgradeableTemplate(
                    id = StatId("DAMAGE"),
                    name = StatName("damage per second"),
                    baseBonus = StatBonus(10.0f),
                    maximum = Upgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f))
            ),
            keys = listOf(LockTypes.GAMMA)
        )

        val skill = skillTemplate.toSkill()
        skill.setSubSkill(SubSkillSlotId("IMPACT"), subSkillTemplate.toSubSkill())
        skill.setBuffSkill(SubSkillSlotId("IMPACT"), BuffSkillSlotId("DEBUFF_1"), buffSkillTemplate.toBuffSkill())

        skill.upgrade(StatId("SPEED"), Upgrades(10))
        skill.upgrade(SubSkillSlotId("IMPACT"), StatId("RADIUS"), Upgrades(10))
        skill.upgrade(SubSkillSlotId("IMPACT"), BuffSkillSlotId("DEBUFF_1"), StatId("DAMAGE"), Upgrades(10))

        println(skill)
        println("total cost: ${skill.totalCost()}")
    }
}
