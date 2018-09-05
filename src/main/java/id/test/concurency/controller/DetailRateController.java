package id.test.concurency.controller;

import com.google.gson.Gson;
import id.test.concurency.common.Constants;
import id.test.concurency.common.ConstantsRest;
import id.test.concurency.dto.request.InputDto;
import id.test.concurency.dto.response.InputRateResponse;
import id.test.concurency.helper.LogUtils;
import id.test.concurency.service.InquiryDataService;
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
@Api(value = ConstantsRest.TREND_EXCHANGE)
public class DetailRateController {

    private InquiryDataService inquiryDataService;
    private Gson gson;

    @Autowired
    public DetailRateController(@Qualifier(Constants.SERVICE_INQUIRY_DETAIL) InquiryDataService inquiryDataService, Gson gson) {
        this.inquiryDataService = inquiryDataService;
        this.gson = gson;
    }


    @PostMapping(ConstantsRest.TREND_EXCHANGE)
    @ApiOperation(value = "Detail controller",
            notes = "Get detail data until 7 days ago wich spesific exchange",
            response = InputRateResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = InputRateResponse.class)})
    @ResponseBody
    public ResponseEntity<Object> index(@ApiParam(name = "input", value = "json value", required = true, defaultValue = "{\"from\":\"GBP\",\"to\":\"USD\",\"rate\":\"3.36\",\"date\":\"2018-08-31\"}") @RequestBody InputDto inputDto) {
        LogUtils logUtils = new LogUtils();

        String uuid = UUID.randomUUID().toString().replace("-", "");
        logUtils.setId(uuid);
        logUtils.info(gson.toJson(inputDto));

        return inquiryDataService.inquiryDataExchange(gson.toJson(inputDto), logUtils);
    }
}
