package dev.dwak.lender.data.modifier

import de.mkammerer.argon2.Argon2
import de.mkammerer.argon2.Argon2Factory
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@ContributesTo(AppScope::class)
interface ArgonProvider {

    @Provides
    @SingleIn(AppScope::class)
    fun argonFactory(): Argon2 = Argon2Factory.create()

    @Provides
    fun hasher(argon2: Argon2): PasswordHasher = {
        argon2.hash(
            10,
            65536,
            1,
            it.toCharArray()
        )
    }

    @Provides
    fun verifier(argon2: Argon2): PasswordVerifier = { hashed, password ->
        argon2.verify(hashed, password)
    }
}

typealias PasswordHasher = (String) -> String
typealias PasswordVerifier = (String, CharArray) -> Boolean