package com.demospring.socialnetwork.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @NotBlank(message = "USERNAME_IS_BLANK")
    String username;
    @NotBlank(message = "PASSWORD_IS_BLANK")
    String password;
    @NotBlank(message = "FIRSTNAME_IS_BLANK")
    String firstName;
    @NotBlank(message = "LASTNAME_IS_BLANK")
    String lastName;
    String clientIp;
    String email;
}
