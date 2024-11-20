package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.entity.Schedule

class ScheduleData(
    val id: String?,
    val name: String?,
)

fun ScheduleData.map(): Schedule {
    return Schedule(
        this.id,
        this.name
    )
}
