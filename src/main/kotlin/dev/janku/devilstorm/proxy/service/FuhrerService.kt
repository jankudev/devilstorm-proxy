package dev.janku.devilstorm.proxy.service

import dev.janku.devilstorm.proxy.domain.Region
import dev.janku.devilstorm.proxy.domain.Summit
import dev.janku.devilstorm.proxy.domain.SummitDetail

interface FuhrerService {

    fun listRegions(): Collection<Region>
    fun getRegion(regionId: Int): Region

    fun listSummits(regionId: Int): Collection<Summit>
    fun getSummit(summitId: Int): SummitDetail

}