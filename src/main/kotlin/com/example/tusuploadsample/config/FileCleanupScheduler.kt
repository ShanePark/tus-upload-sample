package com.example.tusuploadsample.config

import me.desair.tus.server.TusFileUploadService
import org.slf4j.Logger
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class FileCleanupScheduler(
    private val fileUploadService: TusFileUploadService,
) {
    val log: Logger = org.slf4j.LoggerFactory.getLogger(FileCleanupScheduler::class.java)

    @Scheduled(fixedDelayString = "PT24H")
    fun cleanup() {
        log.info("clean up")
        fileUploadService.cleanup()
    }
}
