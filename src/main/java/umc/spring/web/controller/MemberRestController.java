package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MemberConverter;
import umc.spring.converter.MemberMissionConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Restaurant;
import umc.spring.domain.Review;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.service.MemberMissionService.MemberMissionCommandService;
import umc.spring.service.MemberMissionService.MemberMissionQueryService;
import umc.spring.service.MemberService.MemberCommandService;
import umc.spring.validation.annotation.*;
import umc.spring.web.dto.MemberRequestDTO;
import umc.spring.web.dto.MemberResponseDTO;
import umc.spring.web.dto.Mission.MissionResponseDTO;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class MemberRestController {

    private final MemberCommandService memberCommandService;
    private final MemberMissionCommandService memberMissionCommandService;
    private final MemberMissionQueryService memberMissionQueryService;

    @PostMapping("/join")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDTO request){
        Member member = memberCommandService.joinMember(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @PostMapping("/me/missions/{missionId}")
    public ApiResponse<MemberResponseDTO.ChallengeMissionDTO> challenge(@ExistMembers @RequestParam(name = "memberId") Long memberId,
                                                                        @ExistMissions @ChallengingMissions @PathVariable(name = "missionId") Long missionId){
        MemberMission challengeMission = memberMissionCommandService.challengeMission(missionId, memberId);
        return ApiResponse.onSuccess(MemberMissionConverter.toChallengMissionDTO(challengeMission));
    }

    @GetMapping("/me/missions/challenging")
    @Operation(summary = "내가 진행 중인 미션 목록 조회 API", description = "내가 진행 중인 미션들의 목록을 조회하는 API이며, 페이징을 포함합니다. query string으로 page 번호를 입력해주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "회원의 아이디, query string 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 0번이 1 페이지 입니다.")
    })
    public ApiResponse<MissionResponseDTO.MyChallengingMissionListDTO> getMyMissionList(@ExistMembers @RequestParam(name = "memberId") Long memberId,
                                                                                        @NegativePages @RequestParam(name = "page") Integer page){
        Page<MemberMission> missionList = memberMissionQueryService.getMyMissionList(memberId, page - 1);
        return ApiResponse.onSuccess(MemberMissionConverter.myChallengingMissionListDTO(missionList));
    }

    @PatchMapping("/me/missions/challenging/{missionId}")
    @Operation(summary = "내가 진행 중인 미션 목록 조회 API", description = "내가 진행 중인 미션들의 목록을 조회하는 API이며, 페이징을 포함합니다. query string으로 page 번호를 입력해주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "missionId", description = "미션의 아이디, query string 입니다."),
            @Parameter(name = "memberId", description = "회원의 아이디, path variable 입니다.")
    })
    public ApiResponse<MissionResponseDTO.CompleteMissionDTO> completeMission(@ExistMissions @AlreadyCompleteMission @PathVariable(name = "missionId") Long missionId,
                                                                              @ExistMembers @RequestParam(name = "memberId") Long memberId){
        MemberMission completeMission = memberMissionCommandService.completeMission(missionId, memberId);
        return ApiResponse.onSuccess(MemberMissionConverter.completeMissionDTO(completeMission));
    }
}
