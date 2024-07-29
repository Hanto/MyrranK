package com.myrran.view.atlas

data class AtlasConfiguration(

    val atlas: List<String>,
    val fonts: List<String>,
    val textureRegions: List<TextureRegionConfiguration>
)

data class TextureRegionConfiguration(
    val atlas: String,
    val texture: String
)
