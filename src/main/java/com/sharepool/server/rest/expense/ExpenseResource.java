package com.sharepool.server.rest.expense;

import com.sharepool.server.logic.expense.ExpenseRestRequestHandler;
import com.sharepool.server.rest.expense.dto.ExpenseConfirmationDto;
import com.sharepool.server.rest.expense.dto.ExpenseRequestResponseDto;
import com.sharepool.server.rest.expense.dto.ExpensesWrapper;
import com.sharepool.server.rest.expense.dto.PaybackDto;
import com.sharepool.server.rest.util.auth.UserContext;
import io.swagger.annotations.*;
import org.springframework.cache.annotation.CachePut;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.Cacheable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Api(tags = "Expenses", description = "Manage expenses of tours.")
@RestController
@RequestMapping("expenses")
public class ExpenseResource {

    private static final String EXPENSE_CACHE_NAME = "expenses";

    private final ExpenseRestRequestHandler requestHandler;
    private final UserContext userContext;

    public ExpenseResource(
            ExpenseRestRequestHandler requestHandler,
            UserContext userContext
    ) {
        this.requestHandler = requestHandler;
        this.userContext = userContext;
    }

    @ApiOperation(
            value = "Requests a expense for the given tour."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success. The expense has been successfully requested and can be " +
                    "confirmed via the given link (HATEOAS).\n" +
                    "The Method for this link is **PUT**.",
                    response = Resource.class),
            @ApiResponse(code = 404, message = "Failed. The tour does not exist."),
            @ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
    })
    @PostMapping(value = "{tourId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource<ExpenseRequestResponseDto>> requestExpense(
            @ApiParam("The tours id for the requested expense.")
            @PathVariable("tourId")
            @NotNull
                    Long tourId
    ) {
        ExpenseRequestResponseDto expenseRequestResponseDto = requestHandler.requestExpense(tourId);

        return ResponseEntity.ok(
                new Resource<>(
                        expenseRequestResponseDto,
                        linkTo(methodOn(ExpenseResource.class).confirmExpense(tourId)).withSelfRel())
        );
    }

    @ApiOperation(
            value = "Requests a expense for the given tour."
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Success. The expense has been successfully confirmed and persisted."),
            @ApiResponse(code = 404, message = "Failed. The tour does not exist."),
            @ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
    })
    @PutMapping(value = "confirmations/{tourId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CachePut(EXPENSE_CACHE_NAME)
    public ResponseEntity confirmExpense(
            @ApiParam("The tours id for the requested expense.")
            @PathVariable
                    Long tourId
    ) {
        requestHandler.confirmExpense(
                new ExpenseConfirmationDto(
                        tourId,
                        userContext.getUser().getId()));

        return ResponseEntity.created(null).build();
    }

    @ApiOperation(
            value = "Retrieves all expenses for the logged in user."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success. The list contains all expenses for all tours."),
            @ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(EXPENSE_CACHE_NAME)
    public ResponseEntity<ExpensesWrapper> getAllExpenses(
            @ApiParam("Optional filter for the receiver of the expense.")
            @RequestParam(required = false)
                    Long receiverId
    ) {
        return ResponseEntity.ok(requestHandler.getAllExpenses(
                userContext,
                receiverId
                )
        );
    }

    @ApiOperation(
            value = "Sends a payback to the specified user."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success. The list contains all expenses for all tours."),
            @ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CachePut(EXPENSE_CACHE_NAME)
    public ResponseEntity createPayback(
            @ApiParam("The JSON body of the request. Contains parameters of the payback.")
            @RequestBody
            @NotNull
            @Valid
                    PaybackDto paybackDto
    ) {
        requestHandler.createPayback(userContext, paybackDto);

        return ResponseEntity.ok().build();
    }
}
