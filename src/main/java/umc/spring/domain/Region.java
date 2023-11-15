package umc.spring.domain;

import lombok.*;
import umc.spring.domain.common.BaseEntity;
import umc.spring.domain.mapping.RestaurantRegion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Region extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<RestaurantRegion> restaurantRegionList = new ArrayList<>();
}
