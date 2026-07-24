package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import dev.dwak.lender.models.api.request.group.ApiCreateGroupRequest
import dev.dwak.lender.models.api.response.ApiCreateGroupResponse
import dev.dwak.lender.models.api.response.ApiGetGroupMembersResponse
import dev.dwak.lender.models.api.response.ApiGetGroupsResponse

interface GroupsApi {
  @AuthRequired
  @GET("groups/me")
  suspend fun getGroupsForCurrentUser(): ApiGetGroupsResponse

  @AuthRequired
  @POST("groups")
  suspend fun createGroup(
    @Body payload: ApiCreateGroupRequest,
  ): Response<ApiCreateGroupResponse>

  @AuthRequired
  @GET("groups/{groupId}/members")
  suspend fun getGroupMembers(
    @Path("groupId") groupId: String,
  ): Response<ApiGetGroupMembersResponse>
}
