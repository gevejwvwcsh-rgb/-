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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.repository.StoreRepository
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
import com.example.ui.viewmodel.StoreViewModel

@Composable
fun ContactScreen(
    viewModel: StoreViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SlateBackgroundLight)
            .testTag("contact_screen"),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // 1. Direct Contact Cards Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AquaDeep)
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "بيانات التواصل الرسمية",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "صاحب المحل المعلم حماده رزق & المطور ${StoreRepository.BRAND_NAME}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = PureWhite.copy(alpha = 0.85f)
                        )
                    )
                }
            }
        }

        // 2. Clickable Cards for Store Owner and Developer
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // بطاقة صاحب المحل: المعلم حماده رزق
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("owner_info_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackgroundLight),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, MetallicGold.copy(alpha = 0.6f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(AquaContainerLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = AquaPrimary,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Column {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(MetallicGold.copy(alpha = 0.2f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "صاحب المحل",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = MetallicGoldDark,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "صاحب المحل المعلم حماده رزق",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark
                                    )
                                )
                                Text(
                                    text = "رقم الفون 01020931398 مكلمات وتس",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = AquaDeep,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { viewModel.openCall(context, StoreRepository.OWNER_PHONE) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("owner_call_btn"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AquaPrimary,
                                    contentColor = PureWhite
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Call, null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("اتصال الآن", fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = { viewModel.contactOwnerWhatsApp(context) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("owner_whatsapp_btn"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = WhatsAppGreen,
                                    contentColor = PureWhite
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Chat, null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("واتس صاحب المحل", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // بطاقة المطور: 𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("developer_info_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackgroundLight),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, AquaPrimary.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFEF3C7)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Chat,
                                    contentDescription = null,
                                    tint = MetallicGoldDark,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Column {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(AquaContainerLight)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "مطور التطبيق",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = AquaDeep,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "رقم المطور 𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱 01096700804",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark
                                    )
                                )
                                Text(
                                    text = "التواصل مع المطور وتس 01096700804",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color(0xFF166534),
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = { viewModel.contactDeveloperWhatsApp(context) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("developer_whatsapp_btn"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = WhatsAppGreen,
                                contentColor = PureWhite
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Chat, null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "التواصل مع المطور وتس 01096700804",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }

                // Developer Dashboard Entry Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.toggleDeveloperDashboard(true) }
                        .testTag("contact_developer_dashboard_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = AquaDeep),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AdminPanelSettings, null, tint = MetallicGold, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "لوحة تحكم وتعديل المطور 👑",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        color = PureWhite,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "مخصص للمطور المعتمد لتعديل الأسعار والمنتجات",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = PureWhite.copy(alpha = 0.8f)
                                    )
                                )
                            }
                        }
                        Text(
                            text = "فتح ❯",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MetallicGold,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                // Address & How to reach
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackgroundLight),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
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
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = AquaPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                            Text(
                                text = "العنوان وطريقة الوصول بالتفصيل:",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(AquaContainerLight)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = StoreRepository.STORE_ADDRESS,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = AquaDeep,
                                    lineHeight = 22.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = MetallicGoldDark,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(top = 2.dp)
                            )
                            Text(
                                text = "مواعيد العمل: " + StoreRepository.STORE_HOURS,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextMedium,
                                    lineHeight = 18.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        // 3. Quick Quote Estimator (حاسبة مقايسة السباكة)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, MetallicGold.copy(alpha = 0.4f))
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
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = null,
                            tint = AquaPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "حاسبة المقايسة التقديرية للسباكة",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                        )
                    }

                    Text(
                        text = "احسب تكلفة بضاعة التأسيس والتشطيب لشقتك أو فيلتك تقريبياً",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Bathrooms count
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "عدد الحمامات:",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextDark,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { viewModel.setQuoteBathrooms(uiState.quoteBathrooms - 1) },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(SlateBackgroundLight)
                            ) {
                                Icon(Icons.Default.Remove, null, modifier = Modifier.size(16.dp))
                            }
                            Text(
                                text = uiState.quoteBathrooms.toString(),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                            IconButton(
                                onClick = { viewModel.setQuoteBathrooms(uiState.quoteBathrooms + 1) },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(SlateBackgroundLight)
                            ) {
                                Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Kitchens count
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "عدد المطابخ:",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextDark,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { viewModel.setQuoteKitchens(uiState.quoteKitchens - 1) },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(SlateBackgroundLight)
                            ) {
                                Icon(Icons.Default.Remove, null, modifier = Modifier.size(16.dp))
                            }
                            Text(
                                text = uiState.quoteKitchens.toString(),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                            IconButton(
                                onClick = { viewModel.setQuoteKitchens(uiState.quoteKitchens + 1) },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(SlateBackgroundLight)
                            ) {
                                Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Tier selector
                    Text(
                        text = "مستوى الخامات والتشطيب:",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("اقتصادي معتمد", "سوبر لوكس", "فاخر VIP").forEach { tier ->
                            val isSelected = uiState.quoteTier == tier
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) AquaPrimary else SlateBackgroundLight)
                                    .clickable { viewModel.setQuoteTier(tier) }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = tier,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isSelected) PureWhite else TextDark,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Total estimate display & WhatsApp button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(AquaDeep)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "التكلفة التقديرية للبضاعة:",
                                style = MaterialTheme.typography.labelSmall.copy(color = PureWhite.copy(alpha = 0.8f))
                            )
                            Text(
                                text = "حوالي ${uiState.quoteEstimatedTotal} ج.م",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MetallicGold,
                                    fontWeight = FontWeight.Black
                                )
                            )
                        }

                        Button(
                            onClick = { viewModel.sendQuoteViaWhatsApp(context) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = WhatsAppGreen,
                                contentColor = PureWhite
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("طلب المقايسة", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // 4. Contact Form (نموذج التواصل)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackgroundLight),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "نموذج الاستفسار وطلب عروض الجملة",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                    )
                    Text(
                        text = "املأ البيانات وسيتم إرسالها مباشرة إلى واتساب المعلم حماده رزق",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Name
                    OutlinedTextField(
                        value = uiState.contactName,
                        onValueChange = { viewModel.updateContactName(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("contact_name_input"),
                        label = { Text("الاسم الكامل") },
                        leadingIcon = { Icon(Icons.Default.Person, null, tint = AquaPrimary) },
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Phone
                    OutlinedTextField(
                        value = uiState.contactPhone,
                        onValueChange = { viewModel.updateContactPhone(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("contact_phone_input"),
                        label = { Text("رقم الموبايل / الواتساب") },
                        leadingIcon = { Icon(Icons.Default.Phone, null, tint = AquaPrimary) },
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Note
                    OutlinedTextField(
                        value = uiState.contactNote,
                        onValueChange = { viewModel.updateContactNote(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("contact_note_input"),
                        label = { Text("تفاصيل البضاعة المطلوبة أو الرسالة") },
                        shape = RoundedCornerShape(10.dp),
                        minLines = 3
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { viewModel.submitContactForm(context) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("contact_submit_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WhatsAppGreen,
                            contentColor = PureWhite
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "إرسال الاستفسار على واتساب",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }

        // 5. Store Footer
        item {
            Spacer(modifier = Modifier.height(24.dp))
            StoreFooter(viewModel = viewModel)
        }
    }
}
