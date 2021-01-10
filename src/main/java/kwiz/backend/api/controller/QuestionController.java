package kwiz.backend.api.controller;

import kwiz.backend.api.dto.QuestionDto;
import kwiz.backend.api.exchanges.GetQuestionsResponse;
import kwiz.backend.api.exchanges.GetSubmitResponse;
import kwiz.backend.api.exchanges.SubmitQuestionRequest;
import kwiz.backend.api.service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    private static final String BASE_URL = "quiz/v2/api/questions";

    @Autowired
    private QuestionService questionService;

    @GetMapping(BASE_URL)
    public GetQuestionsResponse getAllQuestions(@RequestParam String moduleId) {
        GetQuestionsResponse response = questionService.getQuestionsInModule(moduleId);
        return response;
    }

    @PostMapping(BASE_URL+"/add")
    public void addQuestion(@RequestBody QuestionDto questionDto) {
        questionService.addQuestion(questionDto);
    }
    
    @PutMapping(BASE_URL+"/update")
    public void updateQuestion(@RequestParam String questionId, @RequestBody QuestionDto questionDto) {
        questionService.updateQuestion(questionId, questionDto);
    }

    @DeleteMapping(BASE_URL+"/delete")
    public void removeQuestion(@RequestParam String questionId) {
        questionService.removeQuestion(questionId);
    }

    @PostMapping(BASE_URL+"/submit")
    public GetSubmitResponse getSubmitResponses(@RequestBody SubmitQuestionRequest request) {
        GetSubmitResponse response = questionService.checkSubmittedAnswer(request.getModuleId(), request.getUserAnswers());
        return response;
    }
}
