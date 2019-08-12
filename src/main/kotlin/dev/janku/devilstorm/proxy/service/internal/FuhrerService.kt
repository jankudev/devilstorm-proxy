package dev.janku.devilstorm.proxy.service.internal

import dev.janku.devilstorm.proxy.service.internal.domain.Region
import dev.janku.devilstorm.proxy.service.internal.domain.Summit
import dev.janku.devilstorm.proxy.service.internal.domain.SummitDetail

interface FuhrerService {

    fun listRegions(): Collection<Region>
    fun getRegion(regionId: Int): Region

    fun listSummits(regionId: Int): Collection<Summit>
    fun getSummit(summitId: Int): SummitDetail

}