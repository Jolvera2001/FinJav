package dev.jolvera.finjav.serviceTests

import dev.jolvera.finjav.FinJavKoinModule
import dev.jolvera.finjav.testModules.SqliteMemTestModule
import io.kotest.core.spec.style.FunSpec
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin

class UserServiceTests : FunSpec({
    beforeSpec {
        startKoin { modules(FinJavKoinModule, SqliteMemTestModule) }
    }

    afterSpec {
        stopKoin()
    }

    test("should register user in database")
})