package study.jpql.domain.dto;

import lombok.Builder;

@Builder
public record MemberDTO(
    String username,
    int age
) {
}
