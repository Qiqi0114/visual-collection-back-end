package com.example.collectiontableadministration.domain.request.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 32298
 */
@Data
public class UserGroupDto {
    private Long id;
    private List<Long> userId;
}
