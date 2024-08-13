package com.myrran.infraestructure.view.skills

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable

data class SkillViewAssets(

    val tableBackground: NinePatchDrawable,
    val font20: BitmapFont,
    val font14: BitmapFont,
    val font12: BitmapFont,
    val font10: BitmapFont,
    val statBarBack: TextureRegion,
    val statBarFront: TextureRegion,
    val templateHeader: NinePatchDrawable,
    val skillHeader: NinePatchDrawable,
    val containerHeader: NinePatchDrawable,
    val containerBackground: NinePatchDrawable,
    val tooltipBackground: NinePatchDrawable,
    val iconTextures: Map<String, TextureRegion>
){
    fun getIcon(iconName: String): TextureRegion =

        iconTextures[iconName]!!
}
