package com.kodstar.backend.security.userdetails;

import com.kodstar.backend.model.entity.UserEntity;
import com.kodstar.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserEntity> userEntity = userRepository.findByUsername(username);

		if(!userEntity.isPresent()){
			UserEntity userEntityByEmail =  userRepository.findByEmail(username)
					.orElseThrow(()-> new UsernameNotFoundException(String.format("User %s not found", username)));

			return UserDetailsImpl.build(userEntityByEmail);
		}

		return UserDetailsImpl.build(userEntity.get());
	}


}
