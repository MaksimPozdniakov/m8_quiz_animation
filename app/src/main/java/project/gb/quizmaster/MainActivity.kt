package project.gb.quizmaster

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import project.gb.quizmaster.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_content_navigation) as NavHostFragment

        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * Добавляем анимацию к системной кнопке назад
     */
    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        val currentDestination = navController.currentDestination
        val previousDestination = navController.previousBackStackEntry?.destination

        // Применяем анимацию только если есть предыдущий фрагмент
        if (previousDestination != null && currentDestination != null) {
            val animResId = when (navController.graph.startDestinationId) {
                currentDestination.id -> R.anim.slide_in_right
                else -> R.anim.slide_in_left
            }

            // Применяем анимацию к контейнеру фрагментов
            findViewById<View>(R.id.nav_host_fragment_content_navigation)?.let { containerView ->
                val animation = AnimationUtils.loadAnimation(this, animResId)
                containerView.startAnimation(animation)
            }
        }
        super.onBackPressed()
    }

}