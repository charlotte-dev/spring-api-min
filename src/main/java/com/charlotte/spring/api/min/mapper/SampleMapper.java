package com.charlotte.spring.api.min.mapper;

import com.charlotte.spring.api.min.entity.Sample;

import java.util.List;

public interface SampleMapper {

    List<Sample> selectAll();

    Sample selectOne(int id);

    Sample lock(int id);

    int insert(Sample sample);

    int update(Sample sample);

    int delete(int id);
}
