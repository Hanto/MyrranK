package com.myrran.view.ui.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
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
    private val front: TextureRegion = assets.statBarFront
    private val back: TextureRegion = assets.statBarBack

    companion object {

        val COLOR_0_25 = Color(255 / 255f, 180 / 255f, 0 / 255f, 0.75f)
        val COLOR_26_50 = Color(255 / 255f, 100 / 255f, 0 / 255f, 0.55f)
    }

    init {

        width = back.regionWidth.toFloat()
        height = back.regionHeight.toFloat()
        update()

        this.addListener(object: InputListener() {

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {

                controller.upgrade.invoke(stat.id, NumUpgrades(1))
                return true
            }
        })
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() {

        barUpTo25 = (width.toInt()/25) * stat.upgrades.actual
            .atMax(NumUpgrades(25)).value.toFloat()

        barUpTo50 = (width.toInt()/25) * stat.upgrades.actual
            .minus(NumUpgrades( 25))
            .atMin(NumUpgrades(0))
            .atMax(NumUpgrades(50)).value.toFloat()
    }

    // DRAW:
    //--------------------------------------------------------------------------------------------------------

    override fun draw(batch: Batch, parentAlpha: Float) {

        batch.color = WHITE
        batch.draw(back, x, y, originX, originY, width, height, scaleX, scaleY, rotation)

        batch.color = COLOR_0_25
        batch.draw(front, x, y, originX, originY, barUpTo25, height, scaleX, scaleY, rotation)

        if (stat.upgrades.actual.value > 25) {

            batch.color = COLOR_26_50
            batch.draw(front, x, y, originX, originY, barUpTo50, height, scaleX, scaleY, rotation)
        }
    }
}
