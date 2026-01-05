package dev.dwak.lender.data.modifier.handler

import dev.dwak.lender.db.DbGroup
import dev.dwak.lender.db.DbItem
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerItemId
import dev.dwak.lender.models.server.ServerProfileId

fun ServerItemId.toDb() = DbItem.Id(id)
fun ServerGroupId.toDb() = DbGroup.Id(id)
fun ServerProfileId.toDb() = DbProfile.Id(id)