package controller

import exceptions.RequestHandlingException
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import model.Coordinates
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rest.Controller
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ControllerTests {

  @Test
  fun `controller sends error if being invoked with non valid params`() {
    val request = mockk<HttpServletRequest>()
    val response = mockk<HttpServletResponse>()
    every { response.sendError(400, any()) } returns Unit
    val controller = Controller(request, response)
    val nonValidCoordinates = Coordinates(0, -47.0f)
    controller.doRequest(listOf(nonValidCoordinates)) {}
    verify { response.sendError(400,any()) }
    confirmVerified(response)

  }
}