package com.shuxuan.wu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Student ID: 301203269
//Student Name: Shuxuan Wu
//Second Activity

// ViewModel for managing contact data
class ContactViewModel : ViewModel() {
    val contactList = mutableStateListOf<Contact>()

    fun addContact(contact: Contact) {
        contactList.add(contact)
    }
}

//Add Contact Screen
@Composable
fun AddContactScreen(viewModel: ContactViewModel) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contactTypes by remember { mutableStateOf(setOf<String>()) }
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Name input field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Phone input field
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )

            // Email input field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Determine whether to use Checkboxes or RadioButtons
            val nameStartsWithAtoN = name.firstOrNull()?.uppercaseChar()?.let { it in 'A'..'N' } ?: true
            if (nameStartsWithAtoN) {
                CheckboxGroup(
                    options = listOf("Friend", "Family", "Work"),
                    selectedOptions = contactTypes,
                    onCheckedChange = { option, isChecked ->
                        contactTypes = if (isChecked) {
                            contactTypes + option
                        } else {
                            contactTypes - option
                        }
                    }
                )
            } else {
                RadioButtonGroup(
                    options = listOf("Friend", "Family", "Work"),
                    onSelectedChange = { contactTypes = setOf(it) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Add contact button
            Button(
                onClick = {
                    if (name.isNotBlank() && phone.isNotBlank() && email.isNotBlank() && contactTypes.isNotEmpty()) {
                        viewModel.addContact(Contact(name, phone, email, contactTypes.joinToString(", ")))
                        // Show success message
                        CoroutineScope(Dispatchers.Main).launch {
                            snackBarHostState.showSnackbar("Added successfully")
                        }
                        // Reset fields after adding
                        name = ""
                        phone = ""
                        email = ""
                        contactTypes = setOf()
                    } else {
                        // Show error message if not all fields are filled
                        CoroutineScope(Dispatchers.Main).launch {
                            snackBarHostState.showSnackbar("You must complete all the fields!")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Contact")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // LazyColumn for display list of added contacts
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(viewModel.contactList) { contact ->
                    ContactItem(contact)
                }
            }
        }
    }
}

//Card for LazyColumn
@Composable
fun ContactItem(contact: Contact) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${contact.name}")
            Text(text = "Phone: ${contact.phone}")
            Text(text = "Email: ${contact.email}")
            Text(text = "Type: ${contact.type}")
        }
    }
}