package umc.spring.service.RegionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.repository.RegionRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionQueryServiceImpl implements RegionQueryService{

    private final RegionRepository regionRepository;

    @Override
    @Transactional
    public boolean existRegionById(Long id) {
        return regionRepository.existsById(id);
    }
}
