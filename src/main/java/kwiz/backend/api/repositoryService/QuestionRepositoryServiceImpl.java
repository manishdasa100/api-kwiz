package kwiz.backend.api.repositoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kwiz.backend.api.dto.QuestionDto;
import kwiz.backend.api.entity.QuestionEntity;
import kwiz.backend.api.exceptions.ResourceNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class QuestionRepositoryServiceImpl implements QuestionRepositoryService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public List<QuestionDto> getQuestions(String moduleId) {
        List<QuestionEntity> questionsEntities = mongoTemplate.find(new Query(Criteria.where("moduleId").is(moduleId)), QuestionEntity.class, "questions");
        
        List<QuestionDto> questions = new ArrayList<>();

        for (QuestionEntity questionEntity:questionsEntities) {
            questions.add(mapper.map(questionEntity, QuestionDto.class));
        }

        return questions;
    }

    @Override
    public void add(QuestionDto questionDto) {
        
        QuestionEntity questionEntity = QuestionEntity.builder()
                                                        .moduleId(questionDto.getModuleId())
                                                        .questionTitle(questionDto.getQuestionTitle())
                                                        .questionType(questionDto.getQuestionType())
                                                        .options(questionDto.getOptions())
                                                        .correctAnswers(questionDto.getCorrectAnswers())
                                                        .timeToSolve(questionDto.getTimeToSolve())
                                                        .build();

        mongoTemplate.save(questionEntity);

    }

    @Override
    public void update(String questionId, QuestionDto questionDto) {
        
        String questionTitle = questionDto.getQuestionTitle();
        String questionType = questionDto.getQuestionType();
        Map<Integer, String> options = questionDto.getOptions();
        List<String> correctAns = questionDto.getCorrectAnswers();

        QuestionEntity entity = mongoTemplate.findOne(new Query(Criteria.where("questionId").is(questionId)), QuestionEntity.class, "questions");

        if (entity != null) {
            entity.setQuestionTitle(questionTitle);
            entity.setQuestionType(questionType);
            entity.setOptions(options);
            entity.setCorrectAnswers(correctAns);

            mongoTemplate.save(entity);
        }
    }


    /**
     * Returns the deleted questions as a list. Deletion is made on the basis of quesitionId or moduleId
     * @param type = Type of deletion (REMOVE_BY_QUESTIONID, REMOVE_BY_MODULEID)
     * @param id = id
     */
    @Override
    public List<QuestionDto> remove(String type, String id) {

        List<QuestionDto> deletedQs = new ArrayList<QuestionDto>();

        if (type.equals(QuestionRepositoryService.REMOVE_BY_QUESTION_ID)) {

            // When question is removed based on question Id. Here single question will be deleted.
            QuestionEntity entity = mongoTemplate.findAndRemove(new Query(Criteria.where("questionId").is(id)), QuestionEntity.class, "questions");
            
            if (entity == null) throw new ResourceNotFoundException();

            deletedQs.add(mapper.map(entity, QuestionDto.class));

        } else {

            // When all questions matching a moduleId are deleted. Can be more than one.

            List<QuestionEntity> deletedEntities = mongoTemplate.findAllAndRemove(new Query(Criteria.where("moduleId").is(id)), QuestionEntity.class, "questions");

            for (QuestionEntity entity : deletedEntities) {

                deletedQs.add(mapper.map(entity, QuestionDto.class));
            }
        }

        return deletedQs;

    }

    @Override
    public void deleteAllQuestions() {

        mongoTemplate.dropCollection("questions");
    }
    
}
