package net.empyrean.client

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

interface PlayerClientDataAccessor {
    @Environment(EnvType.CLIENT)
    fun getClientData(): PlayerClientData
}