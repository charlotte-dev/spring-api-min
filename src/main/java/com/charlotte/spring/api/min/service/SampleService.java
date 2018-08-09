package com.charlotte.spring.api.min.service;

import com.charlotte.spring.api.min.entity.Sample;
import com.charlotte.spring.api.min.exception.DataNotFoundException;
import com.charlotte.spring.api.min.exception.ExclusiveException;
import com.charlotte.spring.api.min.exception.UpdateFailedException;
import com.charlotte.spring.api.min.mapper.SampleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleService {

    private final SampleMapper sampleMapper;

    public SampleService(SampleMapper sampleMapper){
        this.sampleMapper = sampleMapper;
    }

    public List<Sample> getSample(){
        return sampleMapper.selectAll();
    }

    public Sample getSample(int id){
        return sampleMapper.selectOne(id);
    }

    public void registerSample(Sample sample) throws UpdateFailedException {
        int insertCnt = sampleMapper.insert(sample);
        if(insertCnt != 1){
            throw new UpdateFailedException();
        }
    }

    public void updateSample(int id, Sample sample) throws UpdateFailedException {
        getLock(id, sample.getVersion());
        sample.setId(id);

        int updateCnt = sampleMapper.update(sample);

        if(updateCnt != 1){
            throw new UpdateFailedException();
        }
    }

    public void deleteSample(int id, int version) throws UpdateFailedException {
        getLock(id, version);

        int deleteCnt = sampleMapper.delete(id);
        if(deleteCnt != 1){
            throw new UpdateFailedException();
        }
    }

    Sample getLock(int id, int version) throws UpdateFailedException {
        Sample current = sampleMapper.lock(id);
        if(current == null){
            throw new DataNotFoundException();
        }

        if(version != current.getVersion()){
            throw new ExclusiveException();
        }
        return current;
    }
}
