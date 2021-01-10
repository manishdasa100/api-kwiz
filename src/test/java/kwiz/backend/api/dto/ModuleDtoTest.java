package kwiz.backend.api.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModuleDtoTest {

    private ObjectMapper mapper;

    @BeforeEach
    private void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    public void deserialisationTest() throws JsonMappingException, JsonProcessingException {

        String json = "{\n"
            + "\"moduleTitle\":\"Java Basics\",\n"
            + "\"moduleDescription\":\"Java Basics questions for beginners\",\n"
            + "\"totalQuestions\":5,\n"
            + "\"avgDuration\":20\n"
        +"}";

        ModuleDto moduleDto = mapper.readValue(json, ModuleDto.class);

        assertNotNull(moduleDto);
        assertEquals("Java Basics", moduleDto.getModuleTitle());
        assertEquals("Java Basics questions for beginners", moduleDto.getModuleDescription());
        assertEquals(0, moduleDto.getTotalQuestions());
        assertEquals(20, moduleDto.getAvgDuration());
    }

    @Test
    public void serialisationTest() throws JsonProcessingException {
        ModuleDto moduleDto = new ModuleDto("1234", "Java Bacics", "Java Basics questions for beginners", 5, 20);

        String json = mapper.writeValueAsString(moduleDto);

        assertTrue(json.contains("moduleId"));
        assertTrue(json.contains("moduleTitle"));
        assertTrue(json.contains("moduleDescription"));
        assertTrue(json.contains("totalQuestions"));
        assertTrue(json.contains("avgDuration"));
    }

}
