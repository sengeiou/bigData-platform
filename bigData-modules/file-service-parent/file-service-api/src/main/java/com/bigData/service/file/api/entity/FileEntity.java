package com.bigData.service.file.api.entity;

import com.bigData.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author yuwei
 * @since 2019-09-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("tbl_file")
public class FileEntity extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 文件原始名称
     */
    private String originalFilename;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 访问路径
     */
    private String filePath;

    /**
     * 文件类型
     */
    private String contentType;

    /**
     * 文件来源
     */
    private String fileType;
}
