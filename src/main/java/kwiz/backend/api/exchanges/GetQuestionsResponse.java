package kwiz.backend.api.exchanges;

import java.util.List;

import kwiz.backend.api.dto.QuestionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetQuestionsResponse {
    
    List<QuestionDto> questions;

}
