package id.test.concurency.service.impl;

import id.test.concurency.common.Constants;
import id.test.concurency.dto.response.InquiryDataResponse;
import id.test.concurency.dto.response.InquiryDetail;
import id.test.concurency.entity.MsTipeExchangeEntity;
import id.test.concurency.entity.TrExchangeEntity;
import id.test.concurency.helper.CustomResponseHelper;
import id.test.concurency.helper.LogUtils;
import id.test.concurency.repository.MsTipeExchangeRepository;
import id.test.concurency.repository.TrExchangeRepository;
import id.test.concurency.service.InquiryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class InquiryDataServicesImpl implements InquiryDataService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private MsTipeExchangeRepository msTipeExchangeRepository;
    private TrExchangeRepository trExchangeRepository;

    @Autowired
    public InquiryDataServicesImpl(MsTipeExchangeRepository msTipeExchangeRepository, TrExchangeRepository trExchangeRepository) {
        this.msTipeExchangeRepository = msTipeExchangeRepository;
        this.trExchangeRepository = trExchangeRepository;
    }

    @Override
    public ResponseEntity<Object> inquiryDataExchange(String Sdate, LogUtils logUtils) {
        InquiryDataResponse inquiryDataResponse;
        try {
            // validate date input
            validate(Sdate);

            Date dEnd = sdf.parse(Sdate);
            Date dStart = getWeekAgo(dEnd);

            // Initiate response
            inquiryDataResponse = new InquiryDataResponse();
            List<InquiryDetail> inquiryDetailList = new ArrayList<>();

            // get All Tipe Exchange
            List<MsTipeExchangeEntity> msTipeExchangeEntities = msTipeExchangeRepository.findAll();
            for (MsTipeExchangeEntity msTipeExchangeEntity : msTipeExchangeEntities) {
                InquiryDetail inqInquiryDetail = new InquiryDetail();

                TrExchangeEntity trExchangeEntity = trExchangeRepository.findByIdTipeExchangeAndDate(msTipeExchangeEntity.getIdTipeExchange(), dEnd);
                List<TrExchangeEntity> trExchangeEntities = trExchangeRepository.findByIdTipeExchangeAndDateBetween(msTipeExchangeEntity.getIdTipeExchange(), dStart, dEnd);
                String average = Constants.DATA_INSUFFICIENT;

                // validate must bu not null and data lenght must be 7 becouse to count rate 7 days ago
                if (trExchangeEntities != null && trExchangeEntities.size() == 7) {
                    List<Double> num = new ArrayList<>();
                    for (TrExchangeEntity exchangeEntity : trExchangeEntities) {
                        //logUtils.info(String.format("exchangeEntity > %s", exchangeEntity));
                        num.add(exchangeEntity.getRate());
                    }

                    // calculate average from list rate using list
                    average = String.valueOf(num.stream().mapToDouble(val -> val).average().orElse(0.0));
                    logUtils.info(String.format("average > %s", average));
                }

                inqInquiryDetail.setFrom(msTipeExchangeEntity.getFrom());
                inqInquiryDetail.setTo(msTipeExchangeEntity.getTo());
                inqInquiryDetail.setRate(Constants.DATA_INSUFFICIENT);
                inqInquiryDetail.setAverage(average);

                if (trExchangeEntity != null) {
                    inqInquiryDetail.setRate(trExchangeEntity.getRate() + "");
                }

                inquiryDetailList.add(inqInquiryDetail);
            }

            inquiryDataResponse.setDetailinq(inquiryDetailList);
            inquiryDataResponse.setDate(Sdate);

        } catch (ParseException par_ex) {
            logUtils.error("error inputRateExchange date cannot parse", par_ex);
            return CustomResponseHelper.create(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException nullex) {
            logUtils.error("error inputRateExchange null", nullex);
            return CustomResponseHelper.create(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logUtils.error("error inputRateExchange exception", e);
            return CustomResponseHelper.create(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(inquiryDataResponse, HttpStatus.OK);
    }

    @Override
    public void validate(String date) throws ParseException {
        if (date == null || date.equals("")) {
            throw new NullPointerException("date null");
        }
        sdf.parse(date);
    }

    private Date getWeekAgo(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -7);
        return calendar.getTime();
    }
}
