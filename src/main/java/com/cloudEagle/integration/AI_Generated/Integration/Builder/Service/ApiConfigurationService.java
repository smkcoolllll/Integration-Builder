package com.cloudEagle.integration.AI_Generated.Integration.Builder.Service;

import com.cloudEagle.integration.AI_Generated.Integration.Builder.Model.ApiConfiguration;
import com.cloudEagle.integration.AI_Generated.Integration.Builder.Repository.ApiConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApiConfigurationService {

    private final ApiConfigurationRepository repository;

    public ApiConfiguration saveConfiguration(ApiConfiguration config) {
        return repository.save(config);
    }

    public Optional<ApiConfiguration> getConfiguration(String appName, String environment) {
        return repository.findByAppNameAndEnvironment(appName, environment);
    }

    public List<ApiConfiguration> getAllConfigurations(String environment) {
        return repository.findByEnvironment(environment);
    }

    public List<ApiConfiguration> getActiveConfigurations() {
        return repository.findByActiveTrue();
    }

    public void deleteConfiguration(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Configuration not found");
        }
        repository.deleteById(id);
    }

    public ApiConfiguration updateConfiguration(Long id, ApiConfiguration newConfig) {
        return repository.findById(id)
                .map(existing -> {
                    if (newConfig.getBaseUrl() != null) existing.setBaseUrl(newConfig.getBaseUrl());
                    if (newConfig.getEndpoint() != null) existing.setEndpoint(newConfig.getEndpoint());
                    if (newConfig.getHeaders() != null) existing.setHeaders(newConfig.getHeaders());
                    if (newConfig.getFieldMappings() != null) existing.setFieldMappings(newConfig.getFieldMappings());
                    if (newConfig.getAuthTemplate() != null) existing.setAuthTemplate(newConfig.getAuthTemplate());
                    if (newConfig.getHttpMethod() != null) existing.setHttpMethod(newConfig.getHttpMethod());
                    return repository.save(existing);
                })
                .orElse(null);
    }

    public void deactivateConfiguration(Long id) {
        repository.findById(id).ifPresent(config -> {
            config.setActive(false);
            repository.save(config);
        });
    }
}