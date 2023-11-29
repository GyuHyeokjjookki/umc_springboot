package umc.spring.web.dto;

import lombok.Getter;
import umc.spring.validation.annotation.ExistCategories;
import umc.spring.validation.annotation.ExistRegion;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class RestaurantRequestDTO {

    @Getter
    public static class AddDTO{
        @NotBlank
        String name;
        @NotBlank
        String location;
        @NotBlank
        String specAddress;
        @ExistRegion
        List<Long> locatedRegion;
    }
}
