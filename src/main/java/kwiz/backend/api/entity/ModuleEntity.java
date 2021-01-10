package kwiz.backend.api.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "modules")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ModuleEntity {

    @Id
    private String moduleId;

    @Indexed
    private String moduleTitle;

    private String moduleDescription;

    private int totalQuestions;

    private int avgDuration;
}
