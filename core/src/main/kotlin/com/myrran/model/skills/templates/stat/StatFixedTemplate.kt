package com.myrran.model.skills.templates.stat

import com.myrran.model.skills.stat.Stat
import com.myrran.model.skills.stat.StatBonus
import com.myrran.model.skills.stat.StatFixed
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.StatName

class StatFixedTemplate(

    override val id: StatId,
    override val name: StatName,
    override val baseBonus: StatBonus,

): StatTemplate
{
    override fun toStat(): Stat =

        StatFixed(
            id = id,
            name = name,
            baseBonus = baseBonus)
}
