package com.microservices.cqrs.account.query.api.dto;

import com.microservices.cqrs.account.common.dto.BaseResponse;
import com.microservices.cqrs.core.domain.BaseEntity;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountLookupResponse extends BaseResponse {
  private List<BaseEntity> accounts;

  public AccountLookupResponse(String message) {
    super(message);
  }

  public AccountLookupResponse(List<BaseEntity> accounts, String message) {
    super(message);
    this.accounts = accounts;
  }
}
