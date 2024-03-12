package dev.adryell.plugins

import dev.adryell.routes.PostRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hi, i'm having sex with gnomes!")
        }
        get("/coito"){
            call.respondText { "Coito is a expression used in Brazil for to referring to the sex act." }
        }
        PostRouting()
    }
}
