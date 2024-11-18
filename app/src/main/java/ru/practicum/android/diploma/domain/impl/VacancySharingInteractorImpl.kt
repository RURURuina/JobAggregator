package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.sharing.VacancySharingInteractor
import ru.practicum.android.diploma.domain.api.sharing.VacancySharingRepository
import ru.practicum.android.diploma.domain.models.entity.Vacancy

class VacancySharingInteractorImpl(
    val vacancySharingRepository: VacancySharingRepository,
) : VacancySharingInteractor {
    override suspend fun shareVacancy(vacancy: Vacancy) {
        vacancySharingRepository.shareVacancy(vacancy)
    }
}
