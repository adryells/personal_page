package dev.adryell.routes

import dev.adryell.models.PostStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.text.get

fun Route.PostRouting(){
    route("/post"){
        get{
            if(PostStorage.isNotEmpty()){
                call.respond(PostStorage)
            } else {
                call.respondText("No posts found.", status = HttpStatusCode.OK)
            }
        }
    }
}