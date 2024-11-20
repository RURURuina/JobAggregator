package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.entity.KeySkill

data class KeySkillData(
    val name: String?, // Описание(Обязанности)
)

fun KeySkillData.map(): KeySkill {
    return KeySkill(
        this.name,
    )
}
