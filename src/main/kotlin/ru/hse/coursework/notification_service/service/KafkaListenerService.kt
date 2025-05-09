package ru.hse.coursework.notification_service.service

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.coursework.notification_service.dto.EmailRequest

@Service
class KafkaListenerService(
    private val emailSenderService: EmailSenderService
) {

    @Transactional
    @KafkaListener(
        topics = ["\${spring.kafka.topic.verify-user-email}"],
        groupId = "\${spring.kafka.consumer.group-id}",
        properties = ["spring.json.value.default.type=ru.hse.coursework.notification_service.dto.EmailRequest"]
    )
    fun sendMail(request: EmailRequest) {
        logger.info("Received email request from Kafka topic for user: ${request.targetEmail}")
        emailSenderService.sendEmail(request)
    }

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(KafkaListenerService::class.java)
    }
}
