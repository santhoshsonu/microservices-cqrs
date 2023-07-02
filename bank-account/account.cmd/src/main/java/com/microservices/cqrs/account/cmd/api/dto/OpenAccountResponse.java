package com.microservices.cqrs.account.cmd.api.dto;

import com.microservices.cqrs.account.common.dto.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpenAccountResponse extends BaseResponse {
  private String id;

  public OpenAccountResponse(String message, String id) {
    super(message);
    this.id = id;
  }
}
