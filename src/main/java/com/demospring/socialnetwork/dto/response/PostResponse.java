package com.demospring.socialnetwork.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    String id;
    String createdBy;
    String modifiedBy;
    Date createdDate;
    Date modifiedDate;
    String title;
    String content;
}
