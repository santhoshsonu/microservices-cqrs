package com.microservices.cqrs.account.query.api.controllers;

import com.microservices.cqrs.account.query.api.dto.AccountLookupResponse;
import com.microservices.cqrs.account.query.api.dto.EqualityType;
import com.microservices.cqrs.account.query.api.queries.FindAccountByHolderQuery;
import com.microservices.cqrs.account.query.api.queries.FindAccountByIdQuery;
import com.microservices.cqrs.account.query.api.queries.FindAccountWithBalanceQuery;
import com.microservices.cqrs.account.query.api.queries.FindAllAccountsQuery;
import com.microservices.cqrs.core.domain.BaseEntity;
import com.microservices.cqrs.core.exceptions.AccountNotFoundException;
import com.microservices.cqrs.core.infrastructure.QueryDispatcher;
import java.text.MessageFormat;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bankAccount/lookup")
@Slf4j
public class AccountQueryController {

  private final QueryDispatcher dispatcher;

  @Autowired
  public AccountQueryController(QueryDispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }

  @GetMapping("/")
  public ResponseEntity<AccountLookupResponse> getAllAccounts() {
    try {
      List<BaseEntity> accounts = dispatcher.send(new FindAllAccountsQuery());
      final int count = accounts.size();
      if (count == 0) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(
          new AccountLookupResponse(
              accounts, MessageFormat.format("Successfully fetched {0} bank accounts", count)));
    } catch (Exception e) {
      final String message = "Failed to complete get all bank accounts request.";
      log.error(message, e);
      return ResponseEntity.internalServerError().body(new AccountLookupResponse(message));
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable("id") String id) {
    try {
      List<BaseEntity> accounts = dispatcher.send(new FindAccountByIdQuery(id));
      return ResponseEntity.ok(
          new AccountLookupResponse(
              accounts, MessageFormat.format("Successfully fetched bank account with ID: {0}", id)));
    } catch (AccountNotFoundException e) {
      log.warn("Client made a bad request - {}", e.toString());
      return ResponseEntity.badRequest().body(new AccountLookupResponse(e.getMessage()));
    } catch (Exception e) {
      final String message =
          MessageFormat.format("Failed to complete get bank account with ID: {0} request.", id);
      log.error(message, e);
      return ResponseEntity.internalServerError().body(new AccountLookupResponse(message));
    }
  }

  @GetMapping("/holder/{name}")
  public ResponseEntity<AccountLookupResponse> getAccountsByHolder(
      @PathVariable("name") String holder) {
    try {
      List<BaseEntity> accounts = dispatcher.send(new FindAccountByHolderQuery(holder));
      final int count = accounts.size();
      if (count == 0) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(
          new AccountLookupResponse(
              accounts,
              MessageFormat.format("Successfully fetched bank account with holder: {0}", holder)));
    } catch (Exception e) {
      final String message =
          MessageFormat.format(
              "Failed to complete get bank account with holder: {0} request.", holder);
      log.error(message, e);
      return ResponseEntity.internalServerError().body(new AccountLookupResponse(message));
    }
  }

  @GetMapping("/balance/{equalityType}/{balance}")
  public ResponseEntity<AccountLookupResponse> getAccountsByHolder(
      @PathVariable("equalityType") EqualityType equalityType,
      @PathVariable("balance") double balance) {
    try {
      List<BaseEntity> accounts =
          dispatcher.send(new FindAccountWithBalanceQuery(equalityType, balance));
      final int count = accounts.size();
      if (count == 0) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(
          new AccountLookupResponse(
              accounts,
              MessageFormat.format(
                  "Successfully fetched bank accounts with funds {0} : {1}", equalityType, balance)));
    } catch (Exception e) {
      final String message =
          MessageFormat.format(
              "Failed to complete get bank accounts with funds {0} : {1}", equalityType, balance);
      log.error(message, e);
      return ResponseEntity.internalServerError().body(new AccountLookupResponse(message));
    }
  }
}
