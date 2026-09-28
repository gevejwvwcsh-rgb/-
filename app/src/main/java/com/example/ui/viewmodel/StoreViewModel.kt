package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.data.local.AppDatabase
import com.example.data.local.entity.ProductEntity
import com.example.data.local.entity.StoreConfigEntity
import com.example.data.local.entity.UserSessionEntity
import com.example.data.model.CartItem
import com.example.data.model.Product
import com.example.data.model.ProductSpec
import com.example.data.model.UserRole
import com.example.data.model.UserSession
import com.example.data.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLEncoder

enum class NavigationTab(val titleAr: String) {
    HOME("الرئيسية"),
    PRODUCTS("المنتجات"),
    CART("السلة"),
    SHOWROOM("المعرض"),
    CONTACT("تواصل والمحل")
}

enum class SortOption(val titleAr: String) {
    FEATURED("المميزة"),
    PRICE_LOW_TO_HIGH("الأقل سعراً"),
    PRICE_HIGH_TO_LOW("الأعلى سعراً"),
    DISCOUNT("العروض والخصومات")
}

data class StoreUiState(
    val currentTab: NavigationTab = NavigationTab.HOME,
    val selectedProduct: Product? = null,
    val searchQuery: String = "",
    val selectedCategoryId: String? = null,
    val wholesaleMode: Boolean = false,
    val sortOption: SortOption = SortOption.FEATURED,
    val cartItems: List<CartItem> = emptyList(),
    // Auth & Developer
    val userSession: UserSession = UserSession(),
    val isDeveloperDashboardOpen: Boolean = false,
    // Dynamic products and store info
    val products: List<Product> = StoreRepository.products,
    val storePhone: String = StoreRepository.STORE_PHONE,
    val developerPhone: String = StoreRepository.DEVELOPER_PHONE,
    val storeAddress: String = StoreRepository.STORE_ADDRESS,
    val storeHours: String = StoreRepository.STORE_HOURS,
    // Contact form fields
    val contactName: String = "",
    val contactPhone: String = "",
    val contactNote: String = "",
    val contactCategory: String = "طلب عرض أسعار جملة",
    // Quick quote calculator
    val quoteBathrooms: Int = 1,
    val quoteKitchens: Int = 1,
    val quoteTier: String = "تأسيس وتشطيب فاخر", // اقتصادي، سوبر لوكس، فاخر VIP
    val quoteEstimatedTotal: Long = 18500
) {
    val cartCount: Int get() = cartItems.sumOf { it.quantity }
    val cartSubtotal: Double get() = cartItems.sumOf { it.totalPrice }
    val cartWholesaleTotal: Double get() = cartItems.sumOf { it.totalWholesalePrice }
}

class StoreViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val sessionDao = db.userSessionDao()
    private val productDao = db.productDao()
    private val configDao = db.storeConfigDao()

    private val _uiState = MutableStateFlow(StoreUiState())
    val uiState: StateFlow<StoreUiState> = _uiState.asStateFlow()

    init {
        recalculateQuote()
        loadSessionFromDb()
        loadProductsFromDb()
        loadConfigFromDb()
    }

    private fun loadSessionFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val session = sessionDao.getSession()
            if (session != null && session.isVerified) {
                val role = try {
                    UserRole.valueOf(session.role)
                } catch (e: Exception) {
                    UserRole.CUSTOMER
                }
                _uiState.update {
                    it.copy(
                        userSession = UserSession(
                            isLoggedIn = true,
                            phoneNumber = session.phoneNumber,
                            email = session.email,
                            role = role,
                            isVerified = session.isVerified
                        )
                    )
                }
            }
        }
    }

    private fun loadProductsFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val dbProducts = productDao.getAllProducts()
            if (dbProducts.isEmpty()) {
                // Pre-populate with default products
                val initialEntities = StoreRepository.products.map { p ->
                    ProductEntity(
                        id = p.id,
                        categoryId = p.categoryId,
                        nameAr = p.nameAr,
                        brand = p.brand,
                        price = p.price,
                        wholesalePrice = p.wholesalePrice,
                        unitAr = p.unitAr,
                        descriptionAr = p.descriptionAr,
                        isFeatured = p.isFeatured,
                        isAvailable = p.inStock,
                        isCustom = false
                    )
                }
                productDao.insertAll(initialEntities)
            } else {
                // Merge db changes into products list
                val defaultMap = StoreRepository.products.associateBy { it.id }.toMutableMap()
                val mergedList = mutableListOf<Product>()

                dbProducts.forEach { entity ->
                    val defaultItem = defaultMap[entity.id]
                    if (defaultItem != null) {
                        mergedList.add(
                            defaultItem.copy(
                                price = entity.price,
                                wholesalePrice = entity.wholesalePrice,
                                nameAr = entity.nameAr,
                                inStock = entity.isAvailable
                            )
                        )
                        defaultMap.remove(entity.id)
                    } else {
                        // Custom product added by developer
                        mergedList.add(
                            Product(
                                id = entity.id,
                                categoryId = entity.categoryId,
                                nameAr = entity.nameAr,
                                brand = entity.brand,
                                price = entity.price,
                                wholesalePrice = entity.wholesalePrice,
                                unitAr = entity.unitAr,
                                descriptionAr = entity.descriptionAr,
                                imageRes = R.drawable.img_pipes_fittings,
                                specs = listOf(
                                    ProductSpec("الماركة", entity.brand),
                                    ProductSpec("الضمان", "معتمد من المحل")
                                ),
                                isFeatured = entity.isFeatured,
                                inStock = entity.isAvailable,
                                tagAr = "صنف جديد"
                            )
                        )
                    }
                }
                // Add any remaining default products not yet in db
                mergedList.addAll(defaultMap.values)

                _uiState.update { it.copy(products = mergedList) }
            }
        }
    }

    private fun loadConfigFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val config = configDao.getConfig()
            if (config != null) {
                _uiState.update {
                    it.copy(
                        storePhone = config.storePhone,
                        developerPhone = config.developerPhone,
                        storeAddress = config.storeAddress,
                        storeHours = config.storeHours
                    )
                }
            }
        }
    }

    // Auth actions
    fun loginWithPhone(phoneNumber: String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val entity = UserSessionEntity(
                id = 1,
                phoneNumber = phoneNumber,
                email = "",
                role = UserRole.CUSTOMER.name,
                isVerified = true,
                loginTimestamp = System.currentTimeMillis()
            )
            sessionDao.saveSession(entity)
            _uiState.update {
                it.copy(
                    userSession = UserSession(
                        isLoggedIn = true,
                        phoneNumber = phoneNumber,
                        role = UserRole.CUSTOMER,
                        isVerified = true
                    )
                )
            }
            launch(Dispatchers.Main) {
                onSuccess()
            }
        }
    }

    fun loginAsDeveloper(email: String = "", onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val entity = UserSessionEntity(
                id = 1,
                phoneNumber = "",
                email = email,
                role = UserRole.DEVELOPER.name,
                isVerified = true,
                loginTimestamp = System.currentTimeMillis()
            )
            sessionDao.saveSession(entity)
            _uiState.update {
                it.copy(
                    userSession = UserSession(
                        isLoggedIn = true,
                        email = email,
                        role = UserRole.DEVELOPER,
                        isVerified = true
                    ),
                    isDeveloperDashboardOpen = true
                )
            }
            launch(Dispatchers.Main) {
                onSuccess()
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            sessionDao.clearSession()
            _uiState.update {
                it.copy(
                    userSession = UserSession(),
                    isDeveloperDashboardOpen = false
                )
            }
        }
    }

    fun toggleDeveloperDashboard(open: Boolean) {
        _uiState.update { it.copy(isDeveloperDashboardOpen = open) }
    }

    // Developer editing actions
    fun updateProductPrices(productId: String, newPrice: Double, newWholesalePrice: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.updatePrices(productId, newPrice, newWholesalePrice)
            _uiState.update { state ->
                val updated = state.products.map {
                    if (it.id == productId) it.copy(price = newPrice, wholesalePrice = newWholesalePrice) else it
                }
                state.copy(products = updated)
            }
        }
    }

    fun addNewProduct(
        nameAr: String,
        brand: String,
        categoryId: String,
        price: Double,
        wholesalePrice: Double,
        unitAr: String,
        descriptionAr: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = "custom_p_${System.currentTimeMillis()}"
            val entity = ProductEntity(
                id = id,
                categoryId = categoryId,
                nameAr = nameAr,
                brand = brand,
                price = price,
                wholesalePrice = wholesalePrice,
                unitAr = unitAr,
                descriptionAr = descriptionAr,
                isFeatured = true,
                isAvailable = true,
                isCustom = true
            )
            productDao.insertOrUpdate(entity)

            val newProd = Product(
                id = id,
                categoryId = categoryId,
                nameAr = nameAr,
                brand = brand,
                price = price,
                wholesalePrice = wholesalePrice,
                unitAr = unitAr,
                descriptionAr = descriptionAr,
                imageRes = R.drawable.img_pipes_fittings,
                specs = listOf(
                    ProductSpec("الماركة", brand),
                    ProductSpec("الضمان", "معتمد من المحل")
                ),
                isFeatured = true,
                inStock = true,
                tagAr = "صنف جديد"
            )

            _uiState.update { state ->
                state.copy(products = listOf(newProd) + state.products)
            }
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.deleteById(productId)
            _uiState.update { state ->
                state.copy(products = state.products.filterNot { it.id == productId })
            }
        }
    }

    fun updateStoreConfig(phone: String, devPhone: String, address: String, hours: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val entity = StoreConfigEntity(
                id = 1,
                storePhone = phone,
                developerPhone = devPhone,
                storeAddress = address,
                storeHours = hours
            )
            configDao.saveConfig(entity)
            _uiState.update {
                it.copy(
                    storePhone = phone,
                    developerPhone = devPhone,
                    storeAddress = address,
                    storeHours = hours
                )
            }
        }
    }

    // Navigation and filters
    fun selectTab(tab: NavigationTab) {
        _uiState.update { it.copy(currentTab = tab, selectedProduct = null) }
    }

    fun selectProduct(product: Product?) {
        _uiState.update { it.copy(selectedProduct = product) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun selectCategory(categoryId: String?) {
        _uiState.update { it.copy(selectedCategoryId = categoryId) }
    }

    fun toggleWholesaleMode() {
        _uiState.update { it.copy(wholesaleMode = !it.wholesaleMode) }
    }

    fun setSortOption(option: SortOption) {
        _uiState.update { it.copy(sortOption = option) }
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        _uiState.update { state ->
            val existing = state.cartItems.find { it.product.id == product.id }
            val updated = if (existing != null) {
                state.cartItems.map {
                    if (it.product.id == product.id) it.copy(quantity = it.quantity + quantity) else it
                }
            } else {
                state.cartItems + CartItem(product, quantity)
            }
            state.copy(cartItems = updated)
        }
    }

    fun updateCartQuantity(productId: String, delta: Int) {
        _uiState.update { state ->
            val updated = state.cartItems.mapNotNull { item ->
                if (item.product.id == productId) {
                    val newQty = item.quantity + delta
                    if (newQty > 0) item.copy(quantity = newQty) else null
                } else item
            }
            state.copy(cartItems = updated)
        }
    }

    fun removeFromCart(productId: String) {
        _uiState.update { state ->
            state.copy(cartItems = state.cartItems.filterNot { it.product.id == productId })
        }
    }

    fun clearCart() {
        _uiState.update { it.copy(cartItems = emptyList()) }
    }

    // Contact form updates
    fun updateContactName(name: String) = _uiState.update { it.copy(contactName = name) }
    fun updateContactPhone(phone: String) = _uiState.update { it.copy(contactPhone = phone) }
    fun updateContactNote(note: String) = _uiState.update { it.copy(contactNote = note) }
    fun updateContactCategory(cat: String) = _uiState.update { it.copy(contactCategory = cat) }

    // Quote calculator
    fun setQuoteBathrooms(count: Int) {
        _uiState.update { it.copy(quoteBathrooms = count.coerceAtLeast(1)) }
        recalculateQuote()
    }

    fun setQuoteKitchens(count: Int) {
        _uiState.update { it.copy(quoteKitchens = count.coerceAtLeast(0)) }
        recalculateQuote()
    }

    fun setQuoteTier(tier: String) {
        _uiState.update { it.copy(quoteTier = tier) }
        recalculateQuote()
    }

    private fun recalculateQuote() {
        val state = _uiState.value
        val bathCost = when (state.quoteTier) {
            "اقتصادي معتمد" -> 8500
            "سوبر لوكس" -> 14500
            else -> 22000 // فاخر VIP
        }
        val kitchenCost = when (state.quoteTier) {
            "اقتصادي معتمد" -> 3500
            "سوبر لوكس" -> 6000
            else -> 9500
        }
        val total = (state.quoteBathrooms * bathCost) + (state.quoteKitchens * kitchenCost)
        _uiState.update { it.copy(quoteEstimatedTotal = total.toLong()) }
    }

    // Intent helper methods
    fun openCall(context: Context, phoneNumber: String = StoreRepository.STORE_PHONE) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "تعذر فتح الاتصال: $phoneNumber", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsApp(context: Context, customText: String = "", targetUrl: String = StoreRepository.STORE_WHATSAPP_URL) {
        try {
            val encodedMessage = URLEncoder.encode(customText, "UTF-8")
            val url = if (customText.isBlank()) {
                targetUrl
            } else {
                "$targetUrl?text=$encodedMessage"
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "تعذر فتح تطبيق واتساب", Toast.LENGTH_SHORT).show()
        }
    }

    fun contactDeveloperWhatsApp(context: Context) {
        val message = "مرحباً بالمطور 𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱 👋 أود الاستفسار والتواصل معك"
        openWhatsApp(context, message, StoreRepository.DEVELOPER_WHATSAPP_URL)
    }

    fun contactOwnerWhatsApp(context: Context) {
        val message = "السلام عليكم يا معلم حماده رزق (صاحب المحل) 👋 أود الاستفسار عن بضاعة وأسعار سباكة"
        openWhatsApp(context, message, StoreRepository.STORE_WHATSAPP_URL)
    }

    fun sendProductInquiry(context: Context, product: Product, quantity: Int = 1) {
        val message = """
            مرحباً متجر الأدوات الصحية والسباكة (براند HAMO ALMBRMJ) 👋
            أرغب في الاستفسار والطلب للمنتج التالي:
            - المنتج: ${product.nameAr}
            - الماركة: ${product.brand}
            - الكمية المطلوبة: $quantity ${product.unitAr}
            - السعر الإفرادي: ${product.price} ج.م
            - السعر الإجمالي: ${product.price * quantity} ج.م
            
            برجاء تأكيد التوافر وطريقة الاستلام أو التوصيل. شكراً لكم!
        """.trimIndent()
        openWhatsApp(context, message)
    }

    fun sendCartOrderViaWhatsApp(context: Context) {
        val state = _uiState.value
        if (state.cartItems.isEmpty()) {
            Toast.makeText(context, "سلة الطلبات فارغة حالياً", Toast.LENGTH_SHORT).show()
            return
        }

        val itemsSummary = state.cartItems.joinToString("\n") {
            "• ${it.product.nameAr} × ${it.quantity} = ${it.totalPrice} ج.م"
        }

        val message = """
            السلام عليكم يا معلم حماده رزق (محل الأدوات الصحية والسباكة) 👋
            براند: ${StoreRepository.BRAND_NAME}
            
            أرغب في تأكيد طلبية بضاعة سباكة من سلة التطبيق:
            
            📋 تفاصيل الطلبية:
            $itemsSummary
            
            💰 إجمالي الفاتورة: ${state.cartSubtotal} جنيه مصري
            (سعر الجملة التقديري: ${state.cartWholesaleTotal} ج.م)
            
            📍 برجاء مراجعة الطلب والتواصل معي لتأكيد التجهيز من المحل في قرية ساو - ديروط.
        """.trimIndent()

        openWhatsApp(context, message)
    }

    fun sendQuoteViaWhatsApp(context: Context) {
        val state = _uiState.value
        val message = """
            السلام عليكم يا معلم حماده رزق 👋
            أرغب في استشارة وحساب مقايسة سباكة لبيتي من محل الأدوات الصحية والسباكة (HAMO ALMBRMJ):
            - عدد الحمامات: ${state.quoteBathrooms}
            - عدد المطابخ: ${state.quoteKitchens}
            - مستوى التشطيب: ${state.quoteTier}
            - المقايسة التقديرية بالتطبيق: حوالي ${state.quoteEstimatedTotal} ج.م
            
            أرجو التواصل لعمل المعاينة وحساب الكميات بدقة وسعر الجملة. شكراً!
        """.trimIndent()
        openWhatsApp(context, message)
    }

    fun submitContactForm(context: Context) {
        val state = _uiState.value
        if (state.contactName.isBlank() || state.contactPhone.isBlank()) {
            Toast.makeText(context, "برجاء كتابة الاسم ورقم الهاتف للتواصل", Toast.LENGTH_SHORT).show()
            return
        }
        val message = """
            طلب تواصل واستفسار من تطبيق متجر الأدوات الصحية والسباكة (HAMO ALMBRMJ) 💬
            - الاسم: ${state.contactName}
            - الهاتف: ${state.contactPhone}
            - نوع الاستفسار: ${state.contactCategory}
            - الرسالة / الملاحظات: ${state.contactNote.ifBlank { "لا توجد ملاحظات إضافية" }}
        """.trimIndent()
        openWhatsApp(context, message)
    }

    fun getFilteredProducts(): List<Product> {
        val state = _uiState.value
        return state.products.filter { product ->
            val matchesCategory = state.selectedCategoryId == null || product.categoryId == state.selectedCategoryId
            val matchesQuery = state.searchQuery.isBlank() ||
                    product.nameAr.contains(state.searchQuery, ignoreCase = true) ||
                    product.brand.contains(state.searchQuery, ignoreCase = true) ||
                    product.descriptionAr.contains(state.searchQuery, ignoreCase = true)
            matchesCategory && matchesQuery
        }.let { list ->
            when (state.sortOption) {
                SortOption.FEATURED -> list.sortedByDescending { it.isFeatured }
                SortOption.PRICE_LOW_TO_HIGH -> list.sortedBy { it.price }
                SortOption.PRICE_HIGH_TO_LOW -> list.sortedByDescending { it.price }
                SortOption.DISCOUNT -> list.sortedByDescending { it.discountPercent }
            }
        }
    }
}
