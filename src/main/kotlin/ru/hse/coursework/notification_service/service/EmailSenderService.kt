package ru.hse.coursework.notification_service.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import ru.hse.coursework.notification_service.dto.EmailRequest

@Service
class EmailSenderService(
    private val mailSender: JavaMailSender,
    @Value("\${spring.mail.username}")
    private val from: String
) {

    fun sendEmail(request: EmailRequest): ResponseEntity<String> =
        try {
            SimpleMailMessage().apply {
                from = from
                setTo(request.targetEmail)
                subject = request.subject
                text = request.text
            }.let { message ->
                mailSender.send(message)

                logger.info("Email sent to ${request.targetEmail} with subject: ${request.subject}")
                ResponseEntity.ok("Email sent successfully")
            }
        } catch (e: MailException) {
            logger.error("Failed to send email to ${request.targetEmail}: ${e.message}", e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to send email: ${e.message}")
        }

    companion object {
        private val logger = LoggerFactory.getLogger(EmailSenderService::class.java)
    }
}