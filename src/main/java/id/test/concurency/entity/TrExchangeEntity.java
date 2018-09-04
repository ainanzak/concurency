package id.test.concurency.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tr_exchange")
public class TrExchangeEntity {
    private long idExchange;
    private Long idTipeExchange;
    private Double rate;
    private Date date;
    private Timestamp createat;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exchange")
    public long getIdExchange() {
        return idExchange;
    }

    public void setIdExchange(long idExchange) {
        this.idExchange = idExchange;
    }

    @Basic
    @Column(name = "id_tipe_exchange")
    public Long getIdTipeExchange() {
        return idTipeExchange;
    }

    public void setIdTipeExchange(Long idTipeExchange) {
        this.idTipeExchange = idTipeExchange;
    }

    @Basic
    @Column(name = "rate")
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "createat")
    public Timestamp getCreateat() {
        return createat;
    }

    public void setCreateat(Timestamp createat) {
        this.createat = createat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrExchangeEntity that = (TrExchangeEntity) o;
        return idExchange == that.idExchange &&
                Objects.equals(idTipeExchange, that.idTipeExchange) &&
                Objects.equals(rate, that.rate) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExchange, idTipeExchange, rate, date);
    }

    @Override
    public String toString() {
        return "TrExchangeEntity{" +
                "idExchange=" + idExchange +
                ", idTipeExchange=" + idTipeExchange +
                ", rate='" + rate + '\'' +
                ", date=" + date +
                ", createat=" + createat +
                '}';
    }
}
