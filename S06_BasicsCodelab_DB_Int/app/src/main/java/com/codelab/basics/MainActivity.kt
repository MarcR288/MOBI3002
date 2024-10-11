/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


// Started with Android Developer code sample
// Code available at https://developer.android.com/codelabs/jetpack-compose-theming#0
// ...then swapped in a DB to populate the compose list

package com.codelab.basics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme
import kotlinx.coroutines.delay


/**
 * Sample DB Compose app with Master-Details pages
 * ShowPageMaster ... shows the list of DB elements
 * ShowPageDetails ... shows the detail contents of one element
 *
 * There isn't much to show, so details looks empty, but
 * the structure is sound, even if the data itself is shallow
 *
 * Use the logcat to follow the logic.
 *
 * It's waiting for real data....
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Open the DB
        val dbClass = DBClass(this)
        Log.d("CodeLab_DB", "onCreate: ")

        // Retrieve and prepare the data
        val names = dbClass.findAll()

        // Then set the content
        setContent {
            BasicsCodelabTheme {
                MyApp(
                    modifier = Modifier.fillMaxSize(),
                    // Get the data from the DB for display
                    names = names,
                    dbClass = dbClass
                )
            }
        }
    }

}

@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    names: List<DataModel>,
    dbClass: DBClass  // Pass the database instance
) {
    var index by remember { mutableIntStateOf(-1) } // Track which name to display

    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if ((index < 0) || (index >= names.size)) { // Always Check endpoints!
            Log.d("CodeLab_DB", "MyApp1: index = $index")
            ShowPageMaster(
                names = names,
                dbClass = dbClass, // Pass the database instance here
                updateIndex = { index = it }
            )
        } else {
            Log.d("CodeLab_DB", "MyApp2: $index ")
            ShowPageDetails(
                dataModel = names[index],  // Get the current DataModel
                dbClass = dbClass,         // Pass the database instance
                index = index,             // Pass index for navigation
                updateIndex = { index = it }
            )
        }
    }
}


@Composable
private fun ShowPageMaster(
    modifier: Modifier = Modifier,
    names: List<DataModel>,
    updateIndex: (index: Int) -> Unit,
    dbClass: DBClass
) {
    var favourite by remember { mutableStateOf<DataModel?>(null) }

    LaunchedEffect(Unit) {
        while (true) {
            favourite = dbClass.findFavourite() // Update the favourite item
            delay(1000) // Poll every second (or adjust the interval as needed)
        }
    }

    LazyColumn(modifier = modifier.padding(vertical = 24.dp)) {
        // Show the favourite item if it exists
        favourite?.let {
            item {
                Text(
                    text = "Favourite: ${it.name}", //Challenge add a field to display in text what is favourite
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } ?: run {
            item {
                Text(
                    text = "No favourite found",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Show the rest of the items
        itemsIndexed(items = names) { pos, name ->
            Log.d("CodeLab_DB", "Item at index $pos is $name")
            ShowEachListItem(name = name, pos, updateIndex, dbClass)
        }
    }
}


@Composable
private fun ShowEachListItem(
    name: DataModel,
    pos: Int,
    updateIndex: (index: Int) -> Unit,
    dbClass: DBClass
) {
    //Challenge - Determine the color based on the type
    val cardColor = when (name.type) {  // Assuming type is a property of DataModel
        "Fire" -> Color.Red  // Replace with your actual types and colors
        "Water" -> Color.Blue
        "Grass" -> Color.Green
        "Bug" -> Color.Yellow
        "Normal" -> Color.Gray
        else -> MaterialTheme.colorScheme.primary // Default color
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = cardColor  // Use the determined color
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name, pos, updateIndex, dbClass)
        Log.d("CodeLab_DB", "Greeting: ")
    }
}

@Composable
private fun CardContent(
    name: DataModel,
    pos: Int,
    updateIndex: (index: Int) -> Unit,
    dbClass: DBClass // Pass the database instance to fetch data
) {
    var expanded by remember { mutableStateOf(false) }
    var updatedInfo by remember { mutableStateOf(name.toString()) } // Initialize with the current data model

    Row(
        modifier = Modifier
            .padding(2.dp) //Challenge / Changed Padding to show additional cards
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                onClick = {
                    updateIndex(pos)
                    Log.d("CodeLab_DB", "Clicked = $name ")
                }
            ) {
                val detailpos = pos +1
                Text(text = "Stats $detailpos ") //Challenge - Changed "pos" to a better generic term "stats" and start it at 1 (not 0)
            }

            val customFontFamily = FontFamily(Font(R.font.pixelletters))

            //Challenge - Display Name and Type on Card
            Text(
                text = name.name + " - " + name.type,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            if (expanded) {
                // Use LaunchedEffect to update information when expanded
                LaunchedEffect(name.id) { // Use a unique key to trigger updates
                    updatedInfo = dbClass.findAll().first { it.id == name.id }.toString()
                    Log.d("CodeLab_DB", "Updated info for ${name.id}: $updatedInfo")
                }

                Text(
                    text = updatedInfo,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Log.d("CodeLab_DB", "Expanded name = $name ")
            }
        }

        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}



@Composable
private fun ShowPageDetails(
    dataModel: DataModel,
    dbClass: DBClass,  // Pass the database instance
    updateIndex: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    index: Int
) {
    var updatedDataModel by remember { mutableStateOf(dataModel) }



    //Challenge - Increment the access count

    LaunchedEffect(dataModel.id) { // Trigger effect when the data model ID changes
        val rowsAffected = dbClass.updateAccessCount(dataModel.id.toInt())
        if (rowsAffected > 0) {
            updatedDataModel = dbClass.findAll().first { it.id == dataModel.id}

            Log.d("ACCESS COUNT", "Access Count updated for ID ${dataModel.id}:")

        } else {
            //Log.e("ACCESS COUNT", "Failed to update access count for ID ${dataModel.id}")
        }
    }



    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(updatedDataModel.toString())
        Log.d("CodeLab_DB", "ShowData: ${updatedDataModel.toString()}")

        Button(onClick = { updateIndex(-1) }) {
            Text(text = "Master")

        }
        Button(onClick = { updateIndex(index + 1) }) {
            Text(text = "Next")
        }
        Button(onClick = { updateIndex(index - 1) }) {
            Text(text = "Prev")
        }
    }
}

