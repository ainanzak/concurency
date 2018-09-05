package id.test.concurency.service;

import id.test.concurency.helper.LogUtils;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.text.ParseException;

public interface InputService {
    ResponseEntity<Object> inputData(String request, LogUtils logUtils);

    void validate(String request) throws ParseException;

    Timestamp getServerTimestamp();
}
