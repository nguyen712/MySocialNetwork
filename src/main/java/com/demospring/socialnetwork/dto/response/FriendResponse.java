package com.demospring.socialnetwork.dto.response;

import com.demospring.socialnetwork.util.enums.RelationShipStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FriendResponse {
    String id;
    RelationShipStatus status;
}
