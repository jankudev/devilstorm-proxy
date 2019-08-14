package dev.janku.devilstorm.proxy

import dev.janku.devilstorm.proxy.service.internal.FuhrerServiceImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProxyApplication

fun main(args: Array<String>) {
    runApplication<ProxyApplication>(*args)

    val service = FuhrerServiceImpl()

    println("List of all regions")
    println(service.listRegions())
    println("--------------------------")

    println("Get region 3")
    println(service.getRegion(3))
    println("--------------------------")

    println("List summits of region 3")
    println(service.listSummits(3))
    println("--------------------------")

    println("Get summit 47")
    println(service.getSummit(47))
    println("--------------------------")
}
