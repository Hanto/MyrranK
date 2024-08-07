package com.myrran.infraestructure.view.ui.skills.created.subskill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.GRAY
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.controller.SubSkillController
import com.myrran.domain.skills.created.SubSkill
import com.myrran.domain.skills.created.SubSkillSlotContent.NoSubSkill
import com.myrran.domain.skills.created.subskill.SubSkillSlot
import com.myrran.domain.skills.lock.LockType
import com.myrran.infraestructure.assets.PURPLE_LIGHT
import com.myrran.infraestructure.assets.SkillViewAssets
import com.myrran.infraestructure.view.ui.misc.TextView
import com.myrran.infraestructure.view.ui.misc.UIClickListener
import com.myrran.infraestructure.view.ui.misc.UIClickListener.Button.RIGHT_BUTTON

@Suppress("DuplicatedCode")
class SubSlotKeyView(

    private val model: SubSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: SubSkillController,

): Table()
{
    private val runesLabel: TextView<String> = TextView(model.toName(), assets.font12, model.toColor(), 1f)
    private var keys: List<TextView<String>> = getKeys()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        touchable = Touchable.enabled
        addListener(UIClickListener(RIGHT_BUTTON) { controller.removeSubSkill() })
        top().center()
        setBackground(assets.tableBackgroundLight)
        rebuildTable()
    }

    private fun rebuildTable() {

        clearChildren()
        val runesRow = Table()
        keys.forEach{ runesRow.add(it) }
        add(runesLabel.align(Align.center)).padLeft(1f).padTop(-3f).row()
        add(runesRow).padLeft(1f).padTop(-6f).padBottom(-1f)
    }

    // DRAG AND DROP NOTIFICATIONS
    //--------------------------------------------------------------------------------------------------------

    fun dontHighlight() {

        runesLabel.setTextColor(model.toColor())
        runesLabel.clearActions()
        runesLabel.addAction(Actions.fadeIn(0.4f))
    }

    fun highlightWithColor(color: Color) {

        runesLabel.setTextColor(color)
        runesLabel.addAction(Actions.forever(Actions.sequence(
            Actions.fadeOut(0.2f, Interpolation.circleIn),
            Actions.fadeIn(1.0f, Interpolation.circleOut)
        )))
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() {

        keys = getKeys()
        rebuildTable()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun getKeys() =

        model.lock.openedBy
            .map { TextView(it.value, assets.font12, it.toColor(), 1f) }

    private fun SubSkillSlot.toName(): String =

        when (val subSkill = model.content) {

            is NoSubSkill -> this.name.value
            is SubSkill -> subSkill.name.value
        }

    private fun SubSkillSlot.toColor(): Color =

        when (model.content) {

            is NoSubSkill -> GRAY
            is SubSkill -> ORANGE
        }

    private fun LockType.toColor(): Color =

        when (val subSkill = model.content) {

            is NoSubSkill -> GRAY
            is SubSkill -> when (subSkill.keys.contains(this)) {
                true -> PURPLE_LIGHT
                false -> GRAY
            }
        }
}
