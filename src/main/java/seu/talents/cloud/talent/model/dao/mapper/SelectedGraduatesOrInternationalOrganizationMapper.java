package seu.talents.cloud.talent.model.dao.mapper;

import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.SelectedGraduatesOrInternationalOrganization;

import java.util.List;

@Repository
public interface SelectedGraduatesOrInternationalOrganizationMapper {
    List<SelectedGraduatesOrInternationalOrganization> getAll(Integer type);
    List<SelectedGraduatesOrInternationalOrganization> search(String keyWord, Integer type);
    Integer getTotalCount(Integer type);
    void add(SelectedGraduatesOrInternationalOrganization selectedGraduatesOrInternationalOrganization);
    void deleteById(Long id);
    void updateById(SelectedGraduatesOrInternationalOrganization selectedGraduatesOrInternationalOrganization);
}
