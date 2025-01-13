package nacho.gallo.techforbchallenge.services.impl;

import nacho.gallo.techforbchallenge.entities.RevokedTokenEntity;
import nacho.gallo.techforbchallenge.models.RevokedToken;
import nacho.gallo.techforbchallenge.repositories.RevokedTokensJpaRepository;
import nacho.gallo.techforbchallenge.services.RevokedTokensService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevokedTokensServiceImpl implements RevokedTokensService {

  private final RevokedTokensJpaRepository revokedTokensRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public RevokedTokensServiceImpl(RevokedTokensJpaRepository revokedTokensRepository, ModelMapper modelMapper) {
    this.revokedTokensRepository = revokedTokensRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public Boolean existsById(String token) {
    return revokedTokensRepository.existsById(token);
  }

  @Override
  public void revoke(RevokedToken revokedToken) {
    RevokedTokenEntity revokedTokenEntity = modelMapper.map(revokedToken, RevokedTokenEntity.class);
    revokedTokensRepository.saveAndFlush(revokedTokenEntity);
  }
}
