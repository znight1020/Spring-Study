package study.shop.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
public abstract class BaseEntity {

  private String createdBy;
  private String createdDate;
  private String lastModifiedBy;
  private String lastModifiedDate;
}
