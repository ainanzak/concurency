package id.test.concurency.controller;

import com.google.gson.Gson;
import id.test.concurency.common.Constants;
import id.test.concurency.common.ConstantsRest;
import id.test.concurency.dto.request.InputRateDto;
import id.test.concurency.dto.response.InputRateResponse;
import id.test.concurency.helper.LogUtils;
import id.test.concurency.service.InputService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(value = ConstantsRest.ADD_RATE)
public class AddRateExchangeController {
    private InputService inputService;
    private Gson gson;

    @Autowired
    public AddRateExchangeController(@Qualifier(Constants.SERVICE_ADD_RATE) InputService inputService, Gson gson) {
        this.inputService = inputService;
        this.gson = gson;
    }

    @PostMapping(ConstantsRest.ADD_RATE)
    @ApiOperation(value = "Input Rate Exchange Controller",
            notes = "Input data rate exchange, and save to database",
            response = InputRateResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = InputRateResponse.class)})
    @ResponseBody
    public ResponseEntity<Object> index(@ApiParam(name = "input", value = "json value", required = true, defaultValue = "{\"from\":\"GBP\",\"to\":\"USD\",\"rate\":\"3.36\",\"date\":\"2018-08-31\"}") @RequestBody InputRateDto inputRateDto) {
        LogUtils logUtils = new LogUtils();

        String uuid = UUID.randomUUID().toString().replace("-", "");
        logUtils.setId(uuid);
        logUtils.info(String.format("/add/rate > %s", gson.toJson(inputRateDto)));

        return inputService.inputData(gson.toJson(inputRateDto), logUtils);
    }
}
