package dev.janku.devilstorm.proxy.service.adapter

import dev.janku.devilstorm.proxy.service.port.TeufelsturmWebpagePort
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class TeufelsturmHttpJsoupAdapter(
        @Value("\${teufelsturm.root.url}") private val rootUrl: String
) : TeufelsturmWebpagePort {

    override fun getPage(rootRelativeUrl: String): Document {
        return Jsoup.connect(rootUrl + rootRelativeUrl).get()
    }

    override fun postPage(rootRelativeUrl: String, data: Map<String, String>): Document {
        val connection = Jsoup.connect(rootUrl + rootRelativeUrl)
        data.forEach { connection.data(it.key, it.value) }
        return connection.post()
    }
}