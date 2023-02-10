package com.example.collectiontableadministration.utils;

import com.example.collectiontableadministration.domain.response.vo.CollectionTableVo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 32298
 */
public class TreeUtils {
    public static List<CollectionTableVo> buildDeptTreeByStream(List<CollectionTableVo> trees, Long parentId){
        //获取parentId = 0的根节点
        List<CollectionTableVo> list = trees.stream().filter(item -> item.getParentId().equals(parentId)).collect(Collectors.toList());
        //根据parentId进行分组
        Map<Long, List<CollectionTableVo>> map = trees.stream().collect(Collectors.groupingBy(CollectionTableVo::getParentId));
        recursionFnTree(list, map);
        return list;
    }

    /**
     * 递归遍历节点
     * @param list
     * @param map
     */
    public static void recursionFnTree(List<CollectionTableVo> list, Map<Long, List<CollectionTableVo>> map){
        for (CollectionTableVo treeSelect : list) {
            List<CollectionTableVo> childList = map.get(treeSelect.getId());
            treeSelect.setChildren(childList);
            if (null != childList && 0 < childList.size()){
                recursionFnTree(childList,map);
            }
        }
    }
}
