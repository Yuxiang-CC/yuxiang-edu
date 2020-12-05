package com.yuxiang.edu.service.core.controller.admin;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.core.entity.Chapter;
import com.yuxiang.edu.service.core.service.ChapterService;
import com.yuxiang.edu.service.core.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 12:52
 */

@Api(description = "课程章节控制器")
@RestController
@RequestMapping("/admin/core/chapter")
@Slf4j
public class AdminChapterController {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoService videoService;

    @ApiOperation("新增章节")
    @PostMapping("/save")
    public R save(@ApiParam(value = "章节对象", required = true)
                  @RequestBody Chapter chapter) {

        boolean result = chapterService.save(chapter);

        if (result) {
            return R.ok().message("添加章节成功");
        } else {
            return R.error().message("添加章节失败");
        }
    }

    @ApiOperation("根据Id查询章节")
    @GetMapping("/get/{id}")
    public R getById(
            @ApiParam(value = "章节Id", required = true)
            @PathVariable("id") String id) {

        Chapter chapter = chapterService.getById(id);

        if (chapter != null) {
            return R.ok().data("item", chapter);
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据Id更新章节")
    @PutMapping("/update")
    public R updateById(@ApiParam(value = "章节对象", required = true)
                        @RequestBody Chapter chapter) {

        boolean result = chapterService.updateById(chapter);

        if (result) {
            return R.ok().message("更新章节成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据Id删除章节")
    @DeleteMapping("/remove/{id}")
    public R removeById(@ApiParam(value = "章节Id")
                        @PathVariable("id") String id) {

        // 删除课程视频 远程调用
        videoService.removeVodVideoByChapterId(id);

        // 删除章节
        boolean result = chapterService.removeChapterById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("删除失败");
        }
    }

}