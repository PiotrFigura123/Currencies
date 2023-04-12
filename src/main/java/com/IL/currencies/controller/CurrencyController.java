package com.IL.currencies.controller;

import com.IL.currencies.dto.ExchangeReadModel;
import com.IL.currencies.service.ExchangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("projects")
class CurrencyController {

    private final ExchangeService exchangeService;


    public CurrencyController(final ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping("/loadData")
    public ResponseEntity<?> publish() {
        exchangeService.getDataFromApiToKafkaProducer();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    String showProjects(Model model) {
        model.addAttribute("exchanges", getExchanges());
        return "projects";
    }

    @ModelAttribute("exchanges")
    List<ExchangeReadModel> getExchanges() {
        return exchangeService.loadExchangeData();

    }

}
