package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.entity.Contacts
import ru.practicum.android.diploma.domain.models.entity.Phone

data class ContactsData(
    val email: String?,
    val name: String?,
    val phones: List<PhonesData>? = null,
)

fun ContactsData.map(): Contacts {
    return Contacts(
        this.email,
        this.name,
        phonesMap(this.phones)
    )
}

private fun phonesMap(oldList: List<PhonesData>?): List<Phone>? {
    return oldList?.let {
        val list = mutableListOf<Phone>()
        oldList.map { list.add(it.map()) }
        list
    }
}
