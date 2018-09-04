package id.test.concurency.dto.response;

import lombok.Data;

@Data
public class InquiryDetail {
    private String from;
    private String to;
    private String rate;
    private String average;
}