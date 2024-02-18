package book.chat.web.DTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClubMakingForm {
    private Long no;
    @NotBlank
    @Size(min = 3, max = 15)
    private String name;
    @NotBlank
    @Size(min = 10, max = 1500)
    private String introduce;
    @Nullable
    private String location;
}
