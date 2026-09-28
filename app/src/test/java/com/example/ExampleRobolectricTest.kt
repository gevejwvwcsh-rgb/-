package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.StoreRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("HAMO ALMBRMJ", appName)
    }

    @Test
    fun `store repository has 8 categories and products`() {
        assertEquals(8, StoreRepository.categories.size)
        assertTrue(StoreRepository.products.isNotEmpty())
        assertEquals("01020931398", StoreRepository.STORE_PHONE)
        assertEquals("01020931398", StoreRepository.OWNER_PHONE)
        assertEquals("01096700804", StoreRepository.DEVELOPER_PHONE)
        assertEquals("المعلم حماده رزق", StoreRepository.OWNER_NAME)
        assertEquals("𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱", StoreRepository.DEVELOPER_NAME)
        assertEquals("UcCMOsqxm%be*a!MWMzUx5uD", StoreRepository.DEVELOPER_PASSWORD)
    }

    @Test
    fun `developer session recognizes developer role with password`() {
        val devSession = com.example.data.model.UserSession(
            isLoggedIn = true,
            role = com.example.data.model.UserRole.DEVELOPER,
            isVerified = true
        )
        assertTrue(devSession.isDeveloper)

        val customerSession = com.example.data.model.UserSession(
            isLoggedIn = true,
            phoneNumber = "01096700804",
            role = com.example.data.model.UserRole.CUSTOMER,
            isVerified = true
        )
        org.junit.Assert.assertFalse(customerSession.isDeveloper)
    }
}
