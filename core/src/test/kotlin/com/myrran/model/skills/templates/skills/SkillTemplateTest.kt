package com.myrran.model.skills.templates.skills

import com.myrran.model.skills.skills.skill.SkillName
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
import com.myrran.model.skills.templates.stat.TemplateStatUpgradeable
import com.myrran.model.spells.spell.SkillType
import org.junit.jupiter.api.Test

class SkillTemplateTest {

    @Test
    fun pim() {

        val skillTemplate = SkillTemplate(
            type = SkillType.BOLT,
            name = SkillName("FIRE BOLT"),
            stats = listOf(
                TemplateStatUpgradeable(
                    id = StatId("SPEED"),
                    name = StatName("speed"),
                    baseBonus = StatBonus(10.0f),
                    maximum = Upgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)),

                TemplateStatUpgradeable(
                    id = StatId("Cooldown"),
                    name = StatName("cooldown"),
                    baseBonus = StatBonus(10.0f),
                    maximum = Upgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f))
            ),
            slots = listOf(
                TemplateSubSkillSlot(
                    id = SubSkillSlotId("IMPACT"),
                    name = SubSkillSlotName("impact"),
                    lock = Lock(listOf(LockTypes.ALPHA, LockTypes.BETA))
                )
            )
        )

        val skill = skillTemplate.toSkill()

        println(skill)
    }
}
