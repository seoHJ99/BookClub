package book.chat.club.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ClubMakingForm {
    @Size(min = 3, max = 15)
    private String name;
    @Size(min = 10, max = 1500)
    private String introduce;
    @Range(min = 5, max = 200)
    private int range;
    @Nullable
    private String location;
//    @NotBlank
    private String profile;
}
