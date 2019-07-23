package com.edu.xueyuan.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.xueyuan.eduservice.entity.EduSubject;
import com.edu.xueyuan.eduservice.entity.subjectDto.OneSubjectDto;
import com.edu.xueyuan.eduservice.entity.subjectDto.TwoSubjectDto;
import com.edu.xueyuan.eduservice.mapper.EduSubjectMapper;
import com.edu.xueyuan.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.xueyuan.exception.EduException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-08
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public List<String> saveSubject(MultipartFile file) {
        //用来接收空值判断信息
        List<String> list = new ArrayList<>();

        try {
            InputStream in = file.getInputStream();

            //创建工作簿

            Workbook book = WorkbookFactory.create(in);

            //创建表
            Sheet sheetAt = book.getSheetAt(0);
            //创建行
            //获取最后一行的索引值
            int lastRowNum = sheetAt.getLastRowNum();
            //直接从第二行开始取值
            for (int rowNum = 1; rowNum <=lastRowNum ; rowNum++) {

                Row row = sheetAt.getRow(rowNum);

                //添加一级分类
                Cell oneCell = row.getCell(0);
                //判断列是否为空，如果是空则提示用户，并且跳过本次操作
                if(oneCell == null){
                    String msg = "第" + (rowNum+1) + "行,第一列数据为空";

                    list.add(msg);

                    continue;
                }

                //获取第一列值的数据类型
                int cellType = oneCell.getCellType();

                String oneCellValue =  null;

                switch (cellType) {

                    case HSSFCell.CELL_TYPE_STRING :
                        oneCellValue = oneCell.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        oneCellValue = String.valueOf(oneCell.getNumericCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        oneCellValue = String.valueOf(oneCell.getBooleanCellValue());

                }



                //判断列的值是否为空，如果是空则提示用户，并且跳过本次操作
                if(StringUtils.isEmpty(oneCellValue)) {
                    String msg = "第" + (rowNum+1) + "行,第一列数据为空";

                    list.add(msg);

                    continue;
                }


                //根据第一列的值查询表
                EduSubject oneSubject = this.getByTitle(oneCellValue);

                String parentId = null;
                //如果不存在，则添加，如果存在，则不添加
                if (oneSubject == null) {

                    EduSubject subjectLevelOne = new EduSubject();

                    subjectLevelOne.setTitle(oneCellValue);

                    subjectLevelOne.setParentId("0");
                    
                    baseMapper.insert(subjectLevelOne);
                    //给二级分类提供一级分类的值
                    parentId = subjectLevelOne.getId();
                }else{
                    parentId = oneSubject.getId();
                }


                //获取二级分类的列
                Cell twoCell = row.getCell(1);

                if(twoCell == null){
                    String msg = "第" + (rowNum + 1) + "行，第2列为空";

                    list.add(msg);

                    continue;
                }
                String twoCellValue = null;

                int cellTypeTwo = twoCell.getCellType();

                switch (cellTypeTwo) {

                    case HSSFCell.CELL_TYPE_STRING:
                        twoCellValue = twoCell.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        twoCellValue = String.valueOf(twoCell.getNumericCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        twoCellValue = String.valueOf(twoCell.getBooleanCellValue());
                }
                twoCellValue = twoCell.getStringCellValue();
                //根据二级分类的标题和一级分类的id进行查询
                EduSubject twoSubject = this.getSubByTitle(twoCellValue, parentId);
                //如果不存在，则添加，如果存在，则不添加
                if(twoSubject == null) {

                    EduSubject subjectLevelTwo = new EduSubject();

                    subjectLevelTwo.setTitle(twoCellValue);

                    subjectLevelTwo.setParentId(parentId);

                    baseMapper.insert(subjectLevelTwo);
                }


            }




        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //查询所有的课程科目信息
    @Override
    public List<OneSubjectDto> listAllSub() {
        //用来封装最终的数据集合
        List<OneSubjectDto> completeList = new ArrayList<>();

        //获取一级分类的数据集合
        QueryWrapper queryWrapperOne= new QueryWrapper();

        queryWrapperOne.eq("parent_id",0);

        List<EduSubject> oneSubjectList = baseMapper.selectList(queryWrapperOne);

        //获取二级分类的数据集合

        QueryWrapper queryWrapperTwo = new QueryWrapper();

        queryWrapperTwo.ne("parent_id",0);

        List<EduSubject> twoSubjectList = baseMapper.selectList(queryWrapperTwo);

        //封装数据
        for (int i = 0; i <oneSubjectList.size() ; i++) {
            //封装一级分类数据
            EduSubject oneLevelSubject = oneSubjectList.get(i);
            //一级分类数据对拷到dto中
            OneSubjectDto oneSubjectDto =  new OneSubjectDto();
            //对拷过程中，注意两实体类中的属性名称必须一致，不然会对拷失败
            BeanUtils.copyProperties(oneLevelSubject,oneSubjectDto);

            completeList.add(oneSubjectDto);


            //封装二级分类数据

            //创建一个集合用来封装二级分类的数据
            List<TwoSubjectDto> twoSubjectDtos = new ArrayList<>();

            for (int j = 0; j <twoSubjectList.size() ; j++) {



                EduSubject twoLevelSubject = twoSubjectList.get(j);
                //判断二级分类的pid是否与一级分类的id相等，如果相等，则说明该二级分类是一个分类的子目录
                if(oneLevelSubject.getId().equals(twoLevelSubject.getParentId())){

                    TwoSubjectDto twoSubjectDto = new TwoSubjectDto();
                    //二级分级数据对拷到dto中
                    BeanUtils.copyProperties(twoLevelSubject,twoSubjectDto);

                    twoSubjectDtos.add(twoSubjectDto);


                }
            }
            //讲封装好的二级分类数据放入一级分类中
            oneSubjectDto.setChildren(twoSubjectDtos);
        }



        return completeList;
    }


    @Override
    public boolean removeSubById(String id) {

        //根据id查询该分类是否含有子分类
        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.eq("parent_id",id);

        Integer count = baseMapper.selectCount(queryWrapper);
        //说明含有子分类
        if(count > 0) {
            throw new EduException(20001,"有子分类不能删除");
        }else{
            int result = baseMapper.deleteById(id);
            //此写法为经常写法
            return result>0;
        }

    }

    /**
     * 此方法用来增加一级分类
     * @param subject
     * @return
     */
    @Override
    public boolean saveLevelOne(EduSubject subject) {

        EduSubject title = this.getByTitle(subject.getTitle());

        if(title == null) {
            int insert = baseMapper.insert(subject);
            return insert>0;
        }else{
            return false;
        }
    }

    /**
     * 此方法用来增加二级分类
     * @param subject
     * @return
     */
    @Override
    public boolean saveLevelTwo(EduSubject subject) {

        EduSubject subByTitle = this.getSubByTitle(subject.getTitle(), subject.getParentId());

        if(subByTitle == null){

            int insert = baseMapper.insert(subject);

            return insert>0;
        }else {

            return false;
        }

    }

    /**
     * 此方法用来查询一级分类的数据
     * @param name 一级分类的title名
     * @return  subject 一级分类的数据
     */
    private EduSubject getByTitle(String name) {

        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("title",name);

        queryWrapper.eq("parent_id","0");

        EduSubject subject = baseMapper.selectOne(queryWrapper);

        return subject;
    }

    /**
     * 此方法用来查询二级分类的数据
     * @param name parentId name：二级分类的title parentId:pid二级分类的母目录
     * @return subject 二级分类的数据
     */
    private EduSubject getSubByTitle(String name,String parentId) {

        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("title",name);

        queryWrapper.eq("parent_id",parentId);

        EduSubject subject = baseMapper.selectOne(queryWrapper);

        return subject;
    }
}
