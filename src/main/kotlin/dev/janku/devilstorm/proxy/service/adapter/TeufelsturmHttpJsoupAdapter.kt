package dev.janku.devilstorm.proxy.service.adapter

import dev.janku.devilstorm.proxy.service.port.TeufelsturmWebpagePort
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class TeufelsturmHttpJsoupAdapter : TeufelsturmWebpagePort {

    @Value("teufelsturm.root.url")
    lateinit var ROOT_URL: String

    override fun getPage(rootRelativeUrl: String): Document {
        return Jsoup.connect(ROOT_URL + rootRelativeUrl).get()
    }

    override fun postPage(rootRelativeUrl: String, data: Map<String, String>): Document {
        val connection = Jsoup.connect(ROOT_URL + rootRelativeUrl)
        data.forEach { it -> connection.data(it.key, it.value) }
        return connection.post()
    }
}