package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients.ClientNotFound;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients.ClientWithPeselExists;
import com.github.haseoo.courier.models.ClientIndividualModel;
import com.github.haseoo.courier.repositories.jpa.ClientIndividualJPARepository;
import com.github.haseoo.courier.repositories.ports.ClientIndividualRepository;
import com.github.haseoo.courier.security.JwtTokenProvider;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualAddData;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualData;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualEditData;
import com.github.haseoo.courier.services.ports.ClientIndividualService;
import com.github.haseoo.courier.services.ports.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.haseoo.courier.enums.ClientType.INDIVIDUAL;
import static com.github.haseoo.courier.utilities.UserUtils.validatePesel;
import static com.github.haseoo.courier.utilities.Utils.copyNonNullProperties;
import static com.github.haseoo.courier.utilities.Utils.validateEmailAddress;

@Service
@RequiredArgsConstructor
public class ClientIndividualServiceImpl implements ClientIndividualService {
    private final ClientIndividualRepository clientIndividualRepository;
    private final ClientIndividualJPARepository clientIndividualJPARepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ClientIndividualData getById(Long id) {
        return ClientIndividualData.of(clientIndividualRepository.getById(id).orElseThrow(() -> new ClientNotFound(id, INDIVIDUAL)));
    }

    @Transactional
    @Override
    public ClientIndividualData add(ClientIndividualAddData addData) {
        validateAddData(addData);
        ClientIndividualModel modelToAdd = modelMapper.map(addData, ClientIndividualModel.class);
        modelToAdd.setPassword(passwordEncoder.encode(String.valueOf(addData.getPassword())).toCharArray());
        validatePesel(addData.getPesel());
        return ClientIndividualData.of(clientIndividualRepository.saveAndFlush(modelToAdd));
    }

    @Transactional
    @Override
    public ClientIndividualData edit(Long id, ClientIndividualEditData editData) {
        ClientIndividualModel clientIndividualModel = clientIndividualRepository.getById(id).orElseThrow(() -> new ClientNotFound(id, INDIVIDUAL));
        ClientIndividualModel modelToEdit = prepareEditModel(editData);
        copyNonNullProperties(modelToEdit, clientIndividualModel);
        return ClientIndividualData.of(clientIndividualRepository.saveAndFlush(clientIndividualModel));
    }

    private ClientIndividualModel prepareEditModel(ClientIndividualEditData editData) {
        ClientIndividualModel modelToEdit = modelMapper.map(editData, ClientIndividualModel.class);
        if (editData.getPassword() != null) {
            modelToEdit.setPassword(passwordEncoder.encode(String.valueOf(editData.getPassword())).toCharArray());
        }
        return modelToEdit;
    }

    private void validateAddData(ClientIndividualAddData addData) {
        userService.checkUsername(addData.getUserName());
        clientIndividualRepository.getByPesel(addData.getPesel()).ifPresent(model -> {
            throw new ClientWithPeselExists();
        });
        validateEmailAddress(addData.getEmailAddress());
    }


}
