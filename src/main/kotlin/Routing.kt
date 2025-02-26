package com.wolkk

import io.ktor.server.application.*
import io.ktor.server.request.receiveChannel
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.readRemaining
import kotlinx.io.readByteArray
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest
import com.google.protobuf.util.JsonFormat

fun Application.configureRouting() {
    routing {
        get("/") { call.respondText("Hello, world!") }
        post("/v1/traces") {
            val rawBytes: ByteArray = call.receiveChannel().readRemaining().readByteArray()
            val exportRequest = ExportTraceServiceRequest.parseFrom(rawBytes)
            // print type of exportRequest
            println("Type of exportRequest: ${exportRequest::class.java}")

            val json = JsonFormat.printer().print(exportRequest)
            println("Received trace: $json")
            call.respondText("Tracing...")
        }
    }
}
