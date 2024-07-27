package com.myrran.model.skills.templates.stat

import com.myrran.model.skills.stat.Stat
import com.myrran.model.skills.stat.StatBonus
import com.myrran.model.skills.stat.StatFixed
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.StatName

class TemplateStatFixed(

    override val id: StatId,
    override val name: StatName,
    override val baseBonus: StatBonus,

): TemplateStat
{
    override fun toStat(): Stat =

        StatFixed(
            id = id,
            name = name,
            baseBonus = baseBonus)
}
