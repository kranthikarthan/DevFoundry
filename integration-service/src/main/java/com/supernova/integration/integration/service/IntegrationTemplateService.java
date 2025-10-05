package com.supernova.integration.integration.service;

import com.supernova.integration.integration.dto.IntegrationTemplate;
import com.supernova.integration.integration.dto.IntegrationType;

import java.util.List;
import java.util.Optional;

public interface IntegrationTemplateService {
    List<IntegrationTemplate> getAllTemplates();
    List<IntegrationTemplate> getTemplatesByType(IntegrationType type);
    Optional<IntegrationTemplate> getTemplateById(String id);
    Optional<IntegrationTemplate> getTemplateByTypeAndId(IntegrationType type, String id);
}