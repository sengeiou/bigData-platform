package com.bigData.service.file.controller;

import com.bigData.common.base.BaseController;
import com.bigData.common.core.JsonPage;
import com.bigData.common.core.R;
import com.bigData.service.file.api.entity.FileEntity;
import com.bigData.service.file.api.query.FileQuery;
import com.bigData.service.file.api.vo.FileVo;
import com.bigData.service.file.mapstruct.FileMapper;
import com.bigData.service.file.service.FileService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuwei
 * @since 2019-09-17
 */
@Api(tags = {"文件管理"})
@RestController
@RequestMapping("/files")
public class FileController extends BaseController {

    @Autowired
    private FileService fileService;

    @Autowired
    private FileMapper fileMapper;

    /**
     * 通过ID查询信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    public R getFileById(@PathVariable String id) {
        return R.ok().setData(fileService.getById(id));
    }

    /**
     * 分页查询信息
     *
     * @param fileQuery
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileQuery", value = "查询实体fileQuery", required = true, dataTypeClass = FileQuery.class)
    })
    @GetMapping("/page")
    public R getFilePage(FileQuery fileQuery) {
        QueryWrapper<FileEntity> queryWrapper = new QueryWrapper<>();
        IPage<FileEntity> page = fileService.page(new Page<>(fileQuery.getPageNum(), fileQuery.getPageSize()), queryWrapper);
        List<FileVo> collect = page.getRecords().stream().map(fileMapper::toVO).collect(Collectors.toList());
        JsonPage<FileVo> jsonPage = new JsonPage<>(page.getCurrent(), page.getSize(), page.getTotal(), collect);
        return R.ok().setData(jsonPage);
    }

    /**
     * 附件上传
     * @param file
     * @return
     */
    @ApiOperation(value = "附件上传")
    @ApiImplicitParam(name = "file", value = "附件file", required = true, dataTypeClass = MultipartFile.class)
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) {
        fileService.uploadFile(file);
        return R.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{id}")
    public R deleteFileById(@PathVariable String id) {
        fileService.deleteFileById(id);
        return R.ok();
    }
}

