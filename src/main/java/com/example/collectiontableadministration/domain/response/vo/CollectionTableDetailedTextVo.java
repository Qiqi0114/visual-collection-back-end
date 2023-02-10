package com.example.collectiontableadministration.domain.response.vo;

import com.example.collectiontableadministration.domain.pojo.CollectionTableDetailedExcel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author
 */
@Data
public class CollectionTableDetailedTextVo {
    private Long id;
    private Long collectionTableId;
    private String collectionTableName;
    private Long collectionTableParentId;
    private String collectionTableParentName;
    @JsonIgnore
    private String collectionTableDetailedTextJson;
    private CollectionTableDetailedExcel collectionTableDetailedExcel;
    private boolean applyFor;
}
