package id.test.concurency.controller;

import id.test.concurency.common.ConstantsRest;
import id.test.concurency.dto.response.InquiryDataResponse;
import id.test.concurency.helper.LogUtils;
import id.test.concurency.service.InquiryDataService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(value = ConstantsRest.INQUIRY_DATA, description = "Inquiry Data Controller")
public class InquiryDataController {
    private InquiryDataService inquiryDataService;

    @Autowired
    public InquiryDataController(InquiryDataService inquiryDataService) {
        this.inquiryDataService = inquiryDataService;
    }

    @GetMapping(ConstantsRest.INQUIRY_DATA)
    @ApiOperation(value = "Inquiry controller",
            notes = "Inquiry data exchange rate, get each rate & avg in 7 days",
//            consumes = "${InquiryDataController.inquiry.date}",
            response = InquiryDataResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = InquiryDataResponse.class)})
    @ResponseBody
    public ResponseEntity<Object> inquiry(@ApiParam(name = "date", value = "String value date format (yyyy-MM-dd)", required = true, format = "yyyy-MM-dd", defaultValue = "2018-08-13") @PathVariable("date") String date) {
        LogUtils logUtils = new LogUtils();

        String uuid = UUID.randomUUID().toString().replace("-", "");
        logUtils.setId(uuid);
        logUtils.info(date);

        return inquiryDataService.inquiryDataExchange(date, logUtils);
    }
}
