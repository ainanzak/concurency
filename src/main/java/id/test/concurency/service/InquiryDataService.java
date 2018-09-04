package id.test.concurency.service;

import id.test.concurency.helper.LogUtils;
import org.springframework.http.ResponseEntity;

public interface InquiryDataService {
    ResponseEntity<Object> inquiryDataExchange(String date, LogUtils logUtils);

    void validate(String date) throws Exception;
}
