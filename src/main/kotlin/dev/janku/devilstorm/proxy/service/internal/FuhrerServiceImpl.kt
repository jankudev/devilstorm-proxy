package dev.janku.devilstorm.proxy.service.internal

import dev.janku.devilstorm.proxy.service.internal.domain.PhotoLink
import dev.janku.devilstorm.proxy.service.internal.domain.Region
import dev.janku.devilstorm.proxy.service.internal.domain.Summit
import dev.janku.devilstorm.proxy.service.internal.domain.SummitDetail
import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import java.net.URL

@Service
class FuhrerServiceImpl : FuhrerService {

    override fun listRegions(): Collection<Region> {
        val document = Jsoup.connect("http://teufelsturm.de/gebiete/").get()
        return document.select("table > tbody > tr > td > font[face='Tahoma'] > a")
                .map { element ->
                    val id = element.attr("href").substringAfter("gebietnr=")
                    val name = element.text()
                    Region(id = id.toInt(), name = name)
                }
    }

    override fun getRegion(regionId: Int): Region {
        val document = Jsoup.connect("http://teufelsturm.de/gebiete/").get()
        val regionElement = document.select("a[href='/gipfel/suche.php?gebietnr=$regionId']").first()
        return Region(id = regionId, name = regionElement.text())
    }

    override fun listSummits(regionId: Int): Collection<Summit> {
        val document = Jsoup.connect("http://teufelsturm.de/gipfel/suche.php")
                .data("text", "")
                .data("gebiertnr", "$regionId")
                .data("sortiers", "3")
                .data("anzahl", "Alle").post()
        return document.select("tr:has(td[bgcolor='#376CAC'])")
                .map { element ->
                    val summitLinkElem = element.select("td[width='25%'] > font > a").first()
                    val id = summitLinkElem.attr("href").substringAfter("gipfelnr=")
                    val name = summitLinkElem.text()
                    Summit(id = id.toInt(), name = name)
                }
    }

    private val TEUFELSTURM_DE_ROOT_URL = "http://teufelsturm.de"

    override fun getSummit(summitId: Int): SummitDetail {
        val summitDocument = Jsoup.connect("http://teufelsturm.de/gipfel/details.php?gipfelnr=$summitId").get()

        val name = summitDocument.select("font[face='Tahoma'][color='#FFFFFF'][size='3']").text()
        val longitude = summitDocument.select("td > font:containsOwn(Longitude)")?.first()?.parent()?.siblingElements()?.text()
        val latitude = summitDocument.select("td > font:containsOwn(Latitude)")?.first()?.parent()?.siblingElements()?.text()

        val images = summitDocument.select("a[href^='/fotos/anzeige.php']").map { element ->
            val thumbnailUrl = URL(TEUFELSTURM_DE_ROOT_URL + element.select("img").attr("src"))

            val photoDocument = Jsoup.connect(TEUFELSTURM_DE_ROOT_URL + element.attr("href")).get()
            val url = URL(TEUFELSTURM_DE_ROOT_URL + photoDocument.select("img[src^='/img/fotos/']").attr("src"))

            PhotoLink(thumbnailUrl = thumbnailUrl, url = url)
        }

        return SummitDetail(
                summit = Summit(id = summitId, name = name),
                longitude = longitude?.toDouble(),
                latitude = latitude?.toDouble(),
                photos = images)
    }
}

fun main(args: Array<String>) {
    val service = FuhrerServiceImpl()

    /*
    println("List of all regions")
    println(service.listRegions());
    println("--------------------------")

    println("Get region 3")
    println(service.getRegion(3));
    println("--------------------------")

    println("List summits of region 3")
    println(service.listSummits(3));
    println("--------------------------")
*/
    println("Get summit 47")
    println(service.getSummit(47))
    println("--------------------------")
}