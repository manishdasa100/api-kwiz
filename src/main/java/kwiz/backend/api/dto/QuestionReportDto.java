package kwiz.backend.api.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionReportDto {
    
    private String questionTitle;

    private String questionType;

    private Map<Integer, String> options;

    private List<String> userAnswer;

    private List<String> correctAnswer;

    private boolean ansCorrect;

}
