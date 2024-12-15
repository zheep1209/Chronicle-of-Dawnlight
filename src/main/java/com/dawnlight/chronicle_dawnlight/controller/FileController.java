package com.dawnlight.chronicle_dawnlight.controller;

import com.dawnlight.chronicle_dawnlight.common.Result;
import com.dawnlight.chronicle_dawnlight.common.utils.BaseContext;
import com.dawnlight.chronicle_dawnlight.pojo.po.FilePO;
import com.dawnlight.chronicle_dawnlight.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     * @param file MultipartFile 文件
     * @param folderId 文件夹ID（可选）
     * @param description 文件描述（可选）
     * @return Result
     */
    @PostMapping("/upload")
    public Result<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folderId", required = false) Integer folderId,
            @RequestParam(value = "description", required = false) String description) {
        try {
            // 构造 FilePO 对象
            FilePO filePO = new FilePO();
            filePO.setUserId(BaseContext.getCurrentThreadId().toString());
            filePO.setFileName(file.getOriginalFilename());
            filePO.setFileSize(file.getSize());
            filePO.setFolderId(folderId);
            filePO.setUploadTime(LocalDateTime.now());
            filePO.setDescription(description);

            // 调用 Service 保存文件
            fileService.uploadFile(filePO, file);
            return Result.success("文件上传成功");
        } catch (Exception e) {
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 获取某个用户的所有文件
     * @return Result
     */
    @GetMapping("/user")
    public Result<List<FilePO>> getUserFiles() {
        List<FilePO> files = fileService.getFilesByUserId(BaseContext.getCurrentThreadId().toString());
        return Result.success(files);
    }

    /**
     * 获取某个文件夹下的所有文件
     * @param folderId 文件夹ID
     * @return Result
     */
    @GetMapping("/{folderId}/files")
    public Result<List<FilePO>> getFilesByFolderId(@PathVariable("folderId") Integer folderId) {
        List<FilePO> files = fileService.getFilesByFolderId(folderId);
        return Result.success(files);
    }

    /**
     * 下载文件
     * @param fileId 文件ID
     * @return ResponseEntity<FileSystemResource>
     */
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Integer fileId) {
        File file = fileService.downloadFile(fileId);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        Path path = Paths.get(file.getAbsolutePath());
        Resource resource = new FileSystemResource(path);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)  // 确保返回的是文件的二进制数据
                .body(resource);
    }
    /**
     * 删除文件
     * @param fileId 文件ID
     * @return Result
     */
    @DeleteMapping("/{fileId}")
    public Result<String> deleteFile(@PathVariable("fileId") Integer fileId) {
        fileService.deleteFile(fileId);
        return Result.success("文件删除成功");
    }
}
