package com.dawnlight.chronicle_dawnlight.service;

import com.dawnlight.chronicle_dawnlight.pojo.po.FilePO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {

    // 上传文件
    void uploadFile(FilePO file, MultipartFile multipartFile);

    // 获取用户的所有文件
    List<FilePO> getFilesByUserId(String userId);

    // 获取某个文件夹下的文件
    List<FilePO> getFilesByFolderId(Integer folderId);

    //下载文件
    File downloadFile(Integer fileId);

    /**
     * 删除文件
     * @param fileId 文件ID
     */
    void deleteFile(Integer fileId);
}
