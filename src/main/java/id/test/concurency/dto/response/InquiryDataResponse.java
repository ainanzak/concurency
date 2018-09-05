package id.test.concurency.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class InquiryDataResponse {
    private String date;
    private List<InquiryDetailExtend> detailinq;
}

