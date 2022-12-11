package com.example.tusuploadsample.config

import me.desair.tus.server.TusFileUploadService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import javax.annotation.PostConstruct
import javax.servlet.ServletContext

@Configuration
class TusConfiguration(
    @Value("\${tus.upload.dir}")
    val tusStoragePath: String,

    @Value("\${tus.upload.expiration}")
    val tusUploadExpirationPeriod: Long,

    private val servletContext: ServletContext
) {

    private val log = org.slf4j.LoggerFactory.getLogger(TusConfiguration::class.java)

    @PostConstruct
    fun init() {
        if (File("$tusStoragePath/uploads").mkdirs()) {
            log.info("Created tus upload directory")
        }
        if (File("$tusStoragePath/locks").mkdirs()) {
            log.info("Created tus lock directory")
        }
    }

    @Bean
    fun tusService(): TusFileUploadService {
        return TusFileUploadService()
            .withUploadURI(servletContext.contextPath + "/upload")
            .withStoragePath(tusStoragePath)
            .withDownloadFeature()
            .withUploadExpirationPeriod(tusUploadExpirationPeriod)
    }

}
