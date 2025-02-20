package com.bigData.service.file.service.impl;

import com.bigData.common.base.BaseServiceImpl;
import com.bigData.service.file.api.entity.FileEntity;
import com.bigData.service.file.dao.FileDao;
import com.bigData.service.file.service.FileService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2019-09-17
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public abstract class FileServiceImpl extends BaseServiceImpl<FileDao, FileEntity> implements FileService {

    @Autowired
    private FileDao fileDao;

    @Override
    public FileEntity uploadFile(MultipartFile file) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setContentType(file.getContentType())
                .setOriginalFilename(file.getOriginalFilename())
                .setFileSize(file.getSize());
        String nowDate = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        String extName = FileUtil.extName(fileEntity.getOriginalFilename());
        String fileName = nowDate + "." + extName;
        fileEntity.setFileName(fileName);
        uploadFile(file, fileEntity);
        // 设置文件来源
        fileEntity.setFileType(fileType());
        // 将文件信息保存到数据库
        fileDao.insert(fileEntity);
        return fileEntity;
    }

    @Override
    public void deleteFileById(String id) {
        FileEntity fileEntity = fileDao.selectById(id);
        if (fileEntity != null) {
            fileDao.deleteById(fileEntity.getId());
            deleteFile(fileEntity);
        }
    }

    /**
     * 文件来源
     *
     * @return
     */
    protected abstract String fileType();

    /**
     * 上传文件
     *
     * @param file
     * @param fileEntity
     */
    protected abstract void uploadFile(MultipartFile file, FileEntity fileEntity);

    /**
     * 删除文件资源
     *
     * @param fileEntity
     * @return
     */
    protected abstract void deleteFile(FileEntity fileEntity);
}
