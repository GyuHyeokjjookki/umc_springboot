package umc.spring.service.MemberMissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.repository.MemberMissionRepository;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.MissionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberMissionQueryServiceImpl implements MemberMissionQueryService {

    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;

    @Override
    public MemberMission findMemberMission(Long missionId) {
        return memberMissionRepository.findByMissionId(missionId);
    }

    @Override
    public Page<MemberMission> getMyMissionList(Long memberId, Integer page) {
        Member member = memberRepository.findById(memberId).get();
        Page<MemberMission> missionPage = memberMissionRepository.findAllByMemberAndStatus(member, MissionStatus.CHALLENGING, PageRequest.of(page, 10));
        return missionPage;
    }
}
