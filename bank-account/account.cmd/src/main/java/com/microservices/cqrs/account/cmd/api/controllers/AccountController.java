package com.microservices.cqrs.account.cmd.api.controllers;

import com.microservices.cqrs.account.cmd.api.commands.DepositFundsCommand;
import com.microservices.cqrs.account.cmd.api.commands.OpenAccountCommand;
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
          new OpenAccountResponse("Bank account created successfully", id), HttpStatus.CREATED);
    } catch (IllegalStateException e) {
      log.warn("Client made a bad request - {}", e.toString());
      return new ResponseEntity<>(
          new OpenAccountResponse(e.toString(), id), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      final String message =
          MessageFormat.format(
              "Error while processing request to open new bank account for id: {0}", id);
      log.error(message, e);
      return new ResponseEntity<>(
          new OpenAccountResponse(message, id), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/deposit/{id}")
  public ResponseEntity<BaseResponse> openAccount(
      @PathVariable(value = "id") String id, @RequestBody DepositFundsCommand command) {
    try {
      command.setId(id);
      commandDispatcher.send(command);
      return new ResponseEntity<>(new BaseResponse("Funds Deposited successfully"), HttpStatus.OK);
    } catch (IllegalStateException | AggregateNotFoundException e) {
      log.warn("Client made a bad request - {}", e.toString());
      return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      final String message =
          MessageFormat.format(
              "Error while processing request to deposit to bank account with id: {0}", id);
      log.error(message, e);
      return new ResponseEntity<>(new BaseResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
