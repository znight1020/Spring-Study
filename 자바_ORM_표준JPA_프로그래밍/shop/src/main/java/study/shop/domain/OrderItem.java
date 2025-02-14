package study.shop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class OrderItem {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ORDER_ITEM_ID")
  private Long id;
  @ManyToOne
  @JoinColumn(name = "ORDER_ID")
  private Order order;
  @ManyToOne
  @JoinColumn(name = "ITEM_ID")
  private Item item;
  private Integer orderPrice;
  private Integer count;
}
