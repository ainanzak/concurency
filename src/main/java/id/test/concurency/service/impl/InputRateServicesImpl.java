package id.test.concurency.service.impl;

import com.google.gson.Gson;
import id.test.concurency.common.Constants;
import id.test.concurency.dto.request.InputRateDto;
import id.test.concurency.dto.response.InputRateResponse;
import id.test.concurency.entity.MsTipeExchangeEntity;
import id.test.concurency.entity.TrExchangeEntity;
import id.test.concurency.helper.CustomResponseHelper;
import id.test.concurency.helper.LogUtils;
import id.test.concurency.repository.MsTipeExchangeRepository;
import id.test.concurency.repository.TrExchangeRepository;
import id.test.concurency.service.InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service(Constants.SERVICE_ADD_RATE)
public class InputRateServicesImpl implements InputService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private MsTipeExchangeRepository msTipeExchangeRepository;
    private TrExchangeRepository trExchangeRepository;

    private Gson gson;

    @Autowired
    public InputRateServicesImpl(MsTipeExchangeRepository msTipeExchangeRepository, TrExchangeRepository trExchangeRepository, Gson gson) {
        this.msTipeExchangeRepository = msTipeExchangeRepository;
        this.trExchangeRepository = trExchangeRepository;
        this.gson = gson;
    }

    @Override
    public ResponseEntity<Object> inputData(String request, LogUtils logUtils) {

        InputRateResponse inputRateResponse;
        try {
            InputRateDto inputDto = gson.fromJson(request, InputRateDto.class);
            // validation request input
            validate(request);

            // initiate response
            inputRateResponse = gson.fromJson(gson.toJson(inputDto), InputRateResponse.class);

            // check from to in table msTipeExchange
            MsTipeExchangeEntity msTipeExchangeEntity = msTipeExchangeRepository.findByFromAndTo(inputDto.getFrom(), inputDto.getTo());
            if (msTipeExchangeEntity == null) {
                inputRateResponse.setStatus("please input from an to.");
                return new ResponseEntity<>(inputRateResponse, HttpStatus.CONFLICT);
            }

            Date date = sdf.parse(inputDto.getDate());

            // Check date is already in database or not
            TrExchangeEntity checkExchangeEntity = trExchangeRepository.findByIdTipeExchangeAndDate(msTipeExchangeEntity.getIdTipeExchange(), date);
            if (checkExchangeEntity != null) {
                inputRateResponse.setStatus("Data already added in database");
                return new ResponseEntity<>(inputRateResponse, HttpStatus.OK);
            }

            // save to database trExchangeEntity
            TrExchangeEntity trExchangeEntity = new TrExchangeEntity();
            trExchangeEntity.setIdTipeExchange(msTipeExchangeEntity.getIdTipeExchange());
            trExchangeEntity.setDate(date);
            trExchangeEntity.setRate(inputDto.getRate());
            trExchangeEntity.setCreateat(getServerTimestamp());
            trExchangeRepository.save(trExchangeEntity);

            inputRateResponse.setStatus("Success");

        } catch (ParseException parex) {
            logUtils.error("error InputRateServicesImpl date cannot parse", parex);
            return CustomResponseHelper.create(HttpStatus.BAD_REQUEST);
        } catch (ArithmeticException arimatex) {
            logUtils.error("error InputRateServicesImpl rate minus", arimatex);
            return CustomResponseHelper.create(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException nullex) {
            logUtils.error("error InputRateServicesImpl null", nullex);
            return CustomResponseHelper.create(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logUtils.error("error InputRateServicesImpl exception", e);
            return CustomResponseHelper.create(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(inputRateResponse, HttpStatus.CREATED);
    }

    @Override
    public void validate(String request) throws ParseException {
        InputRateDto inputDto = gson.fromJson(request, InputRateDto.class);
        if (inputDto.getDate() == null || inputDto.getDate().equals("")) {
            throw new NullPointerException("date null");
        }
        if (inputDto.getFrom() == null || inputDto.getFrom().equals("")) {
            throw new NullPointerException("from null");
        }
        if (inputDto.getTo() == null || inputDto.getTo().equals("")) {
            throw new NullPointerException("to null");
        }
        if (inputDto.getRate() == null) {
            throw new NullPointerException("rate null");
        }

        sdf.parse(inputDto.getDate());

        if (inputDto.getRate() < 0) {
            throw new ArithmeticException("rate minus");
        }
    }

    @Override
    public Timestamp getServerTimestamp() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return new Timestamp(date.getTime());
    }
}
