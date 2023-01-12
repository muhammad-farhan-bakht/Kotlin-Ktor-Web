package com.farhan.kotlin.ktor.todoapp.routes

import com.farhan.kotlin.ktor.todoapp.API_VERSION
import com.farhan.kotlin.ktor.todoapp.models.ResponseType
import com.farhan.kotlin.ktor.todoapp.models.WebTodo
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*
import java.util.*

const val WEB_TODO_ROOT = "$API_VERSION/web/todo/"
const val WEB_TODO_Add = "$API_VERSION/web/todo/add"

@KtorExperimentalLocationsAPI
@Location(WEB_TODO_ROOT)
class WebTodoRoot

@KtorExperimentalLocationsAPI
@Location(WEB_TODO_Add)
class WebTodoAdd

@KtorExperimentalLocationsAPI
fun Route.webTodo(todoList: MutableList<WebTodo>) {

    post<WebTodoAdd> {
        val formData = call.receiveParameters()
        val todo = formData["todo"] ?: return@post call.respond(ResponseType.ResponseWithError(HttpStatusCode.BadRequest.value,false,"Missing Required Fields"))
        todoList.add(WebTodo(getUID(todo.length),todo))
        call.respondRedirect(WEB_TODO_ROOT)
    }

    get<WebTodoRoot> {
        call.respondHtml {
            head {
                title { +"My Todo App" }
            }
            body {
                h1 { +"My Todo App" }
                ul {
                    for (todo in todoList) {
                        li {
                            style = "flex-basis: 48%; padding: 10px; margin: 5px;"

                            +if (todoList.isEmpty()) "No Todo Found" else todo.todoText
                        }
                    }
                }

                form(action = WEB_TODO_Add, encType = FormEncType.applicationXWwwFormUrlEncoded, method = FormMethod.post) {
                    p {
                        +"Todo: "
                        textInput(name = "todo")
                    }
                    p { style = "flex-basis: 48%; padding: 5px; margin: 5px;"
                        submitInput() { value = "Add Todo" }
                    }
                }
            }
        }
    }
}

fun getUID(digit:Int):Long{
    var currentMilliSeconds:String = ""+ Calendar.getInstance().timeInMillis
    var genDigit:Int = digit
    if(genDigit<8)
        genDigit = 8

    if(genDigit>12)
        genDigit = 12

    val cut = currentMilliSeconds.length - genDigit
    currentMilliSeconds = currentMilliSeconds.substring(cut)
    return currentMilliSeconds.toLong()
}