package com.example.consistenthashing.routes

import com.example.consistenthashing.service.ConsistentHashingService
import com.satya.consistenthashing.model.KeyResponse
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.registerRoutes(service: ConsistentHashingService) {
    route("/nodes") {
        post {
            println("POST /nodes hit")
            try {
                val nodeId = call.receive<String>()
                println("Received: $nodeId")
                service.addNode(nodeId)
                call.respond(HttpStatusCode.Created, "Node $nodeId added.")
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, "Invalid payload: ${e.message}")
            }
        }
        delete("/{nodeId}") {
            val nodeId = call.parameters["nodeId"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            service.removeNode(nodeId)
            call.respond(HttpStatusCode.OK, "Node $nodeId removed.")
        }
        get {
            call.respond(service.getRingState().keys.toList())
        }
    }

    route("/keys") {
        post {
            val request = call.receive<Map<String, Map<String, String>>>()
            request.forEach { (key, meta) -> service.storeKey(key, meta) }
            call.respond(HttpStatusCode.Created, "Stored keys.")
        }
        get("/{key}") {
            val key = call.parameters["key"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val (node, meta) = service.getKey(key)
            if (node == null || meta == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(KeyResponse(node, meta))
        }
    }

    get("/ring") {
        call.respond(service.getRingState())
    }
}
