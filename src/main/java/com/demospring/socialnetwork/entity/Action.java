package com.demospring.socialnetwork.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String Id;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false, referencedColumnName = "post_id")
    Post post;
    String action;
    String quantity;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    User user;
}
