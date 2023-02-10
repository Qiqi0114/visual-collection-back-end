package com.example.collectiontableadministration.domain.dto;

import com.example.collectiontableadministration.domain.request.PageBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 32298
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WorkLoadPage extends PageBean {
   private String departmentId;
   private String yearId;
   private String userName;
}
