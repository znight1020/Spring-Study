package study.shop.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import study.shop.domain.BaseEntity;
import study.shop.domain.order.Order;

@Getter @Setter
@Entity
public class Member extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "MEMBER_ID")
  private Long id;
  private String name;
  @Embedded
  private Address address;
  @OneToMany(mappedBy = "member")
  private List<Order> orders;
}
