package uz.work.worldcamp.service.contractService;

import lombok.RequiredArgsConstructor;
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
public class ContractServiceImpl implements ContractService {
    private final UniversityRepository universityRepository;
    private final ContractRepository contractRepository;

    @Override
    public ContractResponseDto createContract(ContractCreateDto contractCreateDto) {
        ContractEntity contract = new ContractEntity();
        contract.setUniversityId(contractCreateDto.getUniversityId());
        contract.setHighestAmount(contractCreateDto.getHighestAmount());
        contract.setAverageAmount(contractCreateDto.getAverageAmount());
        contract.setMinimumAmount(contractCreateDto.getMinimumAmount());
        contract.setCurrency(contractCreateDto.getCurrency());

        universityRepository.findById(contractCreateDto.getUniversityId())
                .ifPresent(contract::setUniversity);

        ContractEntity savedContract = contractRepository.save(contract);
        return mapToDto(savedContract);
    }

    @Override
    public ContractResponseDto updateContract(UUID id, ContractCreateDto contractCreateDto) {
        ContractEntity contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
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
