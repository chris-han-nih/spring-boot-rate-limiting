package chris.ratelimiting

import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Component
class RateLimiter {
  private val requests = ConcurrentHashMap<String, Pair<Int, LocalDateTime>>()
  private val MAX_REQUESTS_PER_HOUR = 10 // 시간당 최대 요청 허용 수
  
  fun isAllowed(ip: String): Boolean {
    val currentTime = LocalDateTime.now()
    val requestInfo = requests[ip]
    
    // 요청 정보가 없거나 마지막 요청 시간이 1시간 이상 전이면, 요청 수를 1로 리셋합니다.
    if (requestInfo == null || requestInfo.second.plusSeconds(10).isBefore(currentTime)) {
      requests[ip] = Pair(1, currentTime)
      return true
    } else {
      // 시간당 요청 수가 최대 요청 허용 수를 초과하지 않는 경우 요청을 허용하고 카운트를 증가시킵니다.
      if (requestInfo.first < MAX_REQUESTS_PER_HOUR) {
        requests[ip] = Pair(requestInfo.first + 1, requestInfo.second)
        return true
      }
    }
    
    // 그 외의 경우, 요청은 제한됩니다.
    return false
  }
}
