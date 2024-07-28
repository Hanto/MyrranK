package com.myrran.infraestructure

import com.badlogic.gdx.Gdx
import com.myrran.domain.skills.book.WorldSkillBook
import com.myrran.domain.utils.DeSerializer
import com.myrran.infraestructure.adapters.SkillBookAdapter
import com.myrran.infraestructure.entities.WorldSkillBookEntity
import com.myrran.view.atlas.Atlas
import com.myrran.view.atlas.AtlasConfiguration

class Repository(

    private val skillBookAdapter: SkillBookAdapter,
    private val deSerializer: DeSerializer
)
{
    fun loadSkillBook(): WorldSkillBook {

        val json = Gdx.files.internal("WorldSkillBook.json").readString()
        val entity = deSerializer.deserialize(json, WorldSkillBookEntity::class.java)
        return skillBookAdapter.toDomain(entity)
    }

    fun loadAtlas(): Atlas {

        val json = Gdx.files.internal("AtlasConfiguration.json").readString()
        val atlasConfiguration = deSerializer.deserialize(json, AtlasConfiguration::class.java)
        return Atlas(atlasConfiguration)
    }

    fun saveSkillBook(worldSkillBook: WorldSkillBook) {

        val entity = skillBookAdapter.fromDomain(worldSkillBook)
        val json = deSerializer.serialize(entity)
        Gdx.files.local("WorldSkillBook2.json").writeString(json, false)
    }
}
