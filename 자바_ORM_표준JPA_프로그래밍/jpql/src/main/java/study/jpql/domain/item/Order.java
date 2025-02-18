package study.jpql.domain.item;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

@Entity
@Table(name = "ORDERS")
public class Order {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int orderAmount;

  @Embedded
  private Address address;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PRODUCT_ID")
  private Product product;
}
