package kwiz.backend.api.repositoryService;

import java.util.List;

import kwiz.backend.api.dto.QuestionDto;

public interface QuestionRepositoryService {
    
    String REMOVE_BY_QUESTION_ID = "removeByQuestionId";
    String REMOVE_BY_MODULE_ID = "removeByModuleId";

    List<QuestionDto> getQuestions(String moduleId);
    void add(QuestionDto questionDto);
    void update(String questionId, QuestionDto questionDto);
    List<QuestionDto> remove(String type, String questionId);
    void deleteAllQuestions();
}
