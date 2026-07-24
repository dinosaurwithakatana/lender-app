package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import dev.dwak.lender.models.api.request.item.ApiCreateItemRequest
import dev.dwak.lender.models.api.response.ApiCreateItemResponse
import dev.dwak.lender.models.api.response.ApiGetItemsReponse

interface ItemApi {
  @AuthRequired
  @POST("items")
  suspend fun createItem(
    @Body payload: ApiCreateItemRequest
  ): Response<ApiCreateItemResponse>

  @AuthRequired
  @GET("items/me")
  suspend fun getCurrentUserItems(): Response<ApiGetItemsReponse>

  @AuthRequired
  @DELETE("items/{itemId}")
  suspend fun deleteItem(
    @Path("itemId") itemId: String,
  ): Response<Unit>
}