package dev.dwak.lender.cli

import dev.dwak.lender.models.server.ServerGroup
import dev.dwak.lender.models.server.ServerItem
import dev.dwak.lender.models.server.ServerProfile

fun ServerProfile.rendered(): String = "[${id.id}] $firstName $lastName"
fun ServerItem.rendered(): String = "[${id.id}] $name"
fun ServerGroup.rendered(): String = "[${id.id}] $name"
