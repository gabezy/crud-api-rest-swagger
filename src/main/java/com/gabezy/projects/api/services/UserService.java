package com.gabezy.projects.api.services;

import com.gabezy.projects.api.domain.dtos.CreateUserDTO;
import com.gabezy.projects.api.domain.dtos.DetailsUserDTO;
import com.gabezy.projects.api.domain.entity.User;
import com.gabezy.projects.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public DetailsUserDTO save(CreateUserDTO dto) {
        User user = new User(dto);
        this.userRepository.save(user);
        return new DetailsUserDTO(user);
    }

    public List<DetailsUserDTO> getAll() {
        return this.userRepository.findAll().stream().map(DetailsUserDTO::new).toList();
    }

    public DetailsUserDTO getById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
        return new DetailsUserDTO(user);
    }


}
