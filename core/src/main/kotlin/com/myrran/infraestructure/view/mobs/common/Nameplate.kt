package com.myrran.infraestructure.view.mobs.common

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.myrran.domain.mobs.common.caster.Caster

class Nameplate(

    private val caster: Caster,
    private val foreground: TextureRegion,
    private val background: TextureRegion,

): Actor()
{
}
