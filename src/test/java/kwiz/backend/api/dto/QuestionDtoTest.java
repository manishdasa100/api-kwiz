package kwiz.backend.api.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuestionDtoTest {
    
    private ObjectMapper mapper;

    @BeforeEach
    private void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    public void deserialisationTest() throws JsonMappingException, JsonProcessingException {

        String json = "{\n"
            + "\"questionId\":\"1234\",\n"
            + "\"questionTitle\":\"abcd\",\n"
            + "\"moduleId\":\"56789\",\n"
            + "\"questionType\":\"Objective\",\n"
            + "\"options\":{\"1\":\"ab\",\"2\":\"cd\"},\n"
            + "\"correctAnswers\":[\"1\"],\n"
            + "\"timeToSolve\":10"
        +"}";

        QuestionDto questionDto = mapper.readValue(json, QuestionDto.class);

        assertNotNull(questionDto);
        assertNull(questionDto.getQuestionId());
        assertEquals("abcd", questionDto.getQuestionTitle());
        assertEquals("56789", questionDto.getModuleId());
        assertEquals("Objective", questionDto.getQuestionType());
        assertEquals("ab", questionDto.getOptions().get(1));
        assertEquals(10, questionDto.getTimeToSolve());
    }

    @Test
    public void serialisationTest() throws JsonProcessingException {
        QuestionDto moduleDto = new QuestionDto("1234", "What is public static void main()", "5678", "Subjective", null, Collections.EMPTY_LIST,10);

        String json = mapper.writeValueAsString(moduleDto);

        assertTrue(json.contains("questionId"));
        assertTrue(json.contains("questionTitle"));
        assertTrue(json.contains("moduleId"));
        assertTrue(json.contains("questionType"));
        assertTrue(json.contains("options"));
        assertFalse(json.contains("correctAnswers"));
        assertFalse(json.contains("timeToSolve"));
    }

}
