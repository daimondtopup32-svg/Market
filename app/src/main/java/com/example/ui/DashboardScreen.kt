package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.theme.*

@Composable
fun DashboardScreen(viewModel: MarketplaceViewModel) {
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            "Seller Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        // Bento Grid Layout
        Row(
            modifier = Modifier.fillMaxWidth().height(160.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Earnings Tile
            BentoTile(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                backgroundColor = BentoPurpleContainer,
                onColor = BentoOnPurpleContainer,
                label = "Earnings",
                value = "$45,280",
                icon = Icons.Default.Payments
            )
            
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // KYC Tile
                BentoTileSmall(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    backgroundColor = BentoBlueContainer,
                    onColor = BentoOnBlueContainer,
                    label = "KYC Verified",
                    icon = Icons.Default.VerifiedUser
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Security Tile
                    BentoTileIcon(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        backgroundColor = BentoPinkContainer,
                        onColor = BentoOnPinkContainer,
                        icon = Icons.Default.Shield
                    )
                    // Analytics Tile
                    BentoTileIcon(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        backgroundColor = Color.White,
                        onColor = Color(0xFF49454F),
                        icon = Icons.Default.Analytics
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Unresolved Disputes Tile (Full width)
        Card(
            modifier = Modifier.fillMaxWidth().height(100.dp),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = BentoDarkContainer)
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Unresolved", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.7f))
                    Text("2 Disputes", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Icon(Icons.Default.SupportAgent, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("Your Listings", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        
        Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.ListAlt, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.outline)
                Text("No active listings yet", color = MaterialTheme.colorScheme.outline)
                Button(
                    onClick = {},
                    modifier = Modifier.padding(top = 16.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text("Create New Listing")
                }
            }
        }
    }
}

@Composable
fun BentoTile(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    onColor: Color,
    label: String,
    value: String,
    icon: ImageVector
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, contentDescription = null, tint = onColor, modifier = Modifier.size(28.dp))
            Column {
                Text(label.uppercase(), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium, color = onColor.copy(alpha = 0.7f))
                Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = onColor)
            }
        }
    }
}

@Composable
fun BentoTileSmall(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    onColor: Color,
    label: String,
    icon: ImageVector
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                color = onColor,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(24.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                }
            }
            Text(label, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = onColor)
        }
    }
}

@Composable
fun BentoTileIcon(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    onColor: Color,
    icon: ImageVector
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = if (backgroundColor == Color.White) CardDefaults.outlinedCardBorder() else null
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = onColor, modifier = Modifier.size(24.dp))
        }
    }
}
