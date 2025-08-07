package com.PSapplication.PSapplication.Services;

import com.PSapplication.PSapplication.DTOs.Builders.MaterieBuilder;
import com.PSapplication.PSapplication.DTOs.MaterieDetailsDTO;
import com.PSapplication.PSapplication.Entities.Materie;
import com.PSapplication.PSapplication.Repositories.MaterieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaterieServiceImplTest {

    MaterieRepository materieRepositoryMock = mock(MaterieRepository.class);

    @InjectMocks
    private MaterieServiceImpl materieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetMaterieById() {
        Materie materie = new Materie();
        materie.setMaterie_id(1L);
       when(materieRepositoryMock.findById(1L)).thenReturn(Optional.of(materie));

        MaterieDetailsDTO dto = materieService.getMaterieById(1L);

        assertEquals(1L, dto.getMaterie_id());
        verify(materieRepositoryMock, times(1)).findById(1L);
    }

    @Test
    void testGetAllMateries() {
        Materie materie1 = new Materie();
        materie1.setMaterie_id(1L);
        Materie materie2 = new Materie();
        materie2.setMaterie_id(2L);

        when(materieRepositoryMock.findAll()).thenReturn(Arrays.asList(materie1, materie2));

        List<MaterieDetailsDTO> dtoList = materieService.getAllMateries();

        assertEquals(2, dtoList.size());
        verify(materieRepositoryMock, times(1)).findAll();
    }





}
