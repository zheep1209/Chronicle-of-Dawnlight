package com.dawnlight.chronicle_dawnlight.service.impl;

import com.dawnlight.chronicle_dawnlight.common.utils.BaseContext;
import com.dawnlight.chronicle_dawnlight.mapper.FileMapper;
import com.dawnlight.chronicle_dawnlight.mapper.FolderMapper;
import com.dawnlight.chronicle_dawnlight.pojo.po.FilePO;
import com.dawnlight.chronicle_dawnlight.pojo.po.FolderPO;
import com.dawnlight.chronicle_dawnlight.service.FileService;
import com.dawnlight.chronicle_dawnlight.service.FolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    // 基础路径
    private static final String BASE_PATH = "C:\\Users\\zheep\\Documents\\Chronicle_Dawnlight\\uploads\\";
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private FolderMapper folderMapper;
    @Autowired
    private FolderService folderService;

    @Override
    public void uploadFile(FilePO file, MultipartFile multipartFile) {
        // 确定存储路径
        String userFolderPath = BASE_PATH + file.getUserId();
        String fullPath = file.getFolderId() == null ? userFolderPath : BASE_PATH + getFolderPath(file.getFolderId());

        // 检查目录是否存在，不存在时调用 createFolder 创建
        File directory = new File(fullPath);
        log.info("路径: {}", fullPath);
        if (!directory.exists()) {
            log.info("路径不存在");
            FolderPO folder = new FolderPO();
            folder.setUserId(file.getUserId());
            folder.setName(BaseContext.getCurrentThreadId().toString());
            folder.setParentId(null);
            int folderId = folderService.createFolder(folder); // 调用 createFolder 方法创建数据库记录和物理目录
            log.info("创建文件夹成功，ID: {}", folderId);
            file.setFolderId(folderId);
        }
        // 如果文件夹ID为空，则保存到当前用户的根文件夹
        if (file.getFolderId() == null) {
            file.setFolderId(folderMapper.getFoldersByUserId(file.getUserId()).get(0).getId());
        }

        // 保存文件到物理路径
        File targetFile = new File(fullPath + File.separator + file.getFileName());
        try {
            multipartFile.transferTo(targetFile);
        } catch (IOException e) {
            throw new RuntimeException("文件保存失败", e);
        }
        // 设置文件路径并保存元信息
        file.setFilePath(targetFile.getAbsolutePath());
        fileMapper.uploadFile(file);
    }

    @Override
    public List<FilePO> getFilesByUserId(String userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    @Override
    public List<FilePO> getFilesByFolderId(Integer folderId) {
        return fileMapper.getFilesByFolderId(folderId);
    }

    @Override
    public File downloadFile(Integer fileId) {
        FilePO file = fileMapper.getFileById(fileId);
        if (file == null) {
            throw new RuntimeException("文件不存在，ID: " + fileId);
        }

        File targetFile = new File(file.getFilePath());
        if (!targetFile.exists()) {
            throw new RuntimeException("文件物理路径不存在: " + file.getFilePath());
        }
        return targetFile;
    }

    private String getFolderPath(Integer folderId) {
        FolderPO folder = folderMapper.getFolderById(folderId);
        if (folder == null) {
            throw new RuntimeException("文件夹不存在，ID: " + folderId);
        }
        return folder.getParentId() == null ? folder.getName() : getFolderPath(folder.getParentId()) + File.separator + folder.getName();
    }

    @Override
    public void deleteFile(Integer fileId) {
        // 查询文件信息
        FilePO file = fileMapper.getFileById(fileId);
        if (file == null) {
            throw new RuntimeException("文件不存在，ID: " + fileId);
        }

        // 删除物理文件
        File physicalFile = new File(file.getFilePath());
        if (physicalFile.exists()) {
            if (!physicalFile.delete()) {
                throw new RuntimeException("删除物理文件失败: " + file.getFilePath());
            }
        } else {
            throw new RuntimeException("物理文件不存在，无法删除: " + file.getFilePath());
        }

        // 删除数据库记录
        fileMapper.deleteFileById(fileId);
    }
}
