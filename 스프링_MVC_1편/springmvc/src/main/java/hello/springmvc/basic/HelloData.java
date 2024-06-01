package hello.springmvc.basic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "HelloData", description = "테스트 DTO")
public class HelloData {
    @Schema(description = "사용자 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "spring")
    private String username;
    @Schema(description = "사용자 나이", requiredMode = Schema.RequiredMode.REQUIRED, example = "25")
    private int age;
}
