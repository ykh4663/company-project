package datajpaptc.forPractice.dto;

import datajpaptc.forPractice.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.teamName = member.getTeam().getName();
    }
}
