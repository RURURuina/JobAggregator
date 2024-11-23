package ru.practicum.android.diploma.data.convertors

import ru.practicum.android.diploma.data.dto.industries.IndustryData
import ru.practicum.android.diploma.domain.models.entity.IndustryDomain

class IndustryDtoConverter {
    fun map(industryDomain : IndustryDomain) : IndustryData {
        return IndustryData(
            industryDomain.id,
            industryDomain.name
        )
    }
    fun map(industryData : IndustryData) : IndustryDomain {
        return IndustryDomain(
            industryData.id,
            industryData.name
        )
    }
}
