package com.microservices.cqrs.account.query.api.queries;

import com.microservices.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FindAccountByHolderQuery extends BaseQuery {
  private String accountHolder;
}
