package dev.dwak.lender.models.api.request

import kotlinx.serialization.Serializable

@Serializable
sealed interface ApiCreateLend {
  val itemId: String
  val groupId: String?
  val lendStatus: ApiLendStatus
  val quantity: Int

  @Serializable
  data class ToProfile(
    val toProfileId: String?,
    override val itemId: String,
    override val groupId: String?,
    override val lendStatus: ApiLendStatus,
    override val quantity: Int,
  ) : ApiCreateLend

  @Serializable
  data class ToGuest(
    val guestFirstName: String?,
    val guestLastName: String?,
    override val itemId: String,
    override val groupId: String?,
    override val lendStatus: ApiLendStatus,
    override val quantity: Int,
  ) : ApiCreateLend
}

enum class ApiLendStatus {
  REQUESTED, APPROVED, DENIED, LENT, RETURNED
}