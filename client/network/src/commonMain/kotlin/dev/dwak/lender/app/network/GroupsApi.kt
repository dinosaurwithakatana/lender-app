package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import dev.dwak.lender.models.api.request.group.ApiCreateGroupRequest
import dev.dwak.lender.models.api.response.ApiCreateGroupResponse
import dev.dwak.lender.models.api.response.ApiGetGroupDetailResponse
import dev.dwak.lender.models.api.response.ApiGetGroupsResponse
import dev.dwak.lender.models.api.response.ApiGroup

interface GroupsApi {
  @AuthRequired
  @GET("groups")
  suspend fun getGroupsForCurrentUser(): ApiGetGroupsResponse

  @AuthRequired
  @GET("groups/{groupId}")
  suspend fun getGroup(@Path("groupId") groupId: String): ApiGetGroupDetailResponse

  @AuthRequired
  @POST("groups")
  suspend fun createGroup(
    @Body payload: ApiCreateGroupRequest,
  ): Response<ApiCreateGroupResponse>
}
