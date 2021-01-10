package kwiz.backend.api.repositoryService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwiz.backend.api.dto.QuestionDto;
import kwiz.backend.api.entity.QuestionEntity;
import kwiz.backend.api.repositoryService.QuestionRepositoryService;
import kwiz.backend.api.repositoryService.QuestionRepositoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class QuestionRepositoryServiceTest {
    
    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private QuestionRepositoryServiceImpl service;

    private List<QuestionEntity> questions;

    private List<QuestionEntity> loadDummyEntities() {

        Map<Integer, String> options = new HashMap<>();
        options.put(1, "star");
        options.put(2, "moon");
        options.put(3, "galaxy");

        return Arrays.asList(
            new QuestionEntity("1234", "abcd", "5678", "Objective", options, Arrays.asList("1", "2"), 10),
            new QuestionEntity("2222", "efgh", "3333", "Subjective", null, null, 10)
        );
    }

    @BeforeEach
    private void setup() {
        questions = loadDummyEntities();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getQuestionsTest() {

        when(mongoTemplate.find(any(Query.class), eq(QuestionEntity.class), anyString())).thenReturn(questions, Collections.EMPTY_LIST);

        List<QuestionDto> questionDtos = service.getQuestions("5678");

        assertEquals(2, questionDtos.size());
        assertEquals("1234", questionDtos.get(0).getQuestionId());

        questionDtos = service.getQuestions("9876");

        assertEquals(0, questionDtos.size());
    }

    @Test
    public void saveQuestionTest() {

        service.add(new QuestionDto());

        verify(mongoTemplate, times(1)).save(any(QuestionEntity.class));

    }

    @Test
    public void updateQuestionTest() {

        when(mongoTemplate.findOne(any(Query.class), eq(QuestionEntity.class), anyString())).thenReturn(null, new QuestionEntity());

        service.update("questionId", new QuestionDto());

        verify(mongoTemplate, times(0)).save(any(QuestionEntity.class));

        service.update("questionId", new QuestionDto());

        verify(mongoTemplate, times(1)).save(any(QuestionEntity.class));

    }

    @Test
    public void removeQuestionTest() {

        when(mongoTemplate.findAndRemove(any(Query.class), eq(QuestionEntity.class), anyString())).thenReturn(questions.get(0));

        QuestionDto removedQuestion = service.remove(QuestionRepositoryService.REMOVE_BY_QUESTION_ID, "1234").get(0);

        assertEquals("5678", removedQuestion.getModuleId());

        when(mongoTemplate.findAllAndRemove(any(Query.class), eq(QuestionEntity.class), anyString())).thenReturn(questions);
        
        List<QuestionDto> removedQs = service.remove(QuestionRepositoryService.REMOVE_BY_MODULE_ID, "5678");

        assertEquals(2, removedQs.size());

        // verify(mongoTemplate, times(1)).remove(any(Query.class), eq(QuestionEntity.class), anyString());
    }

}
