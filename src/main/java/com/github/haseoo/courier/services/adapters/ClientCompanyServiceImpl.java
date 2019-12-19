package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients.ClientNotFound;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients.ClientWithNipExists;
import com.github.haseoo.courier.models.ClientCompanyModel;
import com.github.haseoo.courier.repositories.ports.ClientCompanyRepository;
import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyAddData;
import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyData;
import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyEditData;
import com.github.haseoo.courier.services.ports.ClientCompanyService;
import com.github.haseoo.courier.services.ports.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.haseoo.courier.enums.ClientType.COMPANY;
import static com.github.haseoo.courier.utilities.Utils.copyNonNullProperties;

@Service
@RequiredArgsConstructor
public class ClientCompanyServiceImpl implements ClientCompanyService {
    private final ClientCompanyRepository clientCompanyRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ClientCompanyData getById(Long id) {
        return ClientCompanyData.of(clientCompanyRepository.getById(id).orElseThrow(() -> new ClientNotFound(id, COMPANY)));
    }

    @Transactional
    @Override
    public ClientCompanyData add(ClientCompanyAddData addData) {
        userService.checkUsername(addData.getUserName());
        clientCompanyRepository.getByNip(addData.getNip()).ifPresent(model -> {
            throw new ClientWithNipExists();
        });
        ClientCompanyModel modelToAdd = modelMapper.map(addData, ClientCompanyModel.class);
        modelToAdd.setPassword(passwordEncoder.encode(String.valueOf(addData.getPassword())).toCharArray());
        //validate nip
        return ClientCompanyData.of(clientCompanyRepository.saveAndFlush(modelToAdd));
    }

    @Transactional
    @Override
    public ClientCompanyData edit(Long id, ClientCompanyEditData editData) {
        ClientCompanyModel clientCompanyModel = clientCompanyRepository.getById(id).orElseThrow(() -> new ClientNotFound(id, COMPANY));
        ClientCompanyModel modelToEdit = modelMapper.map(editData, ClientCompanyModel.class);
        if (editData.getPassword() != null) {
            modelToEdit.setPassword(passwordEncoder.encode(String.valueOf(editData.getPassword())).toCharArray());
        }
        copyNonNullProperties(modelToEdit, clientCompanyModel);
        return ClientCompanyData.of(clientCompanyRepository.saveAndFlush(clientCompanyModel));
    }
}
