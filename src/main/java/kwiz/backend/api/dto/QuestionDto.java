package kwiz.backend.api.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    
    @JsonProperty(access = Access.READ_ONLY)
    private String questionId;

    private String questionTitle;

    private String moduleId;

    private String questionType;

    private Map<Integer, String> options;

    @JsonProperty(access = Access.WRITE_ONLY)
    private List<String> correctAnswers;

    @JsonProperty(access = Access.WRITE_ONLY)
    private int timeToSolve;
}
