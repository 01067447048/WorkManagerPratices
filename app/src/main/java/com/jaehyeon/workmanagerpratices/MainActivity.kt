package com.jaehyeon.workmanagerpratices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.jaehyeon.workmanagerpratices.ui.theme.WorkManagerPraticesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val workManager = WorkManager.getInstance(applicationContext)
        val downloadRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .build()
        setContent {
            WorkManagerPraticesTheme {
                val workInfos = workManager
                    .getWorkInfosForUniqueWorkLiveData("download")
                    .observeAsState()
                    .value

                Button(onClick = {
                    workManager
                        .beginUniqueWork(
                            "download",
                            ExistingWorkPolicy.KEEP,
                            downloadRequest
                        )
                        .enqueue()
                }) {
                    Text(text = "Download")
                }
            }
        }
    }
}
