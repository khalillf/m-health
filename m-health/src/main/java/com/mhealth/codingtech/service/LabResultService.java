package com.mhealth.codingtech.service;

import com.mhealth.codingtech.model.LabResult;
import com.mhealth.codingtech.repository.LabResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabResultService {

    @Autowired
    private LabResultRepository labResultRepository;

    public List<LabResult> getAllLabResults() {
        return labResultRepository.findAll();
    }

    public Optional<LabResult> getLabResultById(Long id) {
        return labResultRepository.findById(id);
    }

    public LabResult createLabResult(LabResult labResult) {
        return labResultRepository.save(labResult);
    }

    public LabResult updateLabResult(Long id, LabResult labResult) {
        if (labResultRepository.existsById(id)) {
            labResult.setId(id);
            return labResultRepository.save(labResult);
        } else {
            throw new RuntimeException("LabResult not found with id: " + id);
        }
    }

    public void deleteLabResult(Long id) {
        labResultRepository.deleteById(id);
    }
}
