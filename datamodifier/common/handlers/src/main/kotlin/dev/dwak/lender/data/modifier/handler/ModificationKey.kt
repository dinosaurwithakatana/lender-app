package dev.dwak.lender.data.modifier.handler

import dev.dwak.lender.data.modifier.DataModification
import dev.zacsweers.metro.MapKey
import kotlin.reflect.KClass

@MapKey
annotation class ModificationKey(val m: KClass<out DataModification<*>>)