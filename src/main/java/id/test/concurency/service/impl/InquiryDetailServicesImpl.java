package id.test.concurency.service.impl;

import com.google.gson.Gson;
import id.test.concurency.common.Constants;
import id.test.concurency.dto.request.InputDto;
import id.test.concurency.dto.response.InquiryDetail;
import id.test.concurency.dto.response.InquiryDetailResponse;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service(Constants.SERVICE_INQUIRY_DETAIL)
public class InquiryDetailServicesImpl implements InquiryDataService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private MsTipeExchangeRepository msTipeExchangeRepository;
    private TrExchangeRepository trExchangeRepository;
    private Gson gson;

    @Autowired
    public InquiryDetailServicesImpl(MsTipeExchangeRepository msTipeExchangeRepository, TrExchangeRepository trExchangeRepository, Gson gson) {
        this.msTipeExchangeRepository = msTipeExchangeRepository;
        this.trExchangeRepository = trExchangeRepository;
        this.gson = gson;
    }

    @Override
    public ResponseEntity<Object> inquiryDataExchange(String request, LogUtils logUtils) {
        InquiryDetailResponse inquiryDataResponse;
        try {

            // validate date input
            validate(request);
            InputDto inputDto = gson.fromJson(request, InputDto.class);

            Date dEnd = new Date();
            Date dStart = getWeekAgo(dEnd);

            // Initiate response
            inquiryDataResponse = new InquiryDetailResponse();
            List<InquiryDetail> inquiryDetailList = new ArrayList<>();

            // get All Tipe Exchange
            MsTipeExchangeEntity msTipeExchangeEntity = msTipeExchangeRepository.findByFromAndTo(inputDto.getFrom(), inputDto.getTo());
            List<TrExchangeEntity> trExchangeEntities = trExchangeRepository.findByIdTipeExchangeAndDateBetween(msTipeExchangeEntity.getIdTipeExchange(), dStart, dEnd);
            String average = Constants.DATA_INSUFFICIENT;
            String variance = Constants.DATA_INSUFFICIENT;

            // validate must bu not null
            if (trExchangeEntities != null) {

                List<Double> num = new ArrayList<>();
                // looping to generate entity rate with date
                for (TrExchangeEntity exchangeEntity : trExchangeEntities) {
                    InquiryDetail inqInquiryDetail = new InquiryDetail();

                    inqInquiryDetail.setRate(Constants.DATA_INSUFFICIENT);
                    inqInquiryDetail.setDate(sdf.format(exchangeEntity.getDate()));

                    if (exchangeEntity.getRate() != null) {
                        inqInquiryDetail.setRate(exchangeEntity.getRate() + "");
                    }
                    inquiryDetailList.add(inqInquiryDetail);

                    num.add(exchangeEntity.getRate());
                }

                logUtils.info(String.format("trExchangeEntities.size() > %s", trExchangeEntities.size()));

                // validate data lenght must be 7 becouse to count rate 7 days ago to count minimal & maximal
                if (trExchangeEntities.size() == 7) {
                    average = String.valueOf(num.stream().mapToDouble(val -> val).average().orElse(0.0));
                    logUtils.info(String.format("average > %s", average));
                    double minimal = num.stream().mapToDouble(val -> val).min().orElse(0.0);
                    logUtils.info(String.format("variance > %f", minimal));
                    double maximal = num.stream().mapToDouble(val -> val).max().orElse(0.0);
                    logUtils.info(String.format("variance > %f", maximal));
                    variance = String.valueOf(maximal - minimal);
                    logUtils.info(String.format("variance > %s", variance));
                }
            }

            inquiryDataResponse.setVariance(variance);
            inquiryDataResponse.setAverage(average);
            inquiryDataResponse.setDetailinq(inquiryDetailList);

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
    public void validate(String request) {
        InputDto inputDto = gson.fromJson(request, InputDto.class);
        if (inputDto.getFrom() == null || inputDto.getFrom().equals("")) {
            throw new NullPointerException("from null");
        }
        if (inputDto.getTo() == null || inputDto.getTo().equals("")) {
            throw new NullPointerException("to null");
        }
    }

    private Date getWeekAgo(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -6);
        return calendar.getTime();
    }
}
