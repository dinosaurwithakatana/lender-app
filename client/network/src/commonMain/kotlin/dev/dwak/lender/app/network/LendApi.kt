package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import dev.dwak.lender.models.api.request.ApiCreateLend
import dev.dwak.lender.models.api.response.ApiGetLendsResponse

interface LendApi {
  @AuthRequired
  @GET("lend/me")
  suspend fun getActiveLendsForCurrentUser(): Response<ApiGetLendsResponse>

  @AuthRequired
  @POST("lend")
  suspend fun createLend(
    @Body payload: ApiCreateLend,
  ): Response<Unit>
}
