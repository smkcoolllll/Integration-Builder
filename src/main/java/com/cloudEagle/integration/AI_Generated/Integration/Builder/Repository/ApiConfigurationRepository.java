package com.cloudEagle.integration.AI_Generated.Integration.Builder.Repository;

import com.cloudEagle.integration.AI_Generated.Integration.Builder.Model.ApiConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiConfigurationRepository extends JpaRepository<ApiConfiguration, Long> {

    Optional<ApiConfiguration> findByAppNameAndEnvironment(String appName, String environment);

    List<ApiConfiguration> findByEnvironment(String environment);

    List<ApiConfiguration> findByActiveTrue();

    List<ApiConfiguration> findByActiveFalse();

    List<ApiConfiguration> findByAppName(String appName);

    boolean existsByAppNameAndEnvironment(String appName, String environment);
}
