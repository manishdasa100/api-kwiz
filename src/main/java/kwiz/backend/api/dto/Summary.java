package kwiz.backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Summary {

    private int totalCorrect;

    private int totalQuestions;
    
}
