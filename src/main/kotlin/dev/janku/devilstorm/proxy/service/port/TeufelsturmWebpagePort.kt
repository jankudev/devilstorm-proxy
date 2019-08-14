package dev.janku.devilstorm.proxy.service.port

import org.jsoup.nodes.Document

/**
 * Provide the webpage data for parsing
 */
interface TeufelsturmWebpagePort {
    /**
     * HTTP GET retrieve page relative to the root url
     * @param rootRelativeUrl url relative to root
     * @return JSoup document
     */
    fun getPage(rootRelativeUrl: String): Document

    /**
     * HTTP POST retrieve page relative to the root url
     * @param rootRelativeUrl url relative to root
     * @return JSoup document
     */
    fun postPage(rootRelativeUrl: String, data: Map<String, String>): Document
}