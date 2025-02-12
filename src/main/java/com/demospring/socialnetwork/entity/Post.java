package com.demospring.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "post_id")
    String id;
    String createdBy;
    String modifiedBy;
    Date createdDate;
    Date modifiedDate;
    String title;
    String content;
    long quantityOfLike = 0;
    long quantityOfDislike = 0;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Action> actions;

}
