package kr.co.chunjae.user.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserDTO {
    private String phone;

    private String name;

    private String password;

    private Integer grade;
}
