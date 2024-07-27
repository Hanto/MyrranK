package com.myrran.domain.skills.templates.stat

import com.myrran.domain.skills.stat.Stat
import com.myrran.domain.skills.stat.StatBonus
import com.myrran.domain.skills.stat.StatFixed
import com.myrran.domain.skills.stat.StatId
import com.myrran.domain.skills.stat.StatName

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
