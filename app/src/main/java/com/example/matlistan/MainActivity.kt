// Projketets unika package rad.
package com.example.matlistan

// Med hjälp av importer hämtar vi alla funktioner och verktyg som appen behöver.
import androidx.compose.ui.draw.clip
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.matlistan.ui.theme.MatlistanTheme

// Här har jag skapat upp de färger som bestämmer hur appen ska se ut.
val DarkBlue = Color(0xFF061D3D)
val NavyBlue = Color(0xFF0B2B52)
val SectionBlue = Color(0xFF174477)
val ItemBlue = Color(0xFF32669B)
val ButtonBlue = Color(0xFF2586E8)
val White = Color(0xFFFFFFFF)
val LightText = Color(0xFFD8E9FA)

// Denna klassen beskriver en vara i inköpslistan.
data class ShoppingItem(
    val id: Int,
    val name: String,
    val quantity: Int,
    val isBought: Boolean
)

// Här är MainAktivity, här är appens startpunkt och startar appens utseedet.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MatlistanTheme {
                ShoppingScreen()
            }
        }
    }
}

// Här nedan har jag skapat upp massa olika tänkbara emojis beroende på varans namn.
// Lowercase() funkar genom att båda stora och små bokstäver fungerar i appen.
// Trim() gör att den tar bort extra mellanslag i både början och slutet.
fun getItemEmoji(itemName: String): String {

    return when (itemName.lowercase()) {

        "mjölk" -> "🥛"
        "bröd" -> "🍞"
        "ost" -> "🧀"
        "ägg" -> "🥚"
        "smör" -> "🧈"
        "banan", "bananer" -> "🍌"
        "äpple", "äpplen" -> "🍎"
        "apelsin", "apelsiner", "grapefruit" -> "🍊"
        "potatis" -> "🥔"
        "tomat", "tomater" -> "🍅"
        "gurka" -> "🥒"
        "morot", "morötter" -> "🥕"
        "broccoli" -> "🥦"
        "majs" -> "🌽"
        "jordgubbar" -> "🍓"
        "vindruvor" -> "🍇"
        "citron" -> "🍋"
        "avokado" -> "🥑"
        "pizza" -> "🍕"
        "hamburgare" -> "🍔"
        "pasta", "makaroner" -> "🍝"
        "ris" -> "🍚"
        "kyckling" -> "🍗"
        "fisk" -> "🐟"
        "kaffe" -> "☕"
        "juice" -> "🧃"
        "läsk", "cola", "coca cola", "pepsi", "pepsi max", "energidryck", "monster" -> "🥤"
        "yoghurt" -> "🥣"
        "kaka", "kakor" -> "🍪"
        "godis" -> "🍬"
        "sushi" -> "🍣"

        // Om det skulle vara att appen inte känner igen varan som skrevs in.
        else -> "🛍️"
    }
}

