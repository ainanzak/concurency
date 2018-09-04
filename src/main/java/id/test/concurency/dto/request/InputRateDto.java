package id.test.concurency.dto.request;

import lombok.Data;

import java.util.Objects;

@Data
public class InputRateDto extends InputDto {
    private String date;
    private Double rate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InputRateDto that = (InputRateDto) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, rate);
    }
}
