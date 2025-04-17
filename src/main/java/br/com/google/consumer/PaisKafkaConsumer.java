package br.com.google.consumer;


import br.com.google.dto.PaisDto;
import br.com.google.service.PaisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaisKafkaConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

//    @Autowired
//    private PaisService paisService;

    private final PaisService paisService;
    @Value("${topic1.name.consumer}")
    private String topicName;

    @KafkaListener(topics = "${topic1.name.consumer}", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> payload){

        log.info("Tópico: {}", topicName);
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partion: {}", payload.partition());
        log.info("Order: {}", payload.value());

        PaisDto paisDto = deserializeKey(payload.key());
        if (paisDto != null) {
            paisService.inserirPais(paisDto);
            log.info("PaisDto: código={}, nome={}", paisDto.getCodigo(), paisDto.getNome());
        }


    }

    private PaisDto deserializeKey(String key) {
        PaisDto paisDto = new PaisDto();
        try {
            paisDto = objectMapper.readValue(key, PaisDto.class);
            return paisDto;
        } catch (Exception e) {
            log.error("Erro ao deserializar a chave: {}", e.getMessage());
            return null;
        }
    }

}
