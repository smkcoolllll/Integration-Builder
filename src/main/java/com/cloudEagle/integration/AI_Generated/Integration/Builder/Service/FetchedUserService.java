package com.cloudEagle.integration.AI_Generated.Integration.Builder.Service;

import com.cloudEagle.integration.AI_Generated.Integration.Builder.Model.FetchedUser;
import com.cloudEagle.integration.AI_Generated.Integration.Builder.Repository.FetchedUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FetchedUserService {

    private final FetchedUserRepository repository;

    public FetchedUser saveUser(FetchedUser user) {
        return repository.save(user);
    }

    public List<FetchedUser> getUsersByApp(String appName, String environment) {
        return repository.findBySourceAppAndEnvironment(appName, environment);
    }

    public List<FetchedUser> getAllUsers(String environment) {
        return repository.findByEnvironment(environment);
    }

    public List<FetchedUser> getUsersByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void deleteUsersByApp(String appName) {
        List<FetchedUser> users = repository.findBySourceApp(appName);
        repository.deleteAll(users);
    }

    public void deleteUser(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        repository.deleteById(id);
    }

    public long countUsersByApp(String appName) {
        return repository.countBySourceApp(appName);
    }
}