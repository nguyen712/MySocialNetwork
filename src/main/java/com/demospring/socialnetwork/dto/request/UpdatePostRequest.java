package com.demospring.socialnetwork.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePostRequest {
    @NotEmpty(message = "POST_ID_IS_BLANK")
    String postId;
    PostRequest postRequest;
}
