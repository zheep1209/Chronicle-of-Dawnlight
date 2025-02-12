package com.dawnlight.chronicle_dawnlight.controller;

import com.dawnlight.chronicle_dawnlight.common.Result;
import com.dawnlight.chronicle_dawnlight.service.ToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tools")
public class ToolsController {
    @Autowired
    private ToolsService toolsService;

    /**
     * 获取随机东方Project    图片
     * @return
     */
    @GetMapping("/getTouhouUrl")
    public Result createArticle() {
        return Result.success(toolsService.getTouhouUrl());
    }
    /**
     * 将数据返回为excel格式
     */
}
