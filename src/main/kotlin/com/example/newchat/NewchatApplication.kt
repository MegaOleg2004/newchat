package com.example.newchat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NewchatApplication

fun main(args: Array<String>) {
	runApplication<NewchatApplication>(*args)
}
