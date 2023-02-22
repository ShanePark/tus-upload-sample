package com.example.tusuploadsample.controller

import me.desair.tus.server.TusFileUploadService
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.*
import org.springframework.web.bind.annotation.RestController
import java.io.File
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@CrossOrigin(exposedHeaders = ["Upload-Offset", "Location"])
class UploadController(
    private val fileUploadService: TusFileUploadService,
    @Value("\${tus.upload.dir}")
    private val tusStoragePath: String,
) {
    @RequestMapping(
        value = ["/upload", "/upload/**"],
        method = [GET, POST, HEAD, PATCH, DELETE]
    )
    fun tusUpload(request: HttpServletRequest, response: HttpServletResponse) {
        fileUploadService.process(request, response)

        val requestURI = request.requestURI
        val uploadInfo = fileUploadService.getUploadInfo(requestURI)

        uploadInfo?.let {
            if (!uploadInfo.isUploadInProgress) {
                val file = File(tusStoragePath, uploadInfo.fileName)
                fileUploadService.getUploadedBytes(requestURI).use {
                    FileUtils.copyInputStreamToFile(it, file)
                }
                fileUploadService.deleteUpload(requestURI)
            }
        }
    }

}
