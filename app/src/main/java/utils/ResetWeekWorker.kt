package utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.testapplication.data.repository.UserRepository
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class ResetWeekWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams), KoinComponent {

    override fun doWork(): Result {
        val repo: UserRepository? by inject()

        return if (repo != null) {
            repo?.resetWeek()
            Result.success()
        } else {
            Result.retry()
        }

    }
}