package seu.talents.cloud.talent.service;

import seu.talents.cloud.talent.model.dao.entity.SelectedGraduatesOrInternationalOrganization;
import seu.talents.cloud.talent.model.dto.post.SelectedGraduatesAndInternationalOrganizationSearchDTO;

import java.util.List;

public interface SelectedGraduatesAndInternationalOrganizationService {
    List<SelectedGraduatesOrInternationalOrganization> getByPage(Integer pageIndex, Integer pageSize, Integer type);
    List<SelectedGraduatesOrInternationalOrganization> search(Integer type, SelectedGraduatesAndInternationalOrganizationSearchDTO dto);
    void add(SelectedGraduatesOrInternationalOrganization selectedGraduatesOrInternationalOrganization);
    void deleteById(Long id);
    void updateById(SelectedGraduatesOrInternationalOrganization selectedGraduatesOrInternationalOrganization);
}
