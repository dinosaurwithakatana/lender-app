package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import dev.dwak.lender.models.api.response.ApiProfileResponse

interface ProfileApi {
  @AuthRequired
  @GET("profiles/me")
  suspend fun getCurrentProfile(): Response<ApiProfileResponse>
}
