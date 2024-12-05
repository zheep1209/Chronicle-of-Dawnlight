package com.dawnlight.chronicle_dawnlight.mapper;

import com.dawnlight.chronicle_dawnlight.pojo.po.FolderPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FolderMapper {
    @Insert("INSERT INTO folders (user_id, parent_id, name, created_at) VALUES (#{folder.userId}, #{folder.parentId}, #{folder.name}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "folder.id")
    void createFolder(@Param("folder") FolderPO folder);

    // 获取某个用户的所有文件夹
    List<FolderPO> getFoldersByUserId(String userId);

    // 根据父文件夹ID获取子文件夹
    List<FolderPO> getSubFoldersByParentId(Integer parentId);

    // 根据 ID 获取文件夹信息
    FolderPO getFolderById(Integer id);

    /**
     * 根据文件夹ID删除文件夹
     *
     * @param folderId 文件夹ID
     */
    @Delete("DELETE FROM folders WHERE id = #{folderId}")
    void deleteFolderById(Integer folderId);

    /**
     * 根据文件夹ID删除所有文件
     *
     * @param folderId 文件夹ID
     */
    @Delete("DELETE FROM files WHERE folder_id = #{folderId}")
    void deleteFilesByFolderId(Integer folderId);

}
