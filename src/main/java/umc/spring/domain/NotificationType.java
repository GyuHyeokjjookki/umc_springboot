package umc.spring.domain;

import lombok.*;
import umc.spring.domain.mapping.MemberNotificationAgree;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NotificationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String typeName;

    @OneToMany(mappedBy = "notificationType", cascade = CascadeType.ALL)
    private List<Notification> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "notificationType", cascade = CascadeType.ALL)
    private List<MemberNotificationAgree> memberNotificationAgreeList = new ArrayList<>();
}
