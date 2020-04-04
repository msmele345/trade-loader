package com.mitchmele.tradeloader.rabbitproducer;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbitmq/")
public class ProducerController {

    final RabbitSenderService rabbitSenderService;

    public ProducerController(RabbitSenderService rabbitSenderService) {
        this.rabbitSenderService = rabbitSenderService;
    }


    @PostMapping(value = "/producer")
    public @ResponseBody ResponseEntity<String> produceStock(@RequestBody String stockDetails) {
        try {
            rabbitSenderService.send(stockDetails);
            return ResponseEntity.ok(stockDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());

        }
    }
}
