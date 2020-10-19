package com.jessestolijk

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import net.glxn.qrgen.core.image.ImageType
import net.glxn.qrgen.core.scheme.MeCard
import net.glxn.qrgen.javase.QRCode

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/qr") {
            call.respondHtml {
                body {
                    div {
                        h1 {
                            +"SCAN THE QR CODE!!!"
                        }
                        img (src="/qr-image") {
                        }
                    }
                }
            }
        }

        get("/qr-image") {
            val url = net.glxn.qrgen.core.scheme.Url().apply { url = "https://order-coffee-app.herokuapp.com/menu" }
            call.respondBytes(QRCode.from(url).to(ImageType.PNG).withSize(250, 250).stream().toByteArray(), ContentType.parse("image/png"))
        }

        get("/menu") {
            call.respondHtml {
                body {
                    h1 { +"Menu" }
                    a(href="/menu/order") {
                        +"One coffee"
                    }
                }
            }
        }

        get("/menu/order") {
            call.respondHtml {
                body {
                    h1 { +"Payment method" }
                    a(href="/pay/cash") {
                        +"Cash"
                    }
                    a(href="/pay/ideal") {
                        +"iDeal"
                    }
                }
            }
        }

        get("/html-dsl") {
            call.respondHtml {
                body {
                    h1 { +"HTML" }
                    ul {
                        for (n in 1..10) {
                            li { +"$n" }
                        }
                    }
                }
            }
        }
    }
}

