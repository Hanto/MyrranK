package com.myrran.view.ui.skill.stats

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.myrran.domain.skills.skills.stat.NumUpgrades
import com.myrran.domain.skills.skills.stat.StatUpgradeable
import com.myrran.view.ui.skill.assets.SkillAssets
import com.myrran.view.ui.skill.controller.StatController

class UpgradeView(

    private val stat: StatUpgradeable,
    private val assets: SkillAssets,
    private val controller: StatController

): Actor()
{
    private var barUpTo25 = 0f
    private var barUpTo50 = 0f

    init {

        width = assets.statBarBack.regionWidth.toFloat()
        height = assets.statBarBack.regionHeight.toFloat()
        update()

        this.addListener(object: InputListener() {

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {

                controller.upgrade.invoke(stat.id, NumUpgrades(1))
                return true
            }
        })
    }

    fun update() {

        barUpTo25 = (width.toInt()/25) * stat.upgrades.actual
            .atMax(NumUpgrades(25)).value.toFloat()

        barUpTo50 = (width.toInt()/25) * stat.upgrades.actual
            .minus(NumUpgrades( 25))
            .atMin(NumUpgrades(0))
            .atMax(NumUpgrades(50)).value.toFloat()
    }

    override fun draw(batch: Batch, parentAlpha: Float) {

        batch.color = color

        batch.color = Color.WHITE
        batch.draw(assets.statBarBack, x, y, originX, originY, width, height, scaleX, scaleY, rotation)

        batch.setColor(255 / 255f, 180 / 255f, 0 / 255f, 0.75f)
        batch.draw(assets.statBarFront, x, y, originX, originY, barUpTo25, height, scaleX, scaleY, rotation)

        if (stat.upgrades.actual.value > 25) {

            batch.setColor(255 / 255f, 0 / 255f, 0 / 255f, 0.55f)
            batch.draw(assets.statBarFront, x, y, originX, originY, barUpTo50, height, scaleX, scaleY, rotation)
        }
    }
}
