package config

import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/*"])
class CorsFilter : Filter {
  override fun init(config: FilterConfig) {}

  override fun doFilter(req: ServletRequest, resp: ServletResponse, chain: FilterChain) {
    val response = resp as HttpServletResponse
    response.setHeader("Access-Control-Allow-Origin", "*")
    response.setHeader("Access-Control-Allow-Credentials", "true")
    response.setHeader("Access-Control-Allow-Methods", "*")
    response.setHeader(
        "Access-Control-Allow-Headers",
        "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers")
    chain.doFilter(req, response)
  }

  override fun destroy() {}
}