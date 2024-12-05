package com.dawnlight.chronicle_dawnlight.service;

import com.dawnlight.chronicle_dawnlight.pojo.po.FolderPO;

import java.util.List;

public interface FolderService {

    // 创建文件夹
    int createFolder(FolderPO folder);

    // 获取用户的文件夹
    List<FolderPO> getFoldersByUserId(String userId);

    // 获取某个父文件夹下的子文件夹
    List<FolderPO> getSubFoldersByParentId(Integer parentId);

    void deleteFolder(Integer folderId);
}
