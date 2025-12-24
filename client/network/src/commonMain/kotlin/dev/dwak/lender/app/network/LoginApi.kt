package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import dev.dwak.lender.models.api.auth.request.ApiLoginRequest
import dev.dwak.lender.models.api.auth.request.ApiSignUpRequest
import dev.dwak.lender.models.api.auth.response.ApiLoginSuccessResponse
import dev.dwak.lender.models.api.auth.response.ApiSignupSuccessResponse

interface LoginApi {
  @POST("/login")
  suspend fun login(@Body request: ApiLoginRequest): ApiLoginSuccessResponse

  @POST("/signup")
  suspend fun signup(@Body request: ApiSignUpRequest): ApiSignupSuccessResponse
}