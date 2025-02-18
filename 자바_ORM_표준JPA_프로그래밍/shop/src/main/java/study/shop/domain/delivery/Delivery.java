package study.shop.domain.delivery;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import study.shop.domain.BaseEntity;
import study.shop.domain.member.Address;
import study.shop.domain.order.Order;

@Getter @Setter
@Entity
public class Delivery extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "DELIVERY_ID")
  private Long id;
  @Embedded
  private Address address;
  private DeliveryStatus status;
  @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
  private Order order;
}
