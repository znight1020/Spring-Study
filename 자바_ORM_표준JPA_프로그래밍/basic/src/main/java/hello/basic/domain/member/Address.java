package hello.basic.domain.member;

import jakarta.persistence.Embeddable;
import java.io.ObjectStreamClass;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

  private String city;
  private String street;
  private String zipcode;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Address address = (Address) o;
    return Objects.equals(city, address.getCity())
        && Objects.equals(street, address.getStreet())
        && Objects.equals(zipcode, address.getZipcode());
  }

  @Override
  public int hashCode() {
    return Objects.hash(city, street, zipcode);
  }
}
