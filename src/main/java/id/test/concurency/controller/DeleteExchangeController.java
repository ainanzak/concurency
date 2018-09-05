package id.test.concurency.controller;

import com.google.gson.Gson;
import id.test.concurency.common.ConstantsRest;
import id.test.concurency.dto.request.DeleteDto;
import id.test.concurency.dto.response.InputExchangeResponse;
import id.test.concurency.helper.LogUtils;
import id.test.concurency.service.DeleteDataService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Api(value = ConstantsRest.DELETE_EXCHANGE)
public class DeleteExchangeController {

    private DeleteDataService deleteDataService;
    private Gson gson;

    @Autowired
    public DeleteExchangeController(DeleteDataService deleteDataService, Gson gson) {
        this.deleteDataService = deleteDataService;
        this.gson = gson;
    }

    @DeleteMapping(ConstantsRest.DELETE_EXCHANGE)
    @ApiOperation(value = "Delete Exchange controller",
            notes = "delete exchange from database",
            response = InputExchangeResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = InputExchangeResponse.class)})
    @ResponseBody
    public ResponseEntity<Object> delete(@ApiParam(name = "input", value = "json value", required = true, defaultValue = "{\"from\":\"GBP\",\"to\":\"USD\"") @RequestBody DeleteDto deleteDto) {
        LogUtils logUtils = new LogUtils();

        String uuid = UUID.randomUUID().toString().replace("-", "");
        logUtils.setId(uuid);
        logUtils.info(gson.toJson(deleteDto));

        return deleteDataService.deleteData(deleteDto, logUtils);
    }
}
