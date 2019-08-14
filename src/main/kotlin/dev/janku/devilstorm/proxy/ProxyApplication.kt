package dev.janku.devilstorm.proxy

import dev.janku.devilstorm.proxy.service.FuhrerService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProxyApplication

fun main(args: Array<String>) {
    val ctx = runApplication<ProxyApplication>(*args)

    val service = ctx.getBean(FuhrerService::class.java)

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
