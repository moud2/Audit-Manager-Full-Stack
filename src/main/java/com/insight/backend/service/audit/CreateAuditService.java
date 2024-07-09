package com.insight.backend.service.audit;

import com.insight.backend.dto.AuditResponseDTO;
import com.insight.backend.dto.NewAuditDTO;
import com.insight.backend.model.Audit;
import com.insight.backend.repository.CategoryRepository;

public class CreateAuditService {

    private final CategoryRepository categoryRepository;
    private final SaveAuditService saveAuditService;

    public CreateAuditService(CategoryRepository categoryRepository,SaveAuditService saveAuditService ) {
        this.categoryRepository = categoryRepository;
        this.saveAuditService = saveAuditService;
    }



    public AuditResponseDTO createAudit(NewAuditDTO newAuditDTO) {

                Audit audit = new Audit();
        audit.setName(newAuditDTO.getName());
        


     
        saveAuditService.saveAudit(audit);

        for (Long categoryId : newAuditDTO.getCategories()) {
            if (!isCategoryValid(categoryId)) {
                return null;
            }
        }
        return newAuditDTO;
    }



    private boolean isCategoryValid(Long categoryId) {
        return categoryRepository.findById(categoryId).isPresent();
    }
}
