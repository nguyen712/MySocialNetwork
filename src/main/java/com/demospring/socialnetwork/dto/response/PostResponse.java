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
    String Id;
    String CreatedBy;
    String ModifiedBy;
    Date CreatedDate;
    Date ModifiedDate;
    String title;
    String content;
}
