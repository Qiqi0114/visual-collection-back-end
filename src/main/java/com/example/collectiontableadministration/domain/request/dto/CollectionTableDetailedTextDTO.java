package com.example.collectiontableadministration.domain.request.dto;

import com.example.collectiontableadministration.domain.pojo.CollectionTableDetailedText;
import lombok.Data;

/**
 * @author 32298
 */
@Data
public class CollectionTableDetailedTextDTO {
    private Long id;

    private Long collectionTableDetailedId;

    private CollectionTableDetailedText collectionTableDetailedText;
}
