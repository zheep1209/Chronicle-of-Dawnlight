package com.dawnlight.chronicle_dawnlight.controller;

import com.dawnlight.chronicle_dawnlight.common.Result;
import com.dawnlight.chronicle_dawnlight.common.utils.BaseContext;
import com.dawnlight.chronicle_dawnlight.pojo.po.FolderPO;
import com.dawnlight.chronicle_dawnlight.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders")
public class FolderController {

    @Autowired
    private FolderService folderService;

    /**
     * 创建文件夹
     * @param folder 文件夹信息
     * @return Result
     */
    @PostMapping("/create")
    public Result<String> createFolder(@RequestBody FolderPO folder) {
        folderService.createFolder(folder);
        return Result.success("文件夹创建成功");
    }

    /**
     * 获取某个用户的所有文件夹
     * @return Result
     */
    @GetMapping("/allFolder")
    public Result<List<FolderPO>> getUserFolders() {
        List<FolderPO> folders = folderService.getFoldersByUserId(BaseContext.getCurrentThreadId().toString());
        return Result.success(folders);
    }

    /**
     * 获取某个文件夹的所有子文件夹
     * @param parentId 父文件夹id
     * @return Result
     */
    @GetMapping("/{parentId}/subfolders")
    public Result<List<FolderPO>> getSubFolders(@PathVariable("parentId") Integer parentId) {
        List<FolderPO> subFolders = folderService.getSubFoldersByParentId(parentId);
        return Result.success(subFolders);
    }

    /**
     * 删除文件夹及其内容
     * @param folderId 文件夹ID
     * @return Result
     */
    @DeleteMapping("/{folderId}")
    public Result<String> deleteFolder(@PathVariable("folderId") Integer folderId) {
        folderService.deleteFolder(folderId);
        return Result.success("文件夹及其内容删除成功");
    }
}
