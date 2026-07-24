package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import dev.dwak.lender.models.api.response.ApiGetLendsResponse

interface LendApi {
  @AuthRequired
  @GET("lend/me")
  suspend fun getActiveLendsForCurrentUser(): Response<ApiGetLendsResponse>
}
