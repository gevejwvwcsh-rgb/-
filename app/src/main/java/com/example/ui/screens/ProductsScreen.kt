package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Product
import com.example.data.repository.StoreRepository
import com.example.ui.components.ProductCard
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
import com.example.ui.theme.TextMuted
import com.example.ui.viewmodel.SortOption
import com.example.ui.viewmodel.StoreViewModel

@Composable
fun ProductsScreen(
    viewModel: StoreViewModel,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val filteredProducts = viewModel.getFilteredProducts()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SlateBackgroundLight)
            .testTag("products_screen")
    ) {
        // Search Bar & Controls Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBackgroundLight)
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            // Search Input
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("products_search_input"),
                placeholder = {
                    Text(
                        text = "ابحث عن ماسورة، خلاط، محبس، ماتور...",
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextMuted)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "بحث",
                        tint = AquaPrimary
                    )
                },
                trailingIcon = {
                    if (uiState.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "مسح البحث",
                                tint = TextMuted
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = SlateBackgroundLight,
                    unfocusedContainerColor = SlateBackgroundLight,
                    focusedIndicatorColor = AquaPrimary,
                    unfocusedIndicatorColor = BorderLight
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Wholesale toggle & Sort selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Wholesale mode toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (uiState.wholesaleMode) AquaContainerLight else Color.Transparent)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .clickable { viewModel.toggleWholesaleMode() }
                ) {
                    Switch(
                        checked = uiState.wholesaleMode,
                        onCheckedChange = { viewModel.toggleWholesaleMode() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = PureWhite,
                            checkedTrackColor = MetallicGoldDark
                        ),
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("wholesale_toggle")
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "أسعار الجملة للمقاولين",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (uiState.wholesaleMode) AquaDeep else TextDark
                        )
                    )
                }

                // Results count
                Text(
                    text = "${filteredProducts.size} منتج متاح",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TextMuted,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }

        // Horizontal Category Filter Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBackgroundLight)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // "All" chip
            item {
                FilterChip(
                    selected = uiState.selectedCategoryId == null,
                    onClick = { viewModel.selectCategory(null) },
                    label = {
                        Text(
                            text = "جميع الأقسام",
                            fontWeight = if (uiState.selectedCategoryId == null) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AquaPrimary,
                        selectedLabelColor = PureWhite,
                        containerColor = SlateBackgroundLight,
                        labelColor = TextDark
                    )
                )
            }

            // 8 categories chips
            items(StoreRepository.categories) { category ->
                val isSelected = uiState.selectedCategoryId == category.id
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        viewModel.selectCategory(if (isSelected) null else category.id)
                    },
                    label = {
                        Text(
                            text = category.nameAr,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AquaPrimary,
                        selectedLabelColor = PureWhite,
                        containerColor = SlateBackgroundLight,
                        labelColor = TextDark
                    )
                )
            }
        }

        // Sort Options Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.SwapVert,
                contentDescription = null,
                tint = TextMuted,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "الترتيب:",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextMuted,
                    fontWeight = FontWeight.Bold
                )
            )

            SortOption.values().forEach { option ->
                val isSelected = uiState.sortOption == option
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) AquaDeep else CardBackgroundLight)
                        .border(
                            1.dp,
                            if (isSelected) AquaDeep else BorderLight,
                            RoundedCornerShape(6.dp)
                        )
                        .clickable { viewModel.setSortOption(option) }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = option.titleAr,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (isSelected) PureWhite else TextDark,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 11.sp
                        )
                    )
                }
            }
        }

        // Products Grid
        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "لا توجد منتجات مطابقة للبحث",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "جرب البحث باسم آخر أو إزالة تصفيات الأقسام",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductCard(
                        product = product,
                        viewModel = viewModel,
                        wholesaleMode = uiState.wholesaleMode,
                        onClick = { onProductClick(product) }
                    )
                }
            }
        }
    }
}
