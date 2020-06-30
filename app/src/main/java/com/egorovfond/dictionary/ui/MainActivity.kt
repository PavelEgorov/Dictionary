package com.egorovfond.dictionary.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egorovfond.dictionary.R
import com.egorovfond.dictionary.di.koin.injectDependencies
import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.mvvm.MainInteractor
import com.egorovfond.dictionary.mvvm.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.egorovfond.utils.ui.viewById
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.btn_sheet_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<List<SearchResult>, MainInteractor>() {


    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mainActivityRecyclerview by viewById<RecyclerView>(R.id.rv_main)

    override val model: MainViewModel by viewModel()
    private val adapter: MainRvAdapter by lazy { MainRvAdapter(model.rvAdapterPresenter) }
    lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var splitInstallManager: SplitInstallManager
    private lateinit var appUpdateManager: AppUpdateManager

    private val REQUEST_CODE = 1110

    private val stateUpdatedListener: InstallStateUpdatedListener = object : InstallStateUpdatedListener {
        override fun onStateUpdate(state: InstallState?) {
            state?.let {
                if (it.installStatus() == InstallStatus.DOWNLOADED) {
                   popupSnackbarForCompleteUpdate()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //model = viewModelFactory.create(MainViewModel::class.java)
        model.subscribe().observe(this@MainActivity, Observer<List<SearchResult>> { renderData() })

        init()
    }

    override fun renderData() {
        adapter?.let{
            it.notifyDataSetChanged()
        }
    }

    fun init() {
        injectDependencies()

        checkForUpdates()

        mainActivityRecyclerview.layoutManager = LinearLayoutManager(applicationContext)
        mainActivityRecyclerview.adapter = adapter

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.peekHeight = 100
        bottomSheetBehavior.isHideable = false

        btn_find.setOnClickListener {
            edt_find.editText?.let{
                model.findWorld(it.text.toString())
            }
        }

        fab_main.setOnClickListener{
            splitInstallManager = SplitInstallManagerFactory.create(
                applicationContext
            )
            val request = SplitInstallRequest
                .newBuilder()
                .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
                .build()

            splitInstallManager
                .startInstall(request)
                .addOnSuccessListener {
                    val intent = Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Couldn't download feature: " + it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            //startActivity(Intent(this, com.egorovfond.dictionary.ui.HistoryActivity::class.java))
        }
    }

    private fun checkForUpdates() {
       appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
       val appUpdateInfo = appUpdateManager.appUpdateInfo

        appUpdateInfo.addOnSuccessListener { appUpdateIntent ->
            if (appUpdateIntent.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                 && appUpdateIntent.isUpdateTypeAllowed(IMMEDIATE)
            ) {
                appUpdateManager.registerListener(stateUpdatedListener)
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateIntent,
                    IMMEDIATE,
                    this,
                    REQUEST_CODE
                )
            }
        }
    }

    private fun popupSnackbarForCompleteUpdate() {
        Toast.makeText(
            applicationContext,
            "An update has just been downloaded.",
            Toast.LENGTH_LONG
        ).show()
        appUpdateManager.completeUpdate()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                appUpdateManager.unregisterListener(stateUpdatedListener)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Update flow failed! Result code: $resultCode",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        REQUEST_CODE
                    )
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
       menu?.findItem(R.id.menu_screen_settings)?.isVisible =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
                R.id.menu_screen_settings -> {
                    startActivityForResult(
                        Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY),
                        42)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
