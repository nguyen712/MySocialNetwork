package com.demospring.socialnetwork.entity;

import com.demospring.socialnetwork.util.enums.RelationShipStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    RelationShipStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    User user;
    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private User friend;
    Date CreatedDate;
    Date ModifiedDate;
}
