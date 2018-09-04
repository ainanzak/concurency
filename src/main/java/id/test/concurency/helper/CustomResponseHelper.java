package id.test.concurency.helper;

import id.test.concurency.dto.response.CustomMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomResponseHelper {

    private static final String EMPTY = "";
    private static final String MESSAGE_OK = "hi, welcome :)";
    private static final String MESSAGE_NOT_FOUND = "data not found.";
    private static final String MESSAGE_INTERNAL_SERVER_ERROR = "an error occurred.";
    private static final String MESSAGE_BAD_REQUEST = "bad request.";

    private CustomResponseHelper() {
    }

    public static ResponseEntity<Object> create(HttpStatus status) {
        String message;
        switch (status) {
            case OK:
                message = MESSAGE_OK;
                break;
            case NOT_FOUND:
                message = MESSAGE_NOT_FOUND;
                break;
            case INTERNAL_SERVER_ERROR:
                message = MESSAGE_INTERNAL_SERVER_ERROR;
                break;
            case BAD_REQUEST:
                message = MESSAGE_BAD_REQUEST;
                break;
            default:
                message = EMPTY;
                break;
        }

        return create(status, message);
    }

    private static ResponseEntity<Object> create(HttpStatus status, String message) {
        return new ResponseEntity<>(new CustomMessageDto(status.value(), message), status);
    }

}
