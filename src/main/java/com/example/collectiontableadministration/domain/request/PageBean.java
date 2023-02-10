package com.example.collectiontableadministration.domain.request;

import lombok.Data;

/**
 * @author lhy
 */
@Data
public class PageBean {
    /**
     * 页数
     */
    private int pageNum;
    /**
     * 每页显示条数
     */
    private int pageSize;
}
