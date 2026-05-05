package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import dev.dwak.lender.models.api.request.item.ApiCreateItemRequest
import dev.dwak.lender.models.api.response.ApiCreateItemResponse

interface ItemApi {
  @AuthRequired
  @POST("item")
  suspend fun createItem(
    @Body payload: ApiCreateItemRequest
  ): Response<ApiCreateItemResponse>
}