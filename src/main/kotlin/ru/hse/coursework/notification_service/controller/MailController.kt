package ru.hse.coursework.notification_service.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.hse.coursework.notification_service.dto.EmailRequest
import ru.hse.coursework.notification_service.service.EmailSenderService

@RestController
@RequestMapping(MAIL_SENDER_BASE_PATH_URL)
class MailController(
    private val emailSenderService: EmailSenderService
) {

    @PostMapping(SEND_MAIL_URL)
    fun sendMail(
        @RequestBody request: EmailRequest
    ): ResponseEntity<String> =
        emailSenderService.sendEmail(request)
}
