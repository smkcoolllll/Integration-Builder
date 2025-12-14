package com.cloudEagle.integration.AI_Generated.Integration.Builder.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "fetched_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FetchedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sourceApp;

    @Column(nullable = false)
    private String externalUserId;

    @Column(nullable = false)
    private String email;

    private String name;
    private String status;

    @Column(columnDefinition = "TEXT")
    private String rawData;

    @Column(columnDefinition = "TEXT")
    private String mappedData;

    @Column(nullable = false)
    private String environment;

    @CreationTimestamp
    private LocalDateTime fetchedAt;

    @Builder.Default
    private String fetchStatus = "SUCCESS";
}
