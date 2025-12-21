package dev.dwak.lender.data.modifier.handler

import dev.dwak.lender.data.modifier.DataModification
import dev.zacsweers.metro.MapKey
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.CLASS,
    AnnotationTarget.TYPE,
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ModificationKey(val m: KClass<out DataModification<*>>)