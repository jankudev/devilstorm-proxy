package dev.janku.devilstorm.proxy.service.internal

import dev.janku.devilstorm.proxy.service.port.TeufelsturmWebpagePort
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class FuhrerServiceImplTest {

    // mocks
    @Mock
    private lateinit var teufelsturmWebpagePortMock: TeufelsturmWebpagePort

    // tested service
    @InjectMocks
    private val service = FuhrerServiceImpl()

    private fun getResourceAsString(classpathUri: String): String {
        return object {}.javaClass.getResource(classpathUri).readText()
    }

    @Test
    fun listRegions() {
        // expectation
        val response = Jsoup.parse(getResourceAsString("/data/teufelsturm/gebiete.html"))
        `when`(teufelsturmWebpagePortMock.getPage("/gebiete/")).thenReturn(response)

        // test
        val regions = service.listRegions()

        // verification
        verify(teufelsturmWebpagePortMock).getPage("/gebiete/")
        Assertions.assertEquals("[Region(id=1, name=Gebiet der Steine), Region(id=2, name=Bielatal), Region(id=3, name=Schrammsteine), Region(id=4, name=Schmilkaer Gebiet), Region(id=5, name=Rathener Gebiet), Region(id=6, name=Wehlener Gebiet), Region(id=7, name=Brandgebiet), Region(id=8, name=Kleiner Zschand), Region(id=9, name=Grosser Zschand), Region(id=10, name=Affensteine), Region(id=11, name=Erzgebirgsgrenzgebiet), Region(id=12, name=Wildensteiner Gebiet), Region(id=13, name=Hinterhermsdorfer Gebiet)]", regions.toString())
    }

    @Test
    fun getRegion() {
    }

    @Test
    fun listSummits() {
    }

    @Test
    fun getSummit() {
    }
}