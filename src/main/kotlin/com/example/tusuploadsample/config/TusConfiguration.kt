package com.example.tusuploadsample.config

import me.desair.tus.server.TusFileUploadService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TusConfiguration(
    @Value("\${tus.upload.dir}")
    val tusStoragePath: String,

    @Value("\${tus.upload.expiration}")
    val tusUploadExpirationPeriod: Long
) {

    @Bean
    fun tusService(): TusFileUploadService {
        return TusFileUploadService()
            .withUploadURI("/upload")
            .withStoragePath(tusStoragePath)
            .withDownloadFeature()
            .withUploadExpirationPeriod(tusUploadExpirationPeriod)
    }

}
