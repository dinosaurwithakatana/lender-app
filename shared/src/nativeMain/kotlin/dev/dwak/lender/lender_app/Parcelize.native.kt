package dev.dwak.lender.lender_app

actual interface LenderParcelable

@Target(allowedTargets = [AnnotationTarget.PROPERTY])
@Retention(value = AnnotationRetention.SOURCE)
actual annotation class LenderIgnoredOnParcel actual constructor()