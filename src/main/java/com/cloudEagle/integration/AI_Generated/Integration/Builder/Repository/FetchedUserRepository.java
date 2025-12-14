package com.cloudEagle.integration.AI_Generated.Integration.Builder.Repository;

import com.cloudEagle.integration.AI_Generated.Integration.Builder.Model.FetchedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FetchedUserRepository extends JpaRepository<FetchedUser, Long> {

    List<FetchedUser> findBySourceAppAndEnvironment(String sourceApp, String environment);

    List<FetchedUser> findByEmail(String email);

    List<FetchedUser> findByEnvironment(String environment);

    List<FetchedUser> findBySourceApp(String sourceApp);

    List<FetchedUser> findByStatus(String status);

    List<FetchedUser> findBySourceAppAndStatus(String sourceApp, String status);

    List<FetchedUser> findByExternalUserId(String externalUserId);

    long countBySourceApp(String sourceApp);

    long countBySourceAppAndEnvironment(String sourceApp, String environment);

    boolean existsByExternalUserId(String externalUserId);
}
