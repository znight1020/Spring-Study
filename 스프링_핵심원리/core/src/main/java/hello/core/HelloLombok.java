package hello.core;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter // getter
@Setter // setter
@NoArgsConstructor // 생성자
@ToString
public class HelloLombok {
    private String name;
    private int age;

    public static void main(String[] args){
       HelloLombok helloLombok = new HelloLombok();
       helloLombok.setName("이현수");

       String name = helloLombok.getName();
        System.out.println(helloLombok.toString());
    }
}
