package com.gabezy.projects.api.services;

import com.gabezy.projects.api.config.security.TokenService;
import com.gabezy.projects.api.domain.dtos.CreateUserDTO;
import com.gabezy.projects.api.domain.dtos.DetailsUserDTO;
import com.gabezy.projects.api.domain.dtos.LoginDTO;
import com.gabezy.projects.api.domain.dtos.TokenDTO;
import com.gabezy.projects.api.domain.entity.User;
import com.gabezy.projects.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenService tokenService;

    public DetailsUserDTO save(CreateUserDTO dto) {
        // encoding the password sent by the user in the registration
        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        User user = new User(dto);
        this.userRepository.save(user);
        return new DetailsUserDTO(user);
    }

    public List<DetailsUserDTO> getAll() {
        return this.userRepository.findAll().stream().map(DetailsUserDTO::new).toList();
    }

    public DetailsUserDTO getById(Long id) {
        User user = getUserByIdOrThrowException(id);
        return new DetailsUserDTO(user);
    }

    public TokenDTO login(LoginDTO dto) {
        var user = getUserByUsernameOrThrowException(dto.getUsername());
        boolean passwordMatch = bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword());
        if (!passwordMatch) {
            throw new RuntimeException("wrong password");
        }
        String token = tokenService.generateToken(user);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        tokenDTO.setExpireAt(tokenService.getExpireAt(token));
        return tokenDTO;
    }
    
    private User getUserByIdOrThrowException(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    private User getUserByUsernameOrThrowException(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }
}
