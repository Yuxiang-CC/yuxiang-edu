package com.yuxiang.edu.service.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxiang.edu.service.core.entity.Subject;
import com.yuxiang.edu.service.core.entity.excel.DownLoadSubjectData;
import com.yuxiang.edu.service.core.entity.excel.ExcelSubjectData;
import com.yuxiang.edu.service.core.entity.vo.SubjectVO;
import com.yuxiang.edu.service.core.listener.ExcelSubjectListener;
import com.yuxiang.edu.service.core.mapper.SubjectMapper;
import com.yuxiang.edu.service.core.service.SubjectService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {


    @Override
    public void batchImport(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelSubjectData.class, new ExcelSubjectListener(baseMapper))
                .excelType(ExcelTypeEnum.XLS)
                .sheet().doRead();
    }

    @Cacheable(value = "course:subject", key = "#root.methodName")
    @Override
    public List<SubjectVO> nestedList() {

        return baseMapper.selectNestedSByParentId("0");
    }

    @Override
    public List<DownLoadSubjectData> getExportData() {
        List<Subject> subjects = baseMapper.selectExportData();
        List<DownLoadSubjectData> dataList = new ArrayList<>(16);

        subjects.forEach(subject -> {
            if ("0".equals(subject.getParentId())) {
                AtomicBoolean blog = new AtomicBoolean(false);
                subjects.forEach(s -> {
                    if (s.getParentId().equals(subject.getId())) {
                        DownLoadSubjectData data = new DownLoadSubjectData();
                        data.setLevelOneTitle(subject.getTitle());
                        data.setLevelTwoTitle(s.getTitle());
                        dataList.add(data);
                        blog.set(true);
                    }
                });

                if (!blog.get()) {
                    DownLoadSubjectData data = new DownLoadSubjectData();
                    data.setLevelOneTitle(subject.getTitle());
                    dataList.add(data);
                }
            }
        });


        return dataList;
    }
}
