package dev.dwak.lender.data.modifier.handler.lend

import dev.dwak.lender.data.modification.item.CreateItemMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.BoundHandler
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(CreateItemMod::class) BoundHandler>()
)
class CreateLendHandler : DataModification.Handler<CreateItemMod.Result, CreateItemMod> {
  override suspend fun handle(mod: CreateItemMod): CreateItemMod.Result {
    TODO("Not yet implemented")
  }
}