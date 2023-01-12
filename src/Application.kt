package com.farhan.kotlin.ktor.todoapp

import com.farhan.kotlin.ktor.todoapp.models.WebTodo
import com.farhan.kotlin.ktor.todoapp.routes.webTodo
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Application.module(testing: Boolean = false) {

    install(CallLogging)

    install(Locations) {
    }

    val todoList = mutableListOf<WebTodo>()
    todoList.add(WebTodo(1,"Apple"))
    todoList.add(WebTodo(2,"Leo"))

    routing {
        webTodo(todoList)
    }

}

const val API_VERSION = "/v1"

