package id.test.concurency.dto.response;

import lombok.Data;

@Data
public class CustomMessageDto {

    private int status;
    private String message;

    public CustomMessageDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
