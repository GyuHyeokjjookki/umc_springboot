package umc.spring.web.dto.Mission;

import lombok.Getter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class MissionRequestDTO {

    @Getter
    public static class MissionDTO{

        @Future
        LocalDate deadLine;
        @NotNull
        Integer reward;
        @NotBlank
        String content;
    }
}
