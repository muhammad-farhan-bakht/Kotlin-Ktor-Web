package com.farhan.kotlin.ktor.todoapp.models

sealed class ResponseType{
    data class ResponseWithError(val statusCode:Int, val success:Boolean, val exceptionMessage: String) : ResponseType()
}




