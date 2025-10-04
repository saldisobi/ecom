package com.saldi.ecom

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<EcomApplication>().with(TestcontainersConfiguration::class).run(*args)
}
