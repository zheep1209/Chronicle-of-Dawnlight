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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

// 使用相对路径（相对当前工作目录）

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private FolderMapper folderMapper;
    @Autowired
    private FolderService folderService;

    private static final Path BASE_PATH = Paths.get("/var/uploads/");

    @Override
    public void uploadFile(FilePO file, MultipartFile multipartFile) {
        // 检查并创建用户目录
        Path userFolderPath = BASE_PATH.resolve(String.valueOf(file.getUserId()));
        Path fullPath = file.getFolderId() == null ? userFolderPath : userFolderPath.resolve(getFolderPath(file.getFolderId()));
        try {
            if (!Files.exists(fullPath)) {
                Files.createDirectories(fullPath);
                Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-x---");
                Files.setPosixFilePermissions(fullPath, perms);
            }
        } catch (IOException e) {
            log.error("文件夹创建失败: {}", fullPath, e);
            throw new RuntimeException("无法创建文件夹: " + fullPath, e);
        }

        // 保存文件
        Path targetFile = fullPath.resolve(file.getFileName().toLowerCase());
        try {
            multipartFile.transferTo(targetFile.toFile());
        } catch (IOException e) {
            log.error("文件保存失败: {}", targetFile, e);
            throw new RuntimeException("无法保存文件到路径: " + targetFile, e);
        }

        // 保存文件元信息
        file.setFilePath(targetFile.toAbsolutePath().toString());
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
