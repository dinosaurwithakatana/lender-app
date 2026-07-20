package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import dev.dwak.lender.models.api.request.item.ApiCreateItemRequest
import dev.dwak.lender.models.api.response.ApiCreateItemResponse
import dev.dwak.lender.models.api.response.ApiGetItemsReponse

interface ItemApi {
  @AuthRequired
  @POST("item")
  suspend fun createItem(
    @Body payload: ApiCreateItemRequest
  ): Response<ApiCreateItemResponse>

  @AuthRequired
  @GET("item")
  suspend fun getItems(): Response<ApiGetItemsReponse>
}