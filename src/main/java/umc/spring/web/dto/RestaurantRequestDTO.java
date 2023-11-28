package umc.spring.web.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class RestaurantRequestDTO {

    @Getter
    public static class AddDTO{
        @NotBlank
        String name;
        @NotBlank
        String location;
        @NotBlank
        String specAddress;
    }
}
