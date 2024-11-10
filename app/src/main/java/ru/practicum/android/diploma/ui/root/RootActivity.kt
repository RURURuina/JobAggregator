package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private var binding: ActivityRootBinding? = null
    private var navHostFragment: NavHostFragment? = null
    private var navController: NavController? = null
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBinding()
        prepareNavHostFragment()
        prepareNavHostController()
        prepareBottomNavView()
        // Пример использования access token для HeadHunter API
        val a = Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HHApi::class.java)
        lifecycleScope.launch {
            networkRequestExample(
                a, VacancySearchRequest(hashMapOf())
            )
        }
    }

    private fun prepareBinding() {
        binding = ActivityRootBinding.inflate(layoutInflater)
        binding?.let {
            setContentView(it.root)
        }
    }

    private fun prepareNavHostFragment() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
    }

    private fun prepareNavHostController() {
        navHostFragment?.let {
            navController = it.navController
        }
    }

    private fun prepareBottomNavView() {
        binding?.let {
            bottomNavigationView = it.bottomNavigation
            navController?.let {
                bottomNavigationView?.setupWithNavController(it)
                it.navInflater
            }
        }
    }

    private suspend fun networkRequestExample(api: HHApi, request: VacancySearchRequest) {
        withContext(Dispatchers.IO) {

            val response = api.searchVacancies(request.request)
            response.apply {
                resultResponse = ResponseStatus.OK

            }
            println(response.vacancies)
        }
    }

    fun bottomNavigationVisibility(isVisibile: Boolean) {
        binding?.bottomNavigation?.isVisible = isVisibile
    }
}

interface HHApi {
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: YpDiplomaProject"
    )
    @GET("/vacancies/")
    fun searchVacancies(
        @QueryMap params: HashMap<String, String>,
    ): VacancyResponse
}

open class VacancyResponse(
    @SerializedName("items")
    val vacancies: List<String>?,
    val found: Int?,
    val page: Int?,
    val pages: Int?,
) : Response(){
    fun get():List<String>?{
        return vacancies
    }
}

open class Response {
    var resultResponse: ResponseStatus = ResponseStatus.DEFAULT
    var resultCode: Int = 0
}

sealed interface ResponseStatus {
    object DEFAULT : ResponseStatus
    object OK : ResponseStatus
}

data class VacancySearchRequest(
    val request: HashMap<String, String>,
)
