package com.myrran.domain.entities.common

import com.myrran.domain.misc.Identifiable

sealed interface Entity: Identifiable<EntityId>
