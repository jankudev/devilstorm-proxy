package dev.janku.devilstorm.proxy.service.internal.domain

data class SummitDetail(
        val summit: Summit,

        val longitude: Double?,
        val latitude: Double?,

        val photos: Collection<PhotoLink>
)