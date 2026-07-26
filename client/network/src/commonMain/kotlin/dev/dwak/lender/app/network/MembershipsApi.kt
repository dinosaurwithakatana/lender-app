package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.PATCH
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import dev.dwak.lender.models.api.request.membership.ApiCreateMembershipRequest
import dev.dwak.lender.models.api.request.membership.ApiUpdateMembershipRequest
import dev.dwak.lender.models.api.response.ApiCreateMembershipResponse
import dev.dwak.lender.models.api.response.ApiGetMembershipsResponse

interface MembershipsApi {
  @AuthRequired
  @GET("memberships")
  suspend fun getMemberships(
    @Query("groupId") groupId: String,
    @Query("status") status: String? = null,
  ): Response<ApiGetMembershipsResponse>

  @AuthRequired
  @POST("memberships")
  suspend fun createMembership(
    @Body payload: ApiCreateMembershipRequest,
  ): Response<ApiCreateMembershipResponse>

  @AuthRequired
  @PATCH("memberships/{id}")
  suspend fun updateMembership(
    @Path("id") id: String,
    @Body payload: ApiUpdateMembershipRequest,
  ): Response<Unit>

  @AuthRequired
  @DELETE("memberships/{id}")
  suspend fun deleteMembership(
    @Path("id") id: String,
  ): Response<Unit>
}
