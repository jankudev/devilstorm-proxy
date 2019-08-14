package dev.janku.devilstorm.proxy.domain

data class SummitDetail(
        val summit: Summit,

        val longitude: Double?,
        val latitude: Double?,

        val photos: Collection<PhotoLink>
)