package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import dev.dwak.lender.models.api.request.auth.ApiLoginRequest
import dev.dwak.lender.models.api.request.auth.ApiSignUpRequest
import dev.dwak.lender.models.api.response.ApiLoginSuccessResponse
import dev.dwak.lender.models.api.response.ApiSignupSuccessResponse

interface LoginApi {
  @POST("login")
  suspend fun login(@Body request: ApiLoginRequest): Response<ApiLoginSuccessResponse>

  @POST("signup")
  suspend fun signup(@Body request: ApiSignUpRequest): Response<ApiSignupSuccessResponse>

  @AuthRequired
  @POST("logout")
  suspend fun logout(): Response<Unit>
}