package com.example.collectiontableadministration.domain.dto;

import com.example.collectiontableadministration.domain.request.PageBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 32298
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPage extends PageBean {
    private String userName;
}
