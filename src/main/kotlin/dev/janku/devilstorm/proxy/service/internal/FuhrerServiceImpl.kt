package dev.janku.devilstorm.proxy.service.internal

import dev.janku.devilstorm.proxy.domain.PhotoLink
import dev.janku.devilstorm.proxy.domain.Region
import dev.janku.devilstorm.proxy.domain.Summit
import dev.janku.devilstorm.proxy.domain.SummitDetail
import dev.janku.devilstorm.proxy.service.FuhrerService
import dev.janku.devilstorm.proxy.service.port.TeufelsturmWebpagePort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URL

@Service
class FuhrerServiceImpl : FuhrerService {
    @Value("teufelsturm.root.url")
    lateinit var ROOT_URL: String

    @Autowired
    private lateinit var teufelsturmWebpagePort: TeufelsturmWebpagePort

    override fun listRegions(): Collection<Region> {
        val document = teufelsturmWebpagePort.getPage("/gebiete/")
        return document.select("table > tbody > tr > td > font[face='Tahoma'] > a")
                .map { element ->
                    val id = element.attr("href").substringAfter("gebietnr=")
                    val name = element.text()
                    Region(id = id.toInt(), name = name)
                }
    }

    override fun getRegion(regionId: Int): Region {
        val document = teufelsturmWebpagePort.getPage("/gebiete/")
        val regionElement = document.select("a[href='/gipfel/suche.php?gebietnr=$regionId']").first()
        return Region(id = regionId, name = regionElement.text())
    }

    override fun listSummits(regionId: Int): Collection<Summit> {
        val document = teufelsturmWebpagePort.postPage("/gipfel/suche.php", mapOf(
                "text" to "",
                "gebiertnr" to "$regionId",
                "sortiers" to "3",
                "anzahl" to "Alle"
        ))
        return document.select("tr:has(td[bgcolor='#376CAC'])")
                .map { element ->
                    val summitLinkElem = element.select("td[width='25%'] > font > a").first()
                    val id = summitLinkElem.attr("href").substringAfter("gipfelnr=")
                    val name = summitLinkElem.text()
                    Summit(id = id.toInt(), name = name)
                }
    }

    override fun getSummit(summitId: Int): SummitDetail {
        val summitDocument = teufelsturmWebpagePort.getPage("/gipfel/details.php?gipfelnr=$summitId")

        val name = summitDocument.select("font[face='Tahoma'][color='#FFFFFF'][size='3']").text()
        val longitude = summitDocument.select("td > font:containsOwn(Longitude)")?.first()?.parent()?.siblingElements()?.text()
        val latitude = summitDocument.select("td > font:containsOwn(Latitude)")?.first()?.parent()?.siblingElements()?.text()

        val images = summitDocument.select("a[href^='/fotos/anzeige.php']").map { element ->
            val thumbnailUrl = URL(ROOT_URL + element.select("img").attr("src"))

            val photoDocument = teufelsturmWebpagePort.getPage(element.attr("href"))
            val url = URL(ROOT_URL + photoDocument.select("img[src^='/img/fotos/']").attr("src"))

            PhotoLink(thumbnailUrl = thumbnailUrl, url = url)
        }

        return SummitDetail(
                summit = Summit(id = summitId, name = name),
                longitude = longitude?.toDouble(),
                latitude = latitude?.toDouble(),
                photos = images)
    }
}