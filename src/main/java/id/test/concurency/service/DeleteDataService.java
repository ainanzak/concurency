package id.test.concurency.service;

import id.test.concurency.dto.request.DeleteDto;
import id.test.concurency.helper.LogUtils;
import org.springframework.http.ResponseEntity;

public interface DeleteDataService {
    ResponseEntity<Object> deleteData(DeleteDto deleteDto, LogUtils logUtils);

    void validate(DeleteDto deleteDto);
}
