package com.sharepool.server.rest.expense;

import com.sharepool.server.logic.expense.ExpenseRestRequestHandler;
import com.sharepool.server.rest.expense.dto.ExpenseConfirmationDto;
import com.sharepool.server.rest.expense.dto.ExpenseRequestResponseDto;
import com.sharepool.server.rest.util.auth.UserContext;
import io.swagger.annotations.Api;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Api(tags = "Expenses", description = "Manage expenses of tours.")
@RestController
@RequestMapping("expenses")
public class ExpenseResource {

    private final ExpenseRestRequestHandler requestHandler;
    private final UserContext userContext;

    public ExpenseResource(
            ExpenseRestRequestHandler requestHandler,
            UserContext userContext
    ) {
        this.requestHandler = requestHandler;
        this.userContext = userContext;
    }

    @PostMapping("{tourId}")
    public ResponseEntity<Resource<ExpenseRequestResponseDto>> requestExpense(
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

    @PutMapping("confirmations/{tourId}")
    public ResponseEntity confirmExpense(@PathVariable Long tourId) {
        requestHandler.confirmExpense(
                new ExpenseConfirmationDto(
                        tourId,
                        userContext.getUser().getId()));

        return ResponseEntity.created(null).build();
    }
}
