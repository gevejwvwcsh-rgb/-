package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.CustomerReview
import com.example.data.model.Product
import com.example.data.model.StoreFeatureItem
import com.example.data.repository.StoreRepository
import com.example.ui.components.CategoryCard
import com.example.ui.components.ProductCard
import com.example.ui.components.StoreFooter
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
import com.example.ui.viewmodel.NavigationTab
import com.example.ui.viewmodel.StoreViewModel

@Composable
fun HomeScreen(
    viewModel: StoreViewModel,
    onNavigateToProducts: (categoryId: String?) -> Unit,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val featuredProducts = uiState.products.filter { it.isFeatured }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SlateBackgroundLight)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // 1. HERO SECTION
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .testTag("home_hero_section")
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_hero_banner),
                    contentDescription = "معرض الأدوات الصحية والسباكة",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Dark gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.4f),
                                    Color.Black.copy(alpha = 0.85f)
                                )
                            )
                        )
                )

                // Hero Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    // Brand Badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MetallicGold)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "البراند الرسمي: ${StoreRepository.BRAND_NAME}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "متجر الأدوات الصحية والسباكة",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = PureWhite,
                            fontWeight = FontWeight.Black,
                            fontSize = 24.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "مستلزمات السباكة وتأسيس وتشطيب الفيلات والمنازل — جملة وقطاعي مع المعلم حماده رزق",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = PureWhite.copy(alpha = 0.9f),
                            lineHeight = 20.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Hero Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { onNavigateToProducts(null) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("hero_shop_now_btn"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AquaPrimary,
                                contentColor = PureWhite
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "تسوق الآن",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Button(
                            onClick = {
                                viewModel.openWhatsApp(
                                    context,
                                    "مرحباً متجر السباكة والأدوات الصحية (HAMO ALMBRMJ)، أرغب في الاستفسار عن عروض الأسعار"
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("hero_whatsapp_btn"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = WhatsAppGreen,
                                contentColor = PureWhite
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "تواصل واتساب",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }

        // 2. FEATURES STRIP (شريط المميزات)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .testTag("features_strip"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackgroundLight),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StoreRepository.features.forEach { feature ->
                        FeatureRowItem(feature)
                    }
                }
            }
        }

        // 3. 8 CATEGORIES SECTION (8 أقسام رئيسية)
        item {
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(
                title = "أقسام الأدوات الصحية والسباكة (8 أقسام)",
                subtitle = "كل ما يلزم التأسيس والتشطيب من الألف إلى الياء",
                onSeeAllClick = { onNavigateToProducts(null) }
            )

            // Grid of categories (displayed in pairs for balanced mobile layout)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StoreRepository.categories.chunked(2).forEach { rowCategories ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowCategories.forEach { cat ->
                            Box(modifier = Modifier.weight(1f)) {
                                CategoryCard(
                                    category = cat,
                                    onClick = { onNavigateToProducts(cat.id) }
                                )
                            }
                        }
                        if (rowCategories.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        // 4. FEATURED PRODUCTS (منتجات مميزة 6-8 كروت)
        item {
            Spacer(modifier = Modifier.height(28.dp))
            SectionHeader(
                title = "منتجات مميزة وعروض خاصة",
                subtitle = "أعلى الخامات جودة وضماناً — بأسعار الجملة والقطاعي",
                onSeeAllClick = { onNavigateToProducts(null) }
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(featuredProducts) { product ->
                    ProductCard(
                        product = product,
                        viewModel = viewModel,
                        onClick = { onProductClick(product) },
                        modifier = Modifier.width(220.dp)
                    )
                }
            }
        }

        // 5. "ليه تختارنا؟" (Why choose us)
        item {
            Spacer(modifier = Modifier.height(28.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = AquaDeep),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = MetallicGold,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "ليه تختار متجرنا؟",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = PureWhite
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "الريادة في تجارة الأدوات الصحية ومستلزمات السباكة بمحافظة أسيوط",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = PureWhite.copy(alpha = 0.85f)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val reasons = listOf(
                        "أسعار جملة حقيقية للمقاولين وأصحاب العقارات مع خصومات للكميات.",
                        "بضاعة أصلية معتمدة من كبرى المصانع والشركات العالمية بضمان 5 إلى 10 سنوات.",
                        "إمكانية الاسترجاع والاستبدال الفوري في حالة وجود أي عيب مصنعي بدون تعقيد.",
                        "المعلم حماده رزق يقدم لك استشارة فنية دقيقة لحساب أمتار المواسير وقطع التأسيس مجاناً."
                    )

                    reasons.forEach { reason ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .background(MetallicGold),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = AquaDeep,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = reason,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = PureWhite,
                                    lineHeight = 22.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        // 6. CUSTOMER REVIEWS (آراء العملاء)
        item {
            Spacer(modifier = Modifier.height(28.dp))
            SectionHeader(
                title = "آراء عملائنا الكرام",
                subtitle = "ثقة متوارثة مع سباكين ومقاولي ديروط ومحافظة أسيوط"
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(StoreRepository.reviews) { review ->
                    ReviewCard(review = review, modifier = Modifier.width(280.dp))
                }
            }
        }

        // 7. SHOWROOM GALLERY (معرض صور المحل)
        item {
            Spacer(modifier = Modifier.height(28.dp))
            SectionHeader(
                title = "معرض صور المحل والمخازن",
                subtitle = "جولة مصورة داخل أجنحة المعرض والمخازن"
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(StoreRepository.showroomGallery) { item ->
                    Card(
                        modifier = Modifier
                            .width(260.dp)
                            .border(1.dp, BorderLight, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardBackgroundLight),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column {
                            Image(
                                painter = painterResource(id = item.imageRes),
                                contentDescription = item.titleAr,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentScale = ContentScale.Crop
                            )
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = item.titleAr,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.descriptionAr,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = TextMuted,
                                        fontSize = 11.sp,
                                        lineHeight = 15.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // 8. STORE LOCATION CARD (العنوان وطريقة الوصول)
        item {
            Spacer(modifier = Modifier.height(28.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, MetallicGold.copy(alpha = 0.5f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(AquaContainerLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = AquaPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "مقر المحل في قرية ساو — ديروط",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                            )
                            Text(
                                text = "المعلم حماده رزق يرحب بكم",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = AquaPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "العنوان بالتفصيل: [ محافظة اسيوط مركز ديروط قريه ساو انزل وسال قول عايز محل المعلم حماده رزق محل السباكه ]",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextMedium,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "مواعيد العمل: يوميًا 9:00 ص – 11:00 م (يوم الجمعة يفتح من بعد الظهر حتى 11:00 م)",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextMuted,
                            lineHeight = 18.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // صاحب المحل
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(AquaContainerLight)
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "صاحب المحل المعلم حماده رزق رقم الفون 01020931398 مكلمات وتس",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = AquaDeep,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // المطور
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFFEF3C7))
                            .clickable { viewModel.contactDeveloperWhatsApp(context) }
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "رقم المطور 𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱 01096700804 (التواصل مع المطور وتس)",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF78350F),
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }

        // 9. FOOTER
        item {
            Spacer(modifier = Modifier.height(32.dp))
            StoreFooter(viewModel = viewModel)
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String,
    onSeeAllClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    fontSize = 17.sp
                )
            )

            if (onSeeAllClick != null) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { onSeeAllClick() }
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "عرض الكل",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = AquaPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = AquaPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall.copy(
                color = TextMuted,
                fontSize = 12.sp
            )
        )
    }
}

@Composable
private fun FeatureRowItem(feature: StoreFeatureItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(AquaContainerLight),
            contentAlignment = Alignment.Center
        ) {
            val icon = when (feature.iconType) {
                "price" -> Icons.Default.MonetizationOn
                "verified" -> Icons.Default.Verified
                "shipping" -> Icons.Default.LocalShipping
                else -> Icons.Default.CheckCircle
            }
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AquaPrimary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = feature.titleAr,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            )
            Text(
                text = feature.descriptionAr,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextMuted,
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Composable
private fun ReviewCard(
    review: CustomerReview,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(1.dp, BorderLight, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackgroundLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = review.customerName,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                    )
                    Text(
                        text = review.roleOrLocation,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = AquaPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Row {
                    repeat(review.rating) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = MetallicGold,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "\"${review.comment}\"",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextMedium,
                    lineHeight = 20.sp,
                    fontSize = 13.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = review.dateAr,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextMuted,
                    fontSize = 10.sp
                )
            )
        }
    }
}
