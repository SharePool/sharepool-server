package com.sharepool.sharepoolanalytics.rest;

import com.sharepool.sharepoolanalytics.logic.AnalyticsRestRequestHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "Analytics", description = "Get analytics data")
@RestController
@RequestMapping("analytics")
public class AnalyticsResource {

    private final AnalyticsRestRequestHandler requestHandler;

    public AnalyticsResource(AnalyticsRestRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @ApiOperation(
            "Retrieves analytics information for the logged in user in a given timespan." +
                    "The timestamps must be an epoch timestamp."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success. The users analytics data fro the requested time-frame.",
                    response = Map.class),
            @ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAnalyticsForTimeSpan(
            @RequestParam
                    Long startTimestamp,

            @RequestParam(required = false)
                    Long endTimestamp,

            @RequestHeader(HttpHeaders.AUTHORIZATION)
                    String userToken
    ) {
        return ResponseEntity.ok(requestHandler.getAnalyticsForTimeSpan(userToken, startTimestamp, endTimestamp));
    }
}
