package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insight.backend.model.Audit;

@RestController
public class AuditsController {

    @GetMapping("api/v1/audits")
    public ResponseEntity<List<Audit>> getAudits() {
        List<Audit> auditList = new ArrayList<>();

        // TODO: Temporary code for basic functionality | remove and reimplement properly later
        Audit audit1 = new Audit("ISO-2123", Set.of());
        Audit audit2 = new Audit("ISO-2124", Set.of());
        Audit audit3 = new Audit("ISO-2125", Set.of());
        auditList.add(audit1);
        auditList.add(audit2);
        auditList.add(audit3);

        return ResponseEntity.ok(auditList);
    }


}