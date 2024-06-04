package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insight.backend.model.Audit;

@RestController
public class GetAuditsController {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GetMapping("api/v1/audits")
    public ResponseEntity<String> getAudits() {
        List<Audit> auditList = new ArrayList<>();

        // TODO: Temporär Code Zeilen für Grundfunktion | später entfernen und neu implementieren
        Audit audit1 = new Audit(0, "ISO-2123");
        Audit audit2 = new Audit(1, "ISO-2124");
        Audit audit3 = new Audit(2, "ISO.2125");
        auditList.add(audit1);
        auditList.add(audit2);
        auditList.add(audit3);

        return ResponseEntity.ok(gson.toJson(auditList));
    }


}