package id.test.concurency.controller;

import com.google.gson.Gson;
import id.test.concurency.common.Constants;
import id.test.concurency.common.ConstantsRest;
import id.test.concurency.dto.request.InputDto;
import id.test.concurency.dto.response.InputExchangeResponse;
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
@Api(value = ConstantsRest.ADD_EXCHANGE)
public class AddExchangeController {
    private InputService inputService;
    private Gson gson;

    @Autowired
    public AddExchangeController(@Qualifier(Constants.SERVICE_ADD_EXCHANGE) InputService inputService, Gson gson) {
        this.inputService = inputService;
        this.gson = gson;
    }

    @PostMapping(ConstantsRest.ADD_EXCHANGE)
    @ApiOperation(value = "Input exchange controller",
            notes = "Input data exchange, and save to database",
            response = InputExchangeResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = InputExchangeResponse.class)})
    @ResponseBody
    public ResponseEntity<Object> index(@ApiParam(name = "input", value = "json value", required = true, defaultValue = "{\"from\":\"GBP\",\"to\":\"USD\"}") @RequestBody InputDto inputDto) {
        LogUtils logUtils = new LogUtils();

        String uuid = UUID.randomUUID().toString().replace("-", "");
        logUtils.setId(uuid);
        logUtils.info(String.format("/add/echange > %s", gson.toJson(inputDto)));

        return inputService.inputData(gson.toJson(inputDto), logUtils);
    }
}
