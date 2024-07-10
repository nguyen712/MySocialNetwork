package com.demospring.socialnetwork.dto.request;

import com.demospring.socialnetwork.util.enums.RelationShipStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActionWithFriendRequest {
    RelationShipStatus action;
    String friendId;
}
