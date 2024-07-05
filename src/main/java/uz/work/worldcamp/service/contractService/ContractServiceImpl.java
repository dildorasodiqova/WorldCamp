package uz.work.worldcamp.service.contractService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.work.worldcamp.dtos.createDto.ContractCreateDto;
import uz.work.worldcamp.dtos.responceDto.ContractResponseDto;
import uz.work.worldcamp.entities.ContractEntity;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.ContractRepository;
import uz.work.worldcamp.repositories.UniversityRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContractServiceImpl implements ContractService {
    private final UniversityRepository universityRepository;
    private final ContractRepository contractRepository;

    @Override
    public ContractResponseDto createContract(ContractCreateDto contractCreateDto) {
        log.info("Creating contract with universityId: {}", contractCreateDto.getUniversityId());

        ContractEntity contract = new ContractEntity();
        ContractEntity set = set(contract, contractCreateDto);
        contractRepository.save(set);
        return mapToDto(set);
    }

    @Override
    public ContractResponseDto updateContract(UUID id, ContractCreateDto contractCreateDto) {
        log.info("Updating contract with id: {}", id);

        ContractEntity contract = contractRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Contract not found"));

        contract.setUniversityId(contractCreateDto.getUniversityId());
        contract.setHighestAmount(contractCreateDto.getHighestAmount());
        contract.setAverageAmount(contractCreateDto.getAverageAmount());
        contract.setMinimumAmount(contractCreateDto.getMinimumAmount());
        contract.setCurrency(contractCreateDto.getCurrency());

        universityRepository.findById(contractCreateDto.getUniversityId())
                .ifPresent(contract::setUniversity);

        ContractEntity updatedContract = contractRepository.save(contract);
        return mapToDto(updatedContract);
    }

    private ContractEntity set(ContractEntity contract, ContractCreateDto dto){
        contract.setUniversityId(dto.getUniversityId());
        contract.setHighestAmount(dto.getHighestAmount());
        contract.setAverageAmount(dto.getAverageAmount());
        contract.setMinimumAmount(dto.getMinimumAmount());
        contract.setUniversityId(dto.getUniversityId());
        contract.setCurrency(dto.getCurrency());
        return contract;
    }

    @Override
    public String deleteContract(UUID id) {
        if (contractRepository.deactivateContractById(id)== 0){
            throw new DataNotFoundException("Contract not found !");
        }
        return "Successfully";
    }

    @Override
    public ContractResponseDto getContractById(UUID id) {
        ContractEntity contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        return mapToDto(contract);
    }

    @Override
    public List<ContractResponseDto> getAllContracts(UUID universityId) {
        return contractRepository.findAllByUniversityIdAndIsActiveTrue(universityId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ContractResponseDto mapToDto(ContractEntity contract) {
        return new ContractResponseDto(
                contract.getId(),
                contract.getUniversityId(),
                contract.getHighestAmount(),
                contract.getAverageAmount(),
                contract.getMinimumAmount(),
                contract.getCurrency(),
                contract.getCreatedDate(),
                contract.getUpdateDate()
        );
    }
}
