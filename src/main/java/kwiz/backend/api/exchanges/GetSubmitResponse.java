package kwiz.backend.api.exchanges;

import java.util.List;

import kwiz.backend.api.dto.QuestionReportDto;
import kwiz.backend.api.dto.Summary;

import lombok.Data;

@Data
public class GetSubmitResponse {
    
    private List<QuestionReportDto> questionReports;

    private Summary summary;

}
