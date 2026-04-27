package dev.dwak.lender.models.api.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface ApiCreateLend {
  val itemId: String
  val lendStatus: ApiLendStatus
  val quantity: Int

  @Serializable
  @SerialName("profile")
  data class ToProfile(
    val toProfileId: String?,
    override val itemId: String,
    val groupId: String?,
    override val lendStatus: ApiLendStatus,
    override val quantity: Int,
  ) : ApiCreateLend

  @Serializable
  @SerialName("guest")
  data class ToGuest(
    val firstName: String?,
    val lastName: String?,
    override val itemId: String,
    override val lendStatus: ApiLendStatus,
    override val quantity: Int,
  ) : ApiCreateLend
}

enum class ApiLendStatus {
  REQUESTED, APPROVED, DENIED, LENT, RETURNED
}