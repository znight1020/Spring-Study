package hello.springmvc.basic.requestmapping;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class User {
    private Long id;
    private String name;
    private String email;
    private int age;
}
