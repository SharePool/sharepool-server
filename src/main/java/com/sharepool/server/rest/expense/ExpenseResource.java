package com.sharepool.server.rest.expense;

import com.sharepool.server.logic.expense.ExpenseRestRequestHandler;
import com.sharepool.server.rest.expense.dto.ExpenseConfirmationDto;
import com.sharepool.server.rest.expense.dto.ExpenseRequestResponseDto;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api(tags = "Expenses", description = "Manage expenses of tours.")
@RestController
@RequestMapping("expenses")
public class ExpenseResource {

    private final ExpenseRestRequestHandler requestHandler;

    public ExpenseResource(ExpenseRestRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @PostMapping("/request/{tourId}")
    public ResponseEntity<ExpenseRequestResponseDto> requestExpense(@PathVariable("tourId") @NotNull Long tourId) {
        return ResponseEntity.ok(requestHandler.requestExpense(tourId));
    }

    @PutMapping("/confirmation")
    public ResponseEntity confirmExpense(@RequestBody @Valid ExpenseConfirmationDto expenseConfirmationDto) {
        requestHandler.confirmExpense(expenseConfirmationDto);

        return ResponseEntity.created(null).build();
    }
}
