package com.cloudEagle.integration.AI_Generated.Integration.Builder.Controller;

import com.cloudEagle.integration.AI_Generated.Integration.Builder.Model.ApiConfiguration;
import com.cloudEagle.integration.AI_Generated.Integration.Builder.Model.FetchedUser;
import com.cloudEagle.integration.AI_Generated.Integration.Builder.Service.ApiClientService;
import com.cloudEagle.integration.AI_Generated.Integration.Builder.Service.ApiConfigurationService;
import com.cloudEagle.integration.AI_Generated.Integration.Builder.Service.FetchedUserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/integration")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class IntegrationController {

    private final ApiConfigurationService configService;
    private final FetchedUserService userService;
    private final ApiClientService apiClient;
    private final Gson gson = new Gson();

    @PostMapping("/config")
    public ResponseEntity<?> saveConfiguration(@RequestBody ApiConfiguration config) {
        if (config.getAppName() == null || config.getAppName().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "error", "App name is required"));
        }
        ApiConfiguration saved = configService.saveConfiguration(config);
        return ResponseEntity.ok(Map.of("success", true, "data", saved));
    }

    @GetMapping("/config/{appName}/{environment}")
    public ResponseEntity<?> getConfiguration(@PathVariable String appName, 
                                              @PathVariable String environment) {
        return configService.getConfiguration(appName, environment)
                .map(cfg -> ResponseEntity.ok(Map.of("success", true, "data", cfg)))
                .orElse(ResponseEntity.badRequest()
                        .body(Map.of("success", false, "error", "Configuration not found")));
    }

    @GetMapping("/config/all/{environment}")
    public ResponseEntity<?> getAllConfigurations(@PathVariable String environment) {
        List<ApiConfiguration> configs = configService.getAllConfigurations(environment);
        return ResponseEntity.ok(Map.of("success", true, "data", configs));
    }

    @PutMapping("/config/{id}")
    public ResponseEntity<?> updateConfiguration(@PathVariable Long id, 
                                                 @RequestBody ApiConfiguration config) {
        ApiConfiguration updated = configService.updateConfiguration(id, config);
        if (updated == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "error", "Configuration not found"));
        }
        return ResponseEntity.ok(Map.of("success", true, "data", updated));
    }

    @DeleteMapping("/config/{id}")
    public ResponseEntity<?> deleteConfiguration(@PathVariable Long id) {
        configService.deleteConfiguration(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Configuration deleted"));
    }

    @PostMapping("/fetch/{appName}/{environment}")
    public ResponseEntity<?> fetchUsersFromApp(
            @PathVariable String appName,
            @PathVariable String environment,
            @RequestParam(defaultValue = "false") boolean isSandbox) {

        Optional<ApiConfiguration> configOpt = configService.getConfiguration(appName, environment);
        if (configOpt.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "error", "Configuration not found"));
        }

        ApiConfiguration config = configOpt.get();
        if (!config.isActive()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "error", "Configuration is inactive"));
        }

        String fullUrl = config.getBaseUrl() + config.getEndpoint();
        Map<String, String> headers = parseJsonToMap(config.getHeaders());

        ApiClientService.ApiResponse apiResponse = apiClient.callExternalApi(
                fullUrl,
                config.getHttpMethod(),
                headers,
                null,
                10000
        );

        if (!apiResponse.success()) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, 
                           "error", apiResponse.body(), 
                           "statusCode", apiResponse.statusCode())
            );
        }

        List<Map<String, Object>> rawUsers = apiClient.extractArrayFromResponse(apiResponse.body(), "data");
        Map<String, String> fieldMappings = parseJsonToMap(config.getFieldMappings());

        List<FetchedUser> savedUsers = new ArrayList<>();

        for (Map<String, Object> raw : rawUsers) {
            Map<String, Object> mapped = apiClient.mapFields(raw, fieldMappings);

            FetchedUser user = FetchedUser.builder()
                    .sourceApp(appName)
                    .externalUserId(String.valueOf(raw.getOrDefault("id", "")))
                    .email(String.valueOf(mapped.getOrDefault("email", "")))
                    .name(String.valueOf(mapped.getOrDefault("name", "")))
                    .status(String.valueOf(mapped.getOrDefault("status", "active")))
                    .rawData(gson.toJson(raw))
                    .mappedData(gson.toJson(mapped))
                    .environment(environment)
                    .fetchStatus("SUCCESS")
                    .build();

            savedUsers.add(userService.saveUser(user));
        }

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Fetched " + savedUsers.size() + " users",
                "data", savedUsers,
                "isSandbox", isSandbox,
                "totalFetched", rawUsers.size()
        ));
    }

    @GetMapping("/users/{appName}/{environment}")
    public ResponseEntity<?> getUsersByApp(@PathVariable String appName, 
                                          @PathVariable String environment) {
        List<FetchedUser> users = userService.getUsersByApp(appName, environment);
        return ResponseEntity.ok(Map.of("success", true, "data", users));
    }

    @GetMapping("/users/all/{environment}")
    public ResponseEntity<?> getAllUsers(@PathVariable String environment) {
        List<FetchedUser> users = userService.getAllUsers(environment);
        return ResponseEntity.ok(Map.of("success", true, "data", users));
    }

    @DeleteMapping("/users/{appName}")
    public ResponseEntity<?> deleteUsersByApp(@PathVariable String appName) {
        userService.deleteUsersByApp(appName);
        return ResponseEntity.ok(Map.of("success", true, "message", "Users deleted"));
    }

    private Map<String, String> parseJsonToMap(String json) {
        try {
            if (json == null || json.isBlank()) return new HashMap<>();
            return gson.fromJson(json, Map.class);
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
}