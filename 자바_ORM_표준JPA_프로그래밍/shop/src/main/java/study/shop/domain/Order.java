package study.shop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Table(name = "ORDERS")
@Entity
public class Order {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ORDER_ID")
  private Long id;
  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member member;
  private LocalDateTime orderDate;
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems;

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }
}
