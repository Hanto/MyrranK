package com.myrran.model.skills.stat

data class Stats(

    private val values: Collection<Stat>

):StatsI
{
    private val statMap: Map<StatId, Stat> = values.associateBy { it.id }

    override fun getStats(): Collection<Stat> = values

    override fun getStat(statId: StatId): Stat? = statMap[statId]

    override fun upgrade(statId: StatId, upgradeBy: Upgrades) =

        when (val stat = statMap[statId]) {
            is StatUpgradeable -> stat.upgrade(upgradeBy)
            is StatFixed, null -> Unit
        }

    override fun totalCost(): UpgradeCost =

        statMap.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.sum(next) }
}
