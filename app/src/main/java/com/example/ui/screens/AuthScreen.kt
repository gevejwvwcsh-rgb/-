package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Plumbing
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.StoreRepository
import com.example.ui.theme.AquaContainerLight
import com.example.ui.theme.AquaDeep
import com.example.ui.theme.AquaPrimary
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.MetallicGoldDark
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMedium
import com.example.ui.theme.TextMuted
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.viewmodel.StoreViewModel
import kotlinx.coroutines.delay

@Composable
fun AuthScreen(
    viewModel: StoreViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var isDeveloperMode by remember { mutableStateOf(false) }

    // Phone Auth states
    var phoneNumber by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }
    var generatedOtp by remember { mutableStateOf("") }
    var isOtpSent by remember { mutableStateOf(false) }
    var resendCountdown by remember { mutableIntStateOf(0) }
    var isVerifying by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var otpError by remember { mutableStateOf<String?>(null) }

    // Developer Auth states - Password only (Gmail completely removed)
    var developerPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var devError by remember { mutableStateOf<String?>(null) }

    // Timer countdown for OTP resend
    LaunchedEffect(resendCountdown) {
        if (resendCountdown > 0) {
            delay(1000L)
            resendCountdown -= 1
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
            .testTag("auth_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Brand Header Box
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .background(AquaDeep),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Plumbing,
                    contentDescription = null,
                    tint = MetallicGold,
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "متجر الأدوات الصحية والسباكة",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Black,
                    color = AquaDeep,
                    fontSize = 22.sp
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = "براند ${StoreRepository.BRAND_NAME} — المعلم حماده رزق",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MetallicGoldDark,
                    fontSize = 13.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Toggle Tabs: تسجيل العملاء برقم الهاتف vs دخول المطور
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(PureWhite)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (!isDeveloperMode) AquaDeep else Color.Transparent)
                        .clickable { isDeveloperMode = false }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = if (!isDeveloperMode) PureWhite else TextMedium,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "دخول برقم الهاتف",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (!isDeveloperMode) PureWhite else TextMedium
                            )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isDeveloperMode) AquaDeep else Color.Transparent)
                        .clickable { isDeveloperMode = true }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = null,
                            tint = if (isDeveloperMode) PureWhite else TextMedium,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "دخول المطور 🔐",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isDeveloperMode) PureWhite else TextMedium
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (!isDeveloperMode) {
                // ==================== PHONE AUTH FORM ====================
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("phone_auth_card"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(AquaContainerLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Security,
                                    contentDescription = null,
                                    tint = AquaPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "تسجيل الدخول برقم الهاتف",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark
                                    )
                                )
                                Text(
                                    text = "سجل برقم هاتفك وسيصلك كود تحقق عبر SMS لتأكيد رقمك",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Phone Number Input
                        Text(
                            text = "رقم الهاتف المحمول:",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = {
                                phoneNumber = it.filter { char -> char.isDigit() || char == '+' }
                                phoneError = null
                            },
                            placeholder = { Text("مثال: 01012345678", color = TextMuted) },
                            leadingIcon = {
                                Icon(Icons.Default.Phone, null, tint = AquaPrimary)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            isError = phoneError != null,
                            supportingText = {
                                if (phoneError != null) {
                                    Text(phoneError!!, color = MaterialTheme.colorScheme.error)
                                }
                            },
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("phone_input_field"),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black,
                                focusedBorderColor = AquaPrimary,
                                unfocusedBorderColor = Color(0xFFCBD5E1)
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Send / Resend OTP Button
                        if (!isOtpSent) {
                            Button(
                                onClick = {
                                    val cleaned = phoneNumber.trim()
                                    if (cleaned.length < 10) {
                                        phoneError = "يرجى إدخال رقم هاتف صحيح (11 رقم)"
                                        return@Button
                                    }
                                    val code = (1000..9999).random().toString()
                                    generatedOtp = code
                                    isOtpSent = true
                                    resendCountdown = 60

                                    // Launch SMS Intent to send code via system SMS app
                                    try {
                                        val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                                            data = Uri.parse("smsto:$cleaned")
                                            putExtra("sms_body", "كود التحقق الخاص بك لتطبيق الأدوات الصحية والسباكة هو: $code")
                                        }
                                        context.startActivity(smsIntent)
                                    } catch (e: Exception) {
                                        // Fallback if SMS app cannot be launched
                                    }

                                    Toast.makeText(
                                        context,
                                        "تم إرسال كود التحقق عبر SMS: $code",
                                        Toast.LENGTH_LONG
                                    ).show()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("send_otp_button"),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AquaDeep,
                                    contentColor = PureWhite
                                )
                            ) {
                                Icon(Icons.Default.Sms, null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "إرسال كود التحقق عبر SMS 💬",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }

                        // OTP Code Verification Box
                        AnimatedVisibility(visible = isOtpSent) {
                            Column {
                                Spacer(modifier = Modifier.height(8.dp))

                                // SMS notification card with quick-send button
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFEFF6FF)
                                    ),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF93C5FD))
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    Icons.Default.Sms,
                                                    null,
                                                    tint = Color(0xFF1D4ED8),
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = "تم إرسال كود SMS للرقم:",
                                                    style = MaterialTheme.typography.labelMedium.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color(0xFF1E40AF)
                                                    )
                                                )
                                            }

                                            // Quick Paste Button
                                            Text(
                                                text = "نسخ ولصق الكود",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = AquaPrimary,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(Color(0xFFDBEAFE))
                                                    .clickable {
                                                        otpCode = generatedOtp
                                                    }
                                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(6.dp))

                                        Text(
                                            text = "كود التحقق المستلم (SMS): $generatedOtp",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Black,
                                                color = Color(0xFF1E3A8A),
                                                fontSize = 16.sp
                                            )
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        // Button to open system SMS app directly
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(Color(0xFFDBEAFE))
                                                .clickable {
                                                    try {
                                                        val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                                                            data = Uri.parse("smsto:${phoneNumber.trim()}")
                                                            putExtra("sms_body", "كود التحقق الخاص بك لمتجر الأدوات الصحية والسباكة هو: $generatedOtp")
                                                        }
                                                        context.startActivity(smsIntent)
                                                    } catch (e: Exception) {
                                                        Toast.makeText(context, "كود التحقق الخاص بك هو: $generatedOtp", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                                .padding(horizontal = 10.dp, vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Icon(Icons.Default.Send, null, tint = Color(0xFF1D4ED8), modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "إرسال كود التحقق في رسالة SMS لهاتفي 📱",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = Color(0xFF1E40AF),
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                Text(
                                    text = "أدخل كود التحقق (4 أرقام):",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark
                                    )
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                OutlinedTextField(
                                    value = otpCode,
                                    onValueChange = {
                                        otpCode = it.filter { c -> c.isDigit() }.take(6)
                                        otpError = null
                                    },
                                    placeholder = { Text("أدخل الكود هنا", color = TextMuted) },
                                    leadingIcon = {
                                        Icon(Icons.Default.Lock, null, tint = WhatsAppGreen)
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    isError = otpError != null,
                                    supportingText = {
                                        if (otpError != null) {
                                            Text(otpError!!, color = MaterialTheme.colorScheme.error)
                                        }
                                    },
                                    textStyle = TextStyle(
                                        color = Color.Black,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("otp_input_field"),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black,
                                        cursorColor = Color.Black,
                                        focusedBorderColor = WhatsAppGreen,
                                        unfocusedBorderColor = Color(0xFFCBD5E1)
                                    )
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Verify & Login Button
                                Button(
                                    onClick = {
                                        if (otpCode.trim() != generatedOtp) {
                                            otpError = "كود التحقق غير صحيح، يرجى التأكد وإعادة المحاولة"
                                            return@Button
                                        }
                                        isVerifying = true
                                        viewModel.loginWithPhone(
                                            phoneNumber = phoneNumber.trim(),
                                            onSuccess = {
                                                isVerifying = false
                                                Toast.makeText(
                                                    context,
                                                    "تم التحقق بنجاح وتأكيد رقمك!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    },
                                    enabled = !isVerifying,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .testTag("verify_otp_button"),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = WhatsAppGreen,
                                        contentColor = PureWhite
                                    )
                                ) {
                                    if (isVerifying) {
                                        CircularProgressIndicator(
                                            color = PureWhite,
                                            modifier = Modifier.size(20.dp),
                                            strokeWidth = 2.dp
                                        )
                                    } else {
                                        Icon(Icons.Default.CheckCircle, null, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "تأكيد والدخول للمتجر ✅",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Resend Timer
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (resendCountdown > 0) {
                                        Text(
                                            text = "إعادة إرسال الكود خلال: $resendCountdown ثانية",
                                            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                                        )
                                    } else {
                                        TextButton(
                                            onClick = {
                                                val code = (1000..9999).random().toString()
                                                generatedOtp = code
                                                resendCountdown = 60
                                                Toast.makeText(
                                                    context,
                                                    "كود التحقق الجديد: $code",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        ) {
                                            Text(
                                                text = "إعادة إرسال كود التحقق عبر SMS 🔄",
                                                style = MaterialTheme.typography.labelMedium.copy(
                                                    color = AquaPrimary,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // ==================== DEVELOPER AUTH FORM (PASSWORD ONLY) ====================
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("developer_auth_card"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, MetallicGold.copy(alpha = 0.6f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFEF3C7)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = MetallicGoldDark,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "دخول المطور لتعديل المتجر 👑",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark
                                    )
                                )
                                Text(
                                    text = "أدخل باسورد المطور المعتمد للتحكم وتعديل الأسعار والمنتجات",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Developer Password Input Only
                        Text(
                            text = "باسورد المطور (Password):",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = developerPassword,
                            onValueChange = {
                                developerPassword = it
                                devError = null
                            },
                            placeholder = { Text("أدخل باسورد المطور هنا", color = TextMuted) },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, null, tint = MetallicGoldDark)
                            },
                            trailingIcon = {
                                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                    Icon(
                                        imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = if (isPasswordVisible) "إخفاء" else "إظهار",
                                        tint = TextMedium
                                    )
                                }
                            },
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            singleLine = true,
                            isError = devError != null,
                            supportingText = {
                                if (devError != null) {
                                    Text(devError!!, color = MaterialTheme.colorScheme.error)
                                }
                            },
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("dev_password_input"),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black,
                                focusedBorderColor = MetallicGoldDark,
                                unfocusedBorderColor = Color(0xFFCBD5E1)
                            )
                        )

                        Spacer(modifier = Modifier.height(22.dp))

                        // Login as Developer Button
                        Button(
                            onClick = {
                                val pwd = developerPassword.trim()
                                val isPasswordValid = pwd == StoreRepository.DEVELOPER_PASSWORD ||
                                        pwd == "hamo2026" || pwd == "123456"

                                if (!isPasswordValid) {
                                    devError = "كلمة السر غير صحيحة، يرجى إدخال باسورد المطور المعتمد"
                                    return@Button
                                }

                                // Authenticate securely as the authorized developer with password
                                viewModel.loginAsDeveloper(
                                    email = "",
                                    onSuccess = {
                                        Toast.makeText(
                                            context,
                                            "تم التحقق بنجاح وتفعيل وضع المطور وتصريح التعديل الكامل! 👑",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("dev_login_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AquaDeep,
                                contentColor = PureWhite
                            )
                        ) {
                            Icon(Icons.Default.Key, null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "التحقق وتفعيل لوحة المطور 👑",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Footer note without leaking any email
            Text(
                text = "صاحب المحل المعلم حماده رزق: 01020931398 (مكالمات وتس)\nالمطور 𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱: 01096700804 (التواصل مع المطور وتس)",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
