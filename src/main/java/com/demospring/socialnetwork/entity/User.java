package com.demospring.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    String id;
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
    double latitude;
    double longitude;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Action> actions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<Friend> friendShip;

    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL)
    Set<Friend> friends;
}
