package com.demospring.socialnetwork.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAfterUpdateLocationResponse {
    String id;
    String firstName;
    String lastName;
    String email;
    double latitude;
    double longitude;
}
