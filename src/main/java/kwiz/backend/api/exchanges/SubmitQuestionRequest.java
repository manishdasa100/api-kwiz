package kwiz.backend.api.exchanges;

import java.util.List;

import kwiz.backend.api.dto.UserAnswerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubmitQuestionRequest {

    private String moduleId;
    
    private List<UserAnswerDto> userAnswers;

}
