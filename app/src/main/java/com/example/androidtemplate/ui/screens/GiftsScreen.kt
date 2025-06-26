import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.ui.composables.CreditCardComposable
import com.example.androidtemplate.ui.composables.ParentStoreItemCard
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GiftsScreen(
    cardId: Long,
    cardViewModel: CardScreenViewModel,
    walletViewModel: WalletViewModel,
    nbkidsViewModel: NBKidsViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val cards by cardViewModel.cards.collectAsState()
    val card = cards.find { it.cardId == cardId }
    val wallet by walletViewModel.walletState.collectAsState()
    val storeItems by nbkidsViewModel.storeitems
    val childId = (wallet?.child?.childId ?: Long) as Long

    LaunchedEffect(cardId) {
        walletViewModel.fetchWallet(childId)
        nbkidsViewModel.fetchStoreItems(childId)
    }

    if (card == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Card not found", color = Color.Red)
        }
        return
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = card.cardHolderName ?: "No Name", fontWeight = FontWeight.Bold)
                        Text(
                            text = "(${card.accountNumber})",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.6f)
                    .align(Alignment.CenterHorizontally)
                    .shadow(10.dp, shape = RoundedCornerShape(20.dp))
            ) {
                CreditCardComposable(card)
            }

            Text(
                "Store Items",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))

            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            val allItems = storeItems

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                items(allItems) { item ->
                    val imageResId = remember(item.globalItem.photo) {
                        val resId = context.resources.getIdentifier(
                            item.globalItem.photo,
                            "drawable",
                            context.packageName
                        )
                        if (resId == 0) R.drawable.nbkidz_logo_white else resId
                    }

                    ParentStoreItemCard(
                        item = item,
                        imageResId = imageResId,
                        onToggleHidden = { updatedItem ->
                            nbkidsViewModel.toggleItemHidden(childId, item.id)
                        }
                    )
                }
            }
        }
    }
}