package com.system.reservation.online.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangePasswordDto {

    @NotNull
    private String currentPassword;

    @NotNull
    private String newPassword;

    @NotNull
    private String confirmNewPassword;

}
