package kwiz.backend.api.entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "questions")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QuestionEntity {
    
    @Id
    private String questionId;

    private String questionTitle;

    @Indexed
    private String moduleId;

    private String questionType;

    private Map<Integer, String> options;

    private List<String> correctAnswers;

    private int timeToSolve;

}
