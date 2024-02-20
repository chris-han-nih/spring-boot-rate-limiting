package chris.ratelimiting

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RateLimitingApplication

fun main(args: Array<String>) {
  runApplication<RateLimitingApplication>(*args)
}
