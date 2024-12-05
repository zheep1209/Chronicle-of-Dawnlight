package com.dawnlight.chronicle_dawnlight.mapper;

import com.dawnlight.chronicle_dawnlight.pojo.po.FilePO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface FileMapper {

    // 上传文件
    void uploadFile(FilePO file);

    // 获取某个用户的所有文件
    List<FilePO> getFilesByUserId(String userId);

    // 获取某个文件夹下的所有文件
    List<FilePO> getFilesByFolderId(Integer folderId);

    // 新增方法：通过 fileId 获取文件信息
    @Select("SELECT * FROM files WHERE id = #{fileId}")
    FilePO getFileById(@Param("fileId") Integer fileId);

    /**
     * 根据文件ID删除文件
     * @param fileId 文件ID
     */
    @Delete("DELETE FROM files WHERE id = #{fileId}")
    void deleteFileById(Integer fileId);

}
