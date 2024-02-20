package chris.ratelimiting

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class RateLimitingFilter(val rateLimiter: RateLimiter) : Filter {
  
  override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
    val httpRequest = request as HttpServletRequest
    val httpResponse = response as HttpServletResponse
    
    val clientIp = httpRequest.remoteAddr
    if (!rateLimiter.isAllowed(clientIp)) {
      httpResponse.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests")
      return
    }
    
    chain.doFilter(request, response)
  }
  
  override fun init(filterConfig: FilterConfig?) {}
  override fun destroy() {}
}


