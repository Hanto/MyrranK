package com.myrran.domain.mobs.common.vulnerable

data class VulnerableComponent(

    private var actualHPs: HP,
    private var maxHPs: HP

): Vulnerable
{
    constructor(actualHPs: Int, maxHps: Int): this(HP(actualHPs.toFloat()), HP(maxHps.toFloat()))

    override fun getHPs(): HP =
        actualHPs

    override fun getMaxHps(): HP =
        maxHPs

    override fun receiveDamage(damage: HP) {
        actualHPs = (actualHPs - damage).atMin(HP(0f)) }

    override fun receiveHealing(healing: HP) {
        actualHPs = (actualHPs + healing).atMax(maxHPs)
    }
}
