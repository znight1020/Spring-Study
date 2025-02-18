package hello.basic.domain;

import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
public abstract class BaseEntity {

//  private String createdBy;
  private LocalDateTime createdDate;
//  private String lastModifiedBy;
  private LocalDateTime lastModifiedDate;
}
