package com.sharepool.sharepoolanalytics.rest;

import com.sharepool.sharepoolanalytics.domain.AnalyticsEntry;
import com.sharepool.sharepoolanalytics.logic.AnalyticsRestRequestHandler;
import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.time.LocalDate;
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
            "Retrieves analytics information for the logged in user in a given timespan."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success. The users analytics data for the requested time-frame."),
            @ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<LocalDate, AnalyticsEntry>> getAnalyticsForTimeSpan(
            @ApiParam("Start-Date for analytics time-series. (must be ISO format: \"2019-06-15\")")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate from,

            @ApiParam("End-Date for analytics time-series. (must be ISO format: \"2019-06-20\")")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate to,

            @RequestHeader(HttpHeaders.AUTHORIZATION)
                    String userToken
    ) {
        return ResponseEntity.ok(requestHandler.getAnalyticsForTimeSpan(userToken, from, to));
    }
}
