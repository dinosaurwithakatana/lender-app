package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import dev.dwak.lender.models.api.response.ApiProfile
import dev.dwak.lender.models.api.response.ApiProfileResponse

interface ProfileApi {
  @AuthRequired
  @GET("profiles/me")
  suspend fun getCurrentProfile(): Response<ApiProfileResponse>

  @AuthRequired
  @GET("profiles/lookup")
  suspend fun lookupProfile(
    @Query("email") email: String,
  ): Response<ApiProfile>
}
