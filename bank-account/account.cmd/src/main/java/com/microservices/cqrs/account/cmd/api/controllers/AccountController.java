package com.microservices.cqrs.account.cmd.api.controllers;

import static org.springframework.http.HttpStatus.CREATED;

import com.microservices.cqrs.account.cmd.api.commands.CloseAccountCommand;
import com.microservices.cqrs.account.cmd.api.commands.DepositFundsCommand;
import com.microservices.cqrs.account.cmd.api.commands.OpenAccountCommand;
import com.microservices.cqrs.account.cmd.api.commands.WithdrawFundsCommand;
import com.microservices.cqrs.account.cmd.api.dto.OpenAccountResponse;
import com.microservices.cqrs.account.common.dto.BaseResponse;
import com.microservices.cqrs.core.commands.CommandDispatcher;
import com.microservices.cqrs.core.exceptions.AggregateNotFoundException;
import java.text.MessageFormat;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/bankAccount")
@Slf4j
public class AccountController {
  @Autowired private CommandDispatcher commandDispatcher;

  @PostMapping("/open")
  public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
    String id = UUID.randomUUID().toString();
    command.setId(id);
    try {
      commandDispatcher.send(command);
      return new ResponseEntity<>(
          new OpenAccountResponse("Bank account created successfully", id), CREATED);
    } catch (IllegalStateException e) {
      log.warn("Client made a bad request - {}", e.toString());
      return ResponseEntity.badRequest().body(new OpenAccountResponse(e.toString(), id));
    } catch (Exception e) {
      final String message =
          MessageFormat.format(
              "Error while processing request to open new bank account for id: {0}", id);
      log.error(message, e);
      return ResponseEntity.internalServerError().body(new OpenAccountResponse(message, id));
    }
  }

  @PutMapping("/deposit/{id}")
  public ResponseEntity<BaseResponse> depositFunds(
      @PathVariable(value = "id") String id, @RequestBody DepositFundsCommand command) {
    try {
      command.setId(id);
      commandDispatcher.send(command);
      return ResponseEntity.ok(new BaseResponse("Funds Deposited successfully"));
    } catch (IllegalStateException | AggregateNotFoundException e) {
      log.warn("Client made a bad request - {}", e.toString());
      return ResponseEntity.badRequest().body(new BaseResponse(e.toString()));
    } catch (Exception e) {
      final String message =
          MessageFormat.format(
              "Error while processing request to deposit to bank account with id: {0}", id);
      log.error(message, e);
      return ResponseEntity.internalServerError().body(new BaseResponse(message));
    }
  }

  @PutMapping("/withdrawFunds/{id}")
  public ResponseEntity<BaseResponse> withdrawFunds(
      @PathVariable(value = "id") String id, @RequestBody WithdrawFundsCommand command) {
    try {
      command.setId(id);
      commandDispatcher.send(command);
      return ResponseEntity.ok(new BaseResponse("Funds withdrawn successfully"));
    } catch (IllegalStateException | AggregateNotFoundException e) {
      log.warn("Client made a bad request - {}", e.toString());
      return ResponseEntity.badRequest().body(new BaseResponse(e.toString()));
    } catch (Exception e) {
      final String message =
          MessageFormat.format(
              "Error while processing request to withdraw funds from bank account with id: {0}",
              id);
      log.error(message, e);
      return ResponseEntity.internalServerError().body(new BaseResponse(message));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<BaseResponse> deleteAccount(@PathVariable(value = "id") String id) {
    try {
      commandDispatcher.send(new CloseAccountCommand(id));
      return new ResponseEntity<>(
          new BaseResponse(
              MessageFormat.format("Bank Account deleted successfully with id: {0}", id)),
          HttpStatus.OK);
    } catch (IllegalStateException | AggregateNotFoundException e) {
      log.warn("Client made a bad request - {}", e.toString());
      return ResponseEntity.badRequest().body(new BaseResponse(e.toString()));
    } catch (Exception e) {
      final String message =
          MessageFormat.format(
              "Error while processing request to delete bank account with id: {0}", id);
      log.error(message, e);
      return ResponseEntity.internalServerError().body(new BaseResponse(message));
    }
  }
}
