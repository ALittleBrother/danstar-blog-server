package com.danstar.blog.server.convert;

import com.danstar.blog.server.entity.Account;
import com.danstar.blog.server.vo.account.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {

    AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    Account toEntity(AccountAddReq req);

    AccountDetailResp toDetailResp(Account account);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    void toEntity(AccountUpdateReq req, @MappingTarget Account account);

    AccountListResp toListResp(Account account);

    AccountLoginResp toLoginResp(Account account);
}
