package kwiz.backend.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwiz.backend.api.dto.QuestionDto;
import kwiz.backend.api.dto.QuestionReportDto;
import kwiz.backend.api.dto.Summary;
import kwiz.backend.api.dto.UserAnswerDto;
import kwiz.backend.api.exceptions.ResourceNotFoundException;
import kwiz.backend.api.exchanges.GetQuestionsResponse;
import kwiz.backend.api.exchanges.GetSubmitResponse;
import kwiz.backend.api.repositoryService.QuestionRepositoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepositoryService questionRepositoryService;

    @Autowired
    private ModuleService moduleService;

    private boolean isAnswerCorrect(List<String> actualAns, List<String> userAns) {

        if (actualAns.size() != userAns.size()) {
            return false;
        } 

        for (int i = 0; i < actualAns.size(); i++) {
            if (!actualAns.get(i).equals(userAns.get(i))) return false;
        }

        return true;
    }

    @Override
    public GetQuestionsResponse getQuestionsInModule(String moduleId) {
        List<QuestionDto> questions = questionRepositoryService.getQuestions(moduleId);
        GetQuestionsResponse response = new GetQuestionsResponse();
        response.setQuestions(questions);
        return response;
    }

    @Override
    public void addQuestion(QuestionDto questionDto) {
        boolean moduleExist = moduleService.isModuleExist(questionDto.getModuleId());
        if (moduleExist) {
            questionRepositoryService.add(questionDto);
            moduleService.updateQuestionsCountBy(questionDto.getModuleId(), 1);
            moduleService.updateAvgTimeToComplete(questionDto.getModuleId(), questionDto.getTimeToSolve());
        } else
            throw new ResourceNotFoundException("The question belongs to an invalid module");
    }

    @Override
    public void updateQuestion(String questionId, QuestionDto questionDto) {
        questionRepositoryService.update(questionId, questionDto);
    }

    @Override
    public QuestionDto removeQuestion(String questionId) {

        QuestionDto removedQuestion = questionRepositoryService.remove(QuestionRepositoryService.REMOVE_BY_QUESTION_ID, questionId).get(0);

        moduleService.updateQuestionsCountBy(removedQuestion.getModuleId(), -1);

        // The time to solve the question should be deducted from the total time to solve the module. Hence the second argument is negative
        moduleService.updateAvgTimeToComplete(removedQuestion.getModuleId(), -removedQuestion.getTimeToSolve());

        return removedQuestion;
    }

    @Override
    public List<QuestionDto> removeAllQuestionsInModule(String moduleId) {
        
        return questionRepositoryService.remove(QuestionRepositoryService.REMOVE_BY_MODULE_ID, moduleId);
    }

    @Override
    public GetSubmitResponse checkSubmittedAnswer(String moduleId, List<UserAnswerDto> userAnswersDtos) {
        
        List<QuestionDto> questions = getQuestionsInModule(moduleId).getQuestions();

        Map<String, QuestionDto> questionIdToQuestionDtoMap = getQuestionsMap(questions);

        List<QuestionReportDto> questionReports = new ArrayList<>();

        int correctAnsCount = 0;

        for (UserAnswerDto userAnswerDto:userAnswersDtos) {
            String questionId = userAnswerDto.getQuestionId();

            QuestionDto question = questionIdToQuestionDtoMap.get(questionId);

            if (question == null) throw new ResourceNotFoundException();

            List<String> correctAns = question.getCorrectAnswers();

            List<String> userAns = userAnswerDto.getAnswer();

            boolean ansCorrect = isAnswerCorrect(correctAns, userAns);

            if (ansCorrect) correctAnsCount++;

            QuestionReportDto questionReport = QuestionReportDto.builder()
                                                            .questionTitle(question.getQuestionTitle())
                                                            .questionType(question.getQuestionType())
                                                            .options(question.getOptions())
                                                            .userAnswer(userAns)
                                                            .correctAnswer(correctAns)
                                                            .ansCorrect(ansCorrect)
                                                            .build();

            questionReports.add(questionReport);
        }

        Summary summary = new Summary(correctAnsCount, questions.size());

        GetSubmitResponse submitResponse = new GetSubmitResponse();

        submitResponse.setQuestionReports(questionReports);

        submitResponse.setSummary(summary);

        return submitResponse;
    }

    private Map<String, QuestionDto> getQuestionsMap(List<QuestionDto> questionDtos) {

        Map<String, QuestionDto> questionIdToQuestionDtoMap = new HashMap<>();

        for (QuestionDto questionDto:questionDtos) {
            questionIdToQuestionDtoMap.put(questionDto.getQuestionId(), questionDto);
        }

        return questionIdToQuestionDtoMap;

    }

    @Override
    public void deleteAll() {
        questionRepositoryService.deleteAllQuestions();
    }
    
}
