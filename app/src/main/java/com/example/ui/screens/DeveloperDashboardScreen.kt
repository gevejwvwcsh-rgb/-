package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Product
import com.example.data.repository.StoreRepository
import com.example.ui.theme.AquaContainerLight
import com.example.ui.theme.AquaDeep
import com.example.ui.theme.AquaPrimary
import com.example.ui.theme.BorderLight
import com.example.ui.theme.CardBackgroundLight
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.MetallicGoldDark
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SlateBackgroundLight
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMedium
import com.example.ui.theme.TextMuted
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.viewmodel.StoreViewModel

enum class DevTab {
    PRICES,
    ADD_PRODUCT,
    STORE_INFO
}

@Composable
fun DeveloperDashboardScreen(
    viewModel: StoreViewModel,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableStateOf(DevTab.PRICES) }

    // Dialog state for editing a specific product
    var editingProduct by remember { mutableStateOf<Product?>(null) }
    var editPriceStr by remember { mutableStateOf("") }
    var editWholesalePriceStr by remember { mutableStateOf("") }

    // New product states
    var newNameAr by remember { mutableStateOf("") }
    var newBrand by remember { mutableStateOf("للأدوات الصحية والسباكة") }
    var newCategoryId by remember { mutableStateOf(StoreRepository.categories.firstOrNull()?.id ?: "cat_pipes") }
    var newPriceStr by remember { mutableStateOf("") }
    var newWholesaleStr by remember { mutableStateOf("") }
    var newUnitAr by remember { mutableStateOf("قطعة") }
    var newDescAr by remember { mutableStateOf("") }

    // Store config states
    var editStorePhone by remember { mutableStateOf(StoreRepository.STORE_PHONE) }
    var editDevPhone by remember { mutableStateOf(StoreRepository.DEVELOPER_PHONE) }
    var editAddress by remember { mutableStateOf(StoreRepository.STORE_ADDRESS) }
    var editHours by remember { mutableStateOf(StoreRepository.STORE_HOURS) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SlateBackgroundLight)
            .testTag("developer_dashboard_screen")
    ) {
        // Developer Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AquaDeep)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "رجوع", tint = PureWhite)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "لوحة تحكم وتعديل المطور 👑",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = MetallicGold
                                )
                            )
                        }
                        Text(
                            text = "حساب المطور المعتمد 👑 (صلاحيات كاملة)",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = PureWhite.copy(alpha = 0.85f)
                            )
                        )
                    }
                }

                Button(
                    onClick = {
                        viewModel.logout()
                        Toast.makeText(context, "تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PureWhite.copy(alpha = 0.2f),
                        contentColor = PureWhite
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.ExitToApp, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("خروج", fontSize = 12.sp)
                }
            }
        }

        // Mode Notice Badge
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFEF3C7))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "وضع التعديل والإدارة الحر للمطور فقت — كافة التعديلات تحفظ فوراً",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color(0xFF78350F),
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        // Tab Row
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = PureWhite,
            contentColor = AquaDeep,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
                    color = AquaDeep,
                    height = 3.dp
                )
            }
        ) {
            Tab(
                selected = selectedTab == DevTab.PRICES,
                onClick = { selectedTab = DevTab.PRICES },
                text = {
                    Text(
                        "تعديل الأسعار",
                        fontWeight = if (selectedTab == DevTab.PRICES) FontWeight.Bold else FontWeight.Normal
                    )
                },
                icon = { Icon(Icons.Default.PriceCheck, null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTab == DevTab.ADD_PRODUCT,
                onClick = { selectedTab = DevTab.ADD_PRODUCT },
                text = {
                    Text(
                        "إضافة منتج",
                        fontWeight = if (selectedTab == DevTab.ADD_PRODUCT) FontWeight.Bold else FontWeight.Normal
                    )
                },
                icon = { Icon(Icons.Default.Add, null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTab == DevTab.STORE_INFO,
                onClick = { selectedTab = DevTab.STORE_INFO },
                text = {
                    Text(
                        "بيانات المحل",
                        fontWeight = if (selectedTab == DevTab.STORE_INFO) FontWeight.Bold else FontWeight.Normal
                    )
                },
                icon = { Icon(Icons.Default.Settings, null, modifier = Modifier.size(18.dp)) }
            )
        }

        // Tab Contents
        when (selectedTab) {
            DevTab.PRICES -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Text(
                            text = "قائمة منتجات المتجر (اضغط على أي صنف لتعديل سعره):",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    items(uiState.products) { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    editingProduct = product
                                    editPriceStr = product.price.toString()
                                    editWholesalePriceStr = product.wholesalePrice.toString()
                                },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = PureWhite),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = product.nameAr,
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = TextDark
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "الماركة: ${product.brand} | ${product.unitAr}",
                                        style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row {
                                        Text(
                                            text = "قطاعي: ${product.price} ج",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                color = AquaDeep,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = "جملة: ${product.wholesalePrice} ج",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                color = MetallicGoldDark,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = {
                                            editingProduct = product
                                            editPriceStr = product.price.toString()
                                            editWholesalePriceStr = product.wholesalePrice.toString()
                                        }
                                    ) {
                                        Icon(Icons.Default.Edit, null, tint = AquaPrimary)
                                    }

                                    IconButton(
                                        onClick = {
                                            viewModel.deleteProduct(product.id)
                                            Toast.makeText(context, "تم حذف الصنف من الكتالوج", Toast.LENGTH_SHORT).show()
                                        }
                                    ) {
                                        Icon(Icons.Default.Delete, null, tint = Color(0xFFEF4444))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            DevTab.ADD_PRODUCT -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = "إضافة صنف / منتج جديد للكتالوج:",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    item {
                        OutlinedTextField(
                            value = newNameAr,
                            onValueChange = { newNameAr = it },
                            label = { Text("اسم المنتج بالعربي") },
                            placeholder = { Text("مثال: خلاط مطبخ سوستة تركي أصلي") },
                            textStyle = TextStyle(color = Color.Black),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = newBrand,
                            onValueChange = { newBrand = it },
                            label = { Text("الماركة / الشركة") },
                            placeholder = { Text("مثال: تركي مستورد / الشريف / باناسونيك") },
                            textStyle = TextStyle(color = Color.Black),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedTextField(
                                value = newPriceStr,
                                onValueChange = { newPriceStr = it.filter { c -> c.isDigit() || c == '.' } },
                                label = { Text("سعر القطاعي (ج.م)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                textStyle = TextStyle(color = Color.Black),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    cursorColor = Color.Black
                                ),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )

                            OutlinedTextField(
                                value = newWholesaleStr,
                                onValueChange = { newWholesaleStr = it.filter { c -> c.isDigit() || c == '.' } },
                                label = { Text("سعر الجملة (ج.م)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                textStyle = TextStyle(color = Color.Black),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    cursorColor = Color.Black
                                ),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = newUnitAr,
                            onValueChange = { newUnitAr = it },
                            label = { Text("الوحدة") },
                            placeholder = { Text("مثال: قطعة / عود 4 متر / كرتونة") },
                            textStyle = TextStyle(color = Color.Black),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = newDescAr,
                            onValueChange = { newDescAr = it },
                            label = { Text("وصف ومواصفات المنتج") },
                            placeholder = { Text("اكتب تفاصيل وضمان ومقاسات المنتج...") },
                            textStyle = TextStyle(color = Color.Black),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3,
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    item {
                        Button(
                            onClick = {
                                val price = newPriceStr.toDoubleOrNull() ?: 0.0
                                val wholesalePrice = newWholesaleStr.toDoubleOrNull() ?: price
                                if (newNameAr.isBlank() || price <= 0) {
                                    Toast.makeText(context, "يرجى كتابة اسم المنتج وسعر صحيح", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                viewModel.addNewProduct(
                                    nameAr = newNameAr.trim(),
                                    brand = newBrand.trim(),
                                    categoryId = newCategoryId,
                                    price = price,
                                    wholesalePrice = wholesalePrice,
                                    unitAr = newUnitAr.trim(),
                                    descriptionAr = newDescAr.trim()
                                )

                                Toast.makeText(context, "تمت إضافة المنتج بنجاح إلى المتجر! ✅", Toast.LENGTH_SHORT).show()
                                newNameAr = ""
                                newPriceStr = ""
                                newWholesaleStr = ""
                                newDescAr = ""
                                selectedTab = DevTab.PRICES
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AquaDeep,
                                contentColor = PureWhite
                            )
                        ) {
                            Icon(Icons.Default.Add, null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("حفظ وإضافة الصنف للمتجر", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            DevTab.STORE_INFO -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = "تعديل بيانات وأرقام المتجر والمطور:",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    item {
                        OutlinedTextField(
                            value = editStorePhone,
                            onValueChange = { editStorePhone = it },
                            label = { Text("رقم هاتف صاحب المحل (مكالمات وتس)") },
                            textStyle = TextStyle(color = Color.Black),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = editDevPhone,
                            onValueChange = { editDevPhone = it },
                            label = { Text("رقم المطور 𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱 (واتس)") },
                            textStyle = TextStyle(color = Color.Black),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = editAddress,
                            onValueChange = { editAddress = it },
                            label = { Text("عنوان المحل بالتفصيل") },
                            textStyle = TextStyle(color = Color.Black),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2,
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = editHours,
                            onValueChange = { editHours = it },
                            label = { Text("مواعيد العمل الرسمية") },
                            textStyle = TextStyle(color = Color.Black),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    item {
                        Button(
                            onClick = {
                                viewModel.updateStoreConfig(
                                    phone = editStorePhone.trim(),
                                    devPhone = editDevPhone.trim(),
                                    address = editAddress.trim(),
                                    hours = editHours.trim()
                                )
                                Toast.makeText(context, "تم حفظ بيانات المحل والمطور بنجاح! ✅", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen)
                        ) {
                            Icon(Icons.Default.Check, null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("حفظ التعديلات", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Edit Product Price Dialog
    if (editingProduct != null) {
        val prod = editingProduct!!
        androidx.compose.ui.window.Dialog(
            onDismissRequest = { editingProduct = null }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "تعديل سعر المنتج",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = prod.nameAr,
                        style = MaterialTheme.typography.bodyMedium.copy(color = AquaDeep)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = editPriceStr,
                        onValueChange = { editPriceStr = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("سعر القطاعي الجديد (ج.م)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        textStyle = TextStyle(color = Color.Black),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            cursorColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = editWholesalePriceStr,
                        onValueChange = { editWholesalePriceStr = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("سعر الجملة الجديد (ج.م)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        textStyle = TextStyle(color = Color.Black),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            cursorColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextButton(
                            onClick = { editingProduct = null },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("إلغاء")
                        }

                        Button(
                            onClick = {
                                val newP = editPriceStr.toDoubleOrNull() ?: prod.price
                                val newW = editWholesalePriceStr.toDoubleOrNull() ?: prod.wholesalePrice

                                viewModel.updateProductPrices(prod.id, newP, newW)
                                Toast.makeText(context, "تم تحديث السعر بنجاح! ✅", Toast.LENGTH_SHORT).show()
                                editingProduct = null
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = AquaDeep)
                        ) {
                            Text("حفظ السعر")
                        }
                    }
                }
            }
        }
    }
}
