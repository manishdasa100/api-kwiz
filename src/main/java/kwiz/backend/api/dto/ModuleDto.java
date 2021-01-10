package kwiz.backend.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDto {
   
    @JsonProperty(access = Access.READ_ONLY)
    private String moduleId;

    private String moduleTitle;

    private String moduleDescription;

    @JsonProperty(access = Access.READ_ONLY)
    private int totalQuestions;
    
    @JsonProperty(access = Access.READ_ONLY)
    private int avgDuration;    
}
