package com.example.reaction

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.reaction.data.MainDb
import com.example.reaction.data.NameEntity
//import com.neco_desarrollo.roomdb.data.MainDb
//import com.neco_desarrollo.roomdb.data.NameEntity
import kotlinx.coroutines.launch

class MainViewModel(val database: MainDb) : ViewModel() {
    val itemsList = database.dao.getAllItems()
    val newText = mutableStateOf("")
    var nameEntity: NameEntity? = null

    fun insertItem() = viewModelScope.launch {
        val nameItem = nameEntity?.copy(name = newText.value)
            ?: NameEntity(name =  newText.value)
        database.dao.insertItem(nameItem)
        nameEntity = null
        newText.value = ""
    }

    fun deleteItem(item: NameEntity) = viewModelScope.launch {
        database.dao.deleteItem(item)
    }

    companion object{
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return MainViewModel(database) as T
            }
        }
    }
}