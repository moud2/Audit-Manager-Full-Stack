package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.insight.backend.model.Category;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
public class CategoryController {

    /* GET request for categories with placeholder data */
    @GetMapping("/api/v1/categories")
    public ResponseEntity<List<Category>> getCategory() {
        List<Category> response = new ArrayList<>();
        response.add(createCategory(1L, "Server Administration"));
        response.add(createCategory(2L, "Firewall"));
        response.add(createCategory(3L, "Netzwerk"));
        response.add(createCategory(4L, "Antivirus"));
        response.add(createCategory(5L, "VPN"));
        response.add(createCategory(6L, "Monitoring"));
        response.add(createCategory(7L, "Email"));
        response.add(createCategory(8L, "Secure Browsing"));
        response.add(createCategory(9L, "Client"));
        response.add(createCategory(10L, "Patch-Management"));
        response.add(createCategory(11L, "Schwachstellen-Management"));
        response.add(createCategory(12L, "Verschlüsselung"));
        response.add(createCategory(13L, "Zertifikate und PKI"));
        response.add(createCategory(14L, "Mobile Device Management"));
        response.add(createCategory(15L, "Backup"));
        response.add(createCategory(16L, "Privilege Access Management (PAM)"));
        response.add(createCategory(17L, "Identity, Passwörter und Secure Logon"));
        response.add(createCategory(18L, "Nutzung von Clouddiensten"));
        response.add(createCategory(19L, "Konzepte und Richtlinien"));
        response.add(createCategory(20L, "IAM"));
        response.add(createCategory(21L, "Digitale Signatur"));

        return ResponseEntity.ok(response);
    }

    private Category createCategory(Long id, String name) {
        Category category = new Category(name, Set.of());
        category.setId(id);
        return category;
    }
 
}
