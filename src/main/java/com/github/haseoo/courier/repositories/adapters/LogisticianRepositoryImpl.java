package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.LogisticianModel;
import com.github.haseoo.courier.repositories.jpa.LogisticianJPARepository;
import com.github.haseoo.courier.repositories.ports.LogisticianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LogisticianRepositoryImpl implements LogisticianRepository {
    private final LogisticianJPARepository logisticianJPARepository;

    @Override
    public List<LogisticianModel> getList() {
        return logisticianJPARepository.findAllByActiveTrue();
    }

    @Override
    public Optional<LogisticianModel> getById(Long id) {
        return logisticianJPARepository.findById(id);
    }

    @Override
    public LogisticianModel saveAndFlush(LogisticianModel logisticianModel) {
        return logisticianJPARepository.saveAndFlush(logisticianModel);
    }
}
