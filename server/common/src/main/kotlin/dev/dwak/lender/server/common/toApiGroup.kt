package dev.dwak.lender.server.common

import dev.dwak.lender.models.api.response.ApiGroup
import dev.dwak.lender.models.server.ServerGroup

fun ServerGroup.toApiGroup(): ApiGroup = ApiGroup(
    id = id.id,
    name = name,
    createdAt = createdAt.toString()
)