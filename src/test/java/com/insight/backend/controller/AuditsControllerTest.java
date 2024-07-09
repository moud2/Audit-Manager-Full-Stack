package com.insight.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


import com.insight.backend.model.Audit;
import com.insight.backend.service.Audit.FindAuditService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class AuditsControllerTest {
    // Automatisch injiziertes Calculator-Objekt
    @Autowired
    private Calculator calculator;

    /**
     * Endpunkt zum Addieren zweier Zahlen.
     *
     * @param zahl1 Erste Zahl
     * @param zahl2 Zweite Zahl
     * @return JSON-Antwort mit dem Ergebnis der Addition
     */
    @GetMapping("/api/v1/audits")
    public ResponseEntity<Object> add(@RequestParam int zahl1, int zahl2) {
        // Erstellung der Antwort als Map
        Map<String, Integer> response = new HashMap<>();
        response.put("result", calculator.add(zahl1, zahl2));
        // Rückgabe der Antwort mit HTTP-Status 200 OK
        return ResponseEntity.ok(response);
    }
}
