package com.PSapplication.PSapplication.Services;

import com.PSapplication.PSapplication.DTOs.MaterieDetailsDTO;
import com.PSapplication.PSapplication.DTOs.UserDetailsDTO;

import java.util.List;

public interface MaterieService {

    Long createMaterie(MaterieDetailsDTO materieDetailsDTO);

    MaterieDetailsDTO getMaterieById(Long materieId);

    List<MaterieDetailsDTO>getAllMateries();

    List<UserDetailsDTO> getAllUsersFromMaterie();

    void updateMaterie(MaterieDetailsDTO materieDetailsDTO);

    void deleteMaterie(Long materieId);
}