// Denna funktion bygger upp hela appens innehåll.
@Composable
fun ShoppingScreen() {

    // Sparar alla varor som användaren lägger till.
    val shoppingItems = remember {
        mutableStateListOf<ShoppingItem>()
    }
    // Denna remember sparar texten som skrivs in i inmatingsfältet.
    var itemText by remember { mutableStateOf("") }
    // Och denna remember ger varje ny vara ett eget ID.
    var nextId by remember { mutableStateOf(0) }

    // Räknar det totala antalet varor
    val totalCount = shoppingItems.sumOf { it.quantity }

    // Bakgrund med mörkblå gradient med dekorativa bubblor.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(30.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF174477),
                        DarkBlue,
                        Color(0xFF061A36)
                    )
                )
            )
    ) {

        // Dekorativa bubblor i bakgrunden
        Box(
            modifier = Modifier
                .size(120.dp)
                .offset(x = 270.dp, y = 20.dp)
                .background(
                    Color(0xFF5A8FC2).copy(alpha = 0.13f),
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(85.dp)
                .offset(x = (-25).dp, y = 280.dp)
                .background(
                    Color(0xFF5A8FC2).copy(alpha = 0.12f),
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(110.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 25.dp, y = 20.dp)
                .background(
                    Color(0xFF5A8FC2).copy(alpha = 0.13f),
                    CircleShape
                )
        )

        // Appens innehåll
        // I denna placeras rubriken, inköpslistan och knapparna.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 24.dp),

            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // RUBRIK
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "🛍️",
                    fontSize = 48.sp
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Matlistan",
                        color = White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Listan innehåller $totalCount varor",
                        color = LightText,
                        fontSize = 14.sp
                    )
                }
            }

            // LÄGG TILL VARA
            // Här kan användare skriva in en ny vara.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFF24517D).copy(alpha = 0.75f),
                        RoundedCornerShape(24.dp)
                    )
                    .border(
                        1.dp,
                        Color.White.copy(alpha = 0.15f),
                        RoundedCornerShape(24.dp)
                    )
                    .padding(12.dp),

                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                OutlinedTextField(
                    value = itemText,
                    onValueChange = { itemText = it },
                    placeholder = {
                        Text(
                            "Lägg till en vara...",
                            color = LightText
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = White,
                        unfocusedTextColor = White,
                        focusedBorderColor = Color(0xFFB8DBFF),
                        unfocusedBorderColor = LightText,
                        cursorColor = White,
                        focusedContainerColor = ItemBlue.copy(alpha = 0.45f),
                        unfocusedContainerColor = ItemBlue.copy(alpha = 0.35f)
                    )
                )
                // Knappen som lägger till varan i listan.
                Button(
                    onClick = {
                        shoppingItems.add(
                            ShoppingItem(
                                id = nextId,
                                name = itemText.trim(),
                                quantity = 1,
                                isBought = false
                            )
                        )

                        nextId++
                        itemText = ""
                    },
                    enabled = itemText.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonBlue,
                        disabledContainerColor = Color(0xFF47739F)
                    )
                ) {
                    Text(
                        text = "＋  Lägg till vara",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // VAROR SOM SKA HANDLAS
            // Denna Column visar alla varor som ännu inte är köpta.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        SectionBlue.copy(alpha = 0.85f),
                        RoundedCornerShape(26.dp)
                    )
                    .padding(12.dp),

                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "🛒  Ska handla in dessa varor",
                    color = White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(6.dp)
                )
                // shoppingItems hämtar bara varorna som inte är köpta
                shoppingItems
                    .filter { !it.isBought }
                    .forEach { item ->

                        ShoppingItemRow(
                            item = item,

                            // Flyttar över varan till köpta varor.
                            onToggleBought = {
                                val index = shoppingItems.indexOfFirst {
                                    it.id == item.id
                                }

                                if (index != -1) {
                                    shoppingItems[index] =
                                        item.copy(isBought = true)
                                }
                            },

                            onIncrease = {
                                val index = shoppingItems.indexOfFirst {
                                    it.id == item.id
                                }

                                if (index != -1) {
                                    shoppingItems[index] =
                                        item.copy(
                                            quantity = item.quantity + 1
                                        )
                                }
                            },

                            onDecrease = {
                                val index = shoppingItems.indexOfFirst {
                                    it.id == item.id
                                }

                                if (index != -1 && item.quantity > 1) {
                                    shoppingItems[index] =
                                        item.copy(
                                            quantity = item.quantity - 1
                                        )
                                }
                            },

                            // onDelete tar bort varan från listan.
                            onDelete = {
                                shoppingItems.remove(item)
                            }
                        )
                    }
            }

            // KÖPTA VAROR
            // Denna Column visar varor som användaren har markerat som köpta.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFF24517D).copy(alpha = 0.85f),
                        RoundedCornerShape(26.dp)
                    )
                    .padding(12.dp),

                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "✅  Köpta varor",
                    color = White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(6.dp)
                )
                // shoppingItems hämtar varor som bara är köpta.
                shoppingItems
                    .filter { it.isBought }
                    .forEach { item ->

                        ShoppingItemRow(
                            item = item,

                            // Flyttar tillbaka varan till inköpslistan.
                            onToggleBought = {
                                val index = shoppingItems.indexOfFirst {
                                    it.id == item.id
                                }

                                if (index != -1) {
                                    shoppingItems[index] =
                                        item.copy(isBought = false)
                                }
                            },

                            onIncrease = {
                                val index = shoppingItems.indexOfFirst {
                                    it.id == item.id
                                }

                                if (index != -1) {
                                    shoppingItems[index] =
                                        item.copy(
                                            quantity = item.quantity + 1
                                        )
                                }
                            },

                            onDecrease = {
                                val index = shoppingItems.indexOfFirst {
                                    it.id == item.id
                                }

                                if (index != -1 && item.quantity > 1) {
                                    shoppingItems[index] =
                                        item.copy(
                                            quantity = item.quantity - 1
                                        )
                                }
                            },

                            // onDelete tar bort varan från listan.
                            onDelete = {
                                shoppingItems.remove(item)
                            }
                        )
                    }
            }

            // RENSA HELA LISTAN
            // OutlineButton rensar bort samtliga varor när knappen trycks.
            OutlinedButton(
                onClick = {
                    shoppingItems.clear()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = White
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.5.dp,
                    Color(0xFFB8DBFF)
                )
            ) {
                Text(
                    text = "⟲  Rensa hela listan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// VISAR EN ENSKILD VARA
// Denna funktionen visar en ensild vara i listan.
@Composable
fun ShoppingItemRow(
    item: ShoppingItem,
    onToggleBought: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF477EB2),
                        ItemBlue
                    )
                ),
                RoundedCornerShape(22.dp)
            )
            .padding(horizontal = 5.dp, vertical = 5.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        // Markera som köpt
        // Användaren kan markera en vara som köpt.
        Checkbox(
            checked = item.isBought,
            onCheckedChange = {
                onToggleBought()
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF39C96B),
                uncheckedColor = White,
                checkmarkColor = White
            )
        )

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Visar en emoji som passar till varan
            Text(
                text = getItemEmoji(item.name),
                fontSize = 23.sp
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Visar varans namn
            Text(
                text = item.name,
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }


        // Plus och minus visas bara för varorna som inte är köpta ännu.
        if (!item.isBought) {

            // MINSKA ANTAL
            IconTextButton(
                text = "−",
                onClick = onDecrease,
                enabled = item.quantity > 1
            )

            // Visar hur många av varorna som ska handlas.
            Text(
                text = "${item.quantity}",
                color = White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 5.dp)
            )

            // ÖKA ANTAL
            IconTextButton(
                text = "+",
                onClick = onIncrease
            )
        }

        // TA BORT VARA
        // Papperskog emojin tar bort enskilda varor ur inköplistan.
        TextButton(
            onClick = onDelete,
            modifier = Modifier.width(42.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "🗑",
                fontSize = 20.sp
            )
        }
    }
}

// RUND KNAPP FÖR PLUS OCH MINUS
@Composable
fun IconTextButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {

    TextButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.size(38.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = NavyBlue,
            contentColor = White,
            disabledContentColor = LightText.copy(alpha = 0.35f)
        ),
        shape = CircleShape
    ) {
        Text(
            text = text,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
