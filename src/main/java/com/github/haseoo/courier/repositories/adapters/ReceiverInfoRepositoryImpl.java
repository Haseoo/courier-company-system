package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.ReceiverInfoModel;
import com.github.haseoo.courier.querydata.ReceiverInfoQueryData;
import com.github.haseoo.courier.repositories.jpa.ReceiverInfoJPARepository;
import com.github.haseoo.courier.repositories.ports.ReceiverInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReceiverInfoRepositoryImpl implements ReceiverInfoRepository {
    private final ReceiverInfoJPARepository receiverInfoJPARepository;

    @Override
    public List<ReceiverInfoModel> getList() {
        return receiverInfoJPARepository.findAll();
    }

    @Override
    public Optional<ReceiverInfoModel> getById(Long id) {
        return receiverInfoJPARepository.findById(id);
    }

    @Override
    public ReceiverInfoModel saveAndFlush(ReceiverInfoModel addressModel) {
        return receiverInfoJPARepository.saveAndFlush(addressModel);
    }

    @Override
    public Optional<ReceiverInfoModel> receiverInfoExists(ReceiverInfoQueryData receiverInfoQueryData) {
        return receiverInfoJPARepository.findByNameAndSurnameAndEmailAddressAndPhoneNumber(
                receiverInfoQueryData.getName(),
                receiverInfoQueryData.getSurname(),
                receiverInfoQueryData.getEmailAddress(),
                receiverInfoQueryData.getPhoneNumber()
        );
    }
}
