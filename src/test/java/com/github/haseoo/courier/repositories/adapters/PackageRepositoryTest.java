package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.PackageModel;
import com.github.haseoo.courier.repositories.jpa.PackageJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.haseoo.courier.testutlis.Constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.Constants.PackageDataConstants.EXPECTED_LIST_SIZE;
import static com.github.haseoo.courier.testutlis.Constants.PackageDataConstants.FIRST_ID;
import static com.github.haseoo.courier.testutlis.generators.PackageDataGenerator.getPackageModel;

@SpringBootTest
@Tag(INTEGRATION_TEST)
class PackageRepositoryTest {
    @Autowired
    private PackageJPARepository packageJPARepository;
    @Autowired
    private PackageRepositoryImpl sut;

    @BeforeEach
    void beforeEach() {
        packageJPARepository.deleteAll();
    }

    @Test
    void should_add_package() {
        //given
        PackageModel in = getPackageModel();
        //when
        PackageModel out = sut.saveAndFlush(in);
        //then
        Assertions.assertThat(out).isEqualTo(in);
    }

    @Test
    void should_return_list_with_one_element() {
        //given
        PackageModel in = getPackageModel();
        packageJPARepository.saveAndFlush(in);
        //when & then
        Assertions.assertThat(sut.getList()).hasSize(EXPECTED_LIST_SIZE).contains(in);
    }

    @Test
    void should_return_record_with_id_one() {
        //given
        packageJPARepository.saveAndFlush(getPackageModel());
        //when
        PackageModel packageModel = sut.getById(FIRST_ID).get();
        //then
        Assertions.assertThat(packageModel.getPackageId()).isOne();
    }
}