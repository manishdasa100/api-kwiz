package kwiz.backend.api.service;

import java.util.List;

import kwiz.backend.api.dto.QuestionDto;
import kwiz.backend.api.dto.UserAnswerDto;
import kwiz.backend.api.exchanges.GetQuestionsResponse;
import kwiz.backend.api.exchanges.GetSubmitResponse;

public interface QuestionService {
    
    GetQuestionsResponse getQuestionsInModule(String moduleId);
    void addQuestion(QuestionDto questionDto);
    void updateQuestion(String questionId, QuestionDto questionDto);
    QuestionDto removeQuestion(String questionId);
    List<QuestionDto> removeAllQuestionsInModule(String moduleId);
    GetSubmitResponse checkSubmittedAnswer(String moduleId, List<UserAnswerDto> userAnswers);
    void deleteAll();
}
