package umc.spring.domain.mapping;

import lombok.*;
import umc.spring.domain.Member;
import umc.spring.domain.Region;
import umc.spring.domain.Restaurant;
import umc.spring.domain.common.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RestaurantRegion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    public void setRestaurant(Restaurant restaurant){
        if(this.restaurant != null)
            restaurant.getRestaurantRegionList().remove(this);
        this.restaurant = restaurant;
        restaurant.getRestaurantRegionList().add(this);
    }

    public void setRegion(Region region){
        this.region = region;
    }
}
