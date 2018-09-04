package id.test.concurency.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ms_tipe_exchange")
public class MsTipeExchangeEntity {
    private long idTipeExchange;
    private String from;
    private String to;
    private Timestamp date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipe_exchange")
    public long getIdTipeExchange() {
        return idTipeExchange;
    }

    public void setIdTipeExchange(long idTipeExchange) {
        this.idTipeExchange = idTipeExchange;
    }

    @Basic
    @Column(name = "\"from\"")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Basic
    @Column(name = "\"to\"")
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Basic
    @Column(name = "\"date\"")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MsTipeExchangeEntity that = (MsTipeExchangeEntity) o;
        return idTipeExchange == that.idTipeExchange &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipeExchange, from, to, date);
    }

    @Override
    public String toString() {
        return "MsTipeExchangeEntity{" +
                "idTipeExchange=" + idTipeExchange +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", date=" + date +
                '}';
    }
}
