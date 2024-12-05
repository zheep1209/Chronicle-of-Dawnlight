package com.dawnlight.chronicle_dawnlight.service.impl;

import com.dawnlight.chronicle_dawnlight.common.utils.BaseContext;
import com.dawnlight.chronicle_dawnlight.mapper.FileMapper;
import com.dawnlight.chronicle_dawnlight.mapper.FolderMapper;
import com.dawnlight.chronicle_dawnlight.pojo.po.FilePO;
import com.dawnlight.chronicle_dawnlight.pojo.po.FolderPO;
import com.dawnlight.chronicle_dawnlight.service.FolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
@Slf4j
@Service
public class FolderServiceImpl implements FolderService {

    // 基础路径
    private static final String BASE_PATH = "C:\\Users\\zheep\\Documents\\Chronicle_Dawnlight\\uploads\\";
    @Autowired
    private FolderMapper folderMapper;
    @Autowired
    private FileMapper fileMapper;

    @Override
    public int createFolder(FolderPO folder) {
        folder.setUserId(BaseContext.getCurrentThreadId().toString());
        if (folder.getParentId() == null) {
            folder.setName(folder.getUserId());
        }

        // 构建文件夹路径
        String userFolderPath = BASE_PATH + folder.getUserId();
        String parentFolderPath = folder.getParentId() == null ? "" : File.separator + getParentFolderPath(folder.getParentId());
        String fullPath = folder.getParentId() == null ? userFolderPath : userFolderPath + parentFolderPath + File.separator + folder.getName();

        // 创建物理文件夹
        File file = new File(fullPath);
        if (!file.exists()) {
            boolean isCreated = file.mkdirs();
            if (!isCreated) {
                throw new RuntimeException("无法创建物理文件夹: " + fullPath);
            }
        }

        // 创建数据库记录
        folderMapper.createFolder(folder);

        // 获取新插入的文件夹ID
        int folderId = folder.getId();
        log.info("创建文件夹成功，ID: {}", folderId);

        return folderId;
    }


    @Override
    public List<FolderPO> getFoldersByUserId(String userId) {
        return folderMapper.getFoldersByUserId(userId);
    }

    @Override
    public List<FolderPO> getSubFoldersByParentId(Integer parentId) {
        return folderMapper.getSubFoldersByParentId(parentId);
    }

    /**
     * 递归获取父级文件夹路径
     */
    private String getParentFolderPath(Integer parentId) {
        FolderPO parentFolder = folderMapper.getFolderById(parentId);
        if (parentFolder == null) {
            throw new RuntimeException("父级文件夹不存在，ID: " + parentId);
        }
        return parentFolder.getParentId() == null ? "" : getParentFolderPath(parentFolder.getParentId()) + File.separator + parentFolder.getName();
    }

    @Override
    public void deleteFolder(Integer folderId) {
        // 1. 获取文件夹信息并删除该文件夹下的所有文件
        deleteFilesInFolder(folderId);

        // 2. 递归删除所有子文件夹
        deleteSubFolders(folderId);

        // 3. 删除文件夹本身
        FolderPO folder = folderMapper.getFolderById(folderId);
        if (folder == null) {
            throw new RuntimeException("文件夹不存在，ID: " + folderId);
        }

        String folderPath = BASE_PATH + folder.getUserId() + File.separator + getFolderPath(folderId);
        File folderFile = new File(folderPath);
        if (folderFile.exists()) {
            deleteDirectory(folderFile); // 删除目录及其内容
        }

        // 4. 删除数据库中的文件夹记录
        folderMapper.deleteFolderById(folderId);
    }

    /**
     * 递归删除所有子文件夹
     */
    private void deleteSubFolders(Integer folderId) {
        List<FolderPO> subFolders = folderMapper.getSubFoldersByParentId(folderId);
        for (FolderPO subFolder : subFolders) {
            deleteFilesInFolder(subFolder.getId()); // 删除子文件夹下的所有文件
            deleteSubFolders(subFolder.getId()); // 递归删除子文件夹
            String subFolderPath = BASE_PATH + subFolder.getUserId() + File.separator + getFolderPath(subFolder.getId());
            File subFolderFile = new File(subFolderPath);
            if (subFolderFile.exists()) {
                deleteDirectory(subFolderFile); // 删除子文件夹目录及其内容
            }
            folderMapper.deleteFolderById(subFolder.getId()); // 删除子文件夹记录
        }
    }

    /**
     * 删除文件夹下所有文件
     */
    private void deleteFilesInFolder(Integer folderId) {
        List<FilePO> files = fileMapper.getFilesByFolderId(folderId);
        for (FilePO file : files) {
            File targetFile = new File(file.getFilePath());
            if (targetFile.exists()) {
                boolean isDeleted = targetFile.delete();
                if (!isDeleted) {
                    throw new RuntimeException("无法删除文件: " + file.getFilePath());
                }
            }
            fileMapper.deleteFileById(file.getId()); // 删除文件记录
        }
    }

    /**
     * 递归删除目录及其内容
     */
    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file); // 递归删除子目录
                } else {
                    file.delete(); // 删除文件
                }
            }
        }
        directory.delete(); // 删除空目录
    }

    /**
     * 获取文件夹的完整路径
     */
    private String getFolderPath(Integer folderId) {
        FolderPO folder = folderMapper.getFolderById(folderId);
        if (folder == null) {
            throw new RuntimeException("文件夹不存在，ID: " + folderId);
        }
        return folder.getParentId() == null ? folder.getName() : getFolderPath(folder.getParentId()) + File.separator + folder.getName();
    }
}
