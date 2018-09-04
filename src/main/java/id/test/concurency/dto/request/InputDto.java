package id.test.concurency.dto.request;

import lombok.Data;

import java.util.Objects;

@Data
public class InputDto {
    private String from;
    private String to;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputDto inputDto = (InputDto) o;
        return Objects.equals(from, inputDto.from) &&
                Objects.equals(to, inputDto.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
