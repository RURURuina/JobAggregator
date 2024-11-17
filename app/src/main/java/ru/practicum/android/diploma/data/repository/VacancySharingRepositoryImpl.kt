package ru.practicum.android.diploma.data.repository

import android.content.Context
import android.content.Intent
import ru.practicum.android.diploma.domain.api.sharing.VacancySharingRepository
import ru.practicum.android.diploma.domain.models.entity.Vacancy

class VacancySharingRepositoryImpl(
    val context: Context,
) : VacancySharingRepository {
    override suspend fun shareVacancy(vacancy: Vacancy) {
        context.startActivity(
            Intent
                .createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        ""
// TODO: сформировать строку для отыслкой сообщением
                    )
                    type = "text/plain"
                }, null)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}
