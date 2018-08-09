package com.charlotte.spring.api.min.controller;

import com.charlotte.spring.api.min.entity.Sample;
import com.charlotte.spring.api.min.exception.UpdateFailedException;
import com.charlotte.spring.api.min.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@Transactional(rollbackFor = Exception.class)
public class SampleController {

    final SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @PostMapping
    public ResponseEntity<String> post(@RequestBody Sample sample) throws UpdateFailedException {
        sampleService.registerSample(sample);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Sample>> getList() {
        List<Sample> samples = sampleService.getSample();

        return new ResponseEntity<>(samples, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sample> get(@PathVariable int id) {
        Sample sample = sampleService.getSample(id);

        return new ResponseEntity<>(sample, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> put(@PathVariable int id, @RequestBody Sample sample) throws UpdateFailedException {
        sampleService.updateSample(id, sample);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{version}")
    public ResponseEntity<String> delete(@PathVariable int id, @PathVariable int version) throws UpdateFailedException {
        sampleService.deleteSample(id, version);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
