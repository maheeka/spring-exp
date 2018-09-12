package api.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {

    @RequestMapping("/greetings")
    fun sayHello(@RequestParam(value = "name") name: String): String {
        return "Hello Kotlin $name!"
    }

}
