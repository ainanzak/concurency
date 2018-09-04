package id.test.concurency.dto.response;

import lombok.Data;

import java.util.Objects;

@Data
public class InputRateResponse extends InputExchangeResponse {
    private Double rate;
    private String date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InputRateResponse that = (InputRateResponse) o;
        return Objects.equals(rate, that.rate) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rate, date);
    }
}