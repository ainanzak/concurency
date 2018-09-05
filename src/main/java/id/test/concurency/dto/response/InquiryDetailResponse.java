package id.test.concurency.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class InquiryDetailResponse {
    private List<InquiryDetail> detailinq;
    private String variance;
    private String average;
}

