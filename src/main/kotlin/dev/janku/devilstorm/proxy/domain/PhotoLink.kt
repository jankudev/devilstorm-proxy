package dev.janku.devilstorm.proxy.domain

import java.net.URL

data class PhotoLink(
        val thumbnailUrl: URL,
        val url: URL
)