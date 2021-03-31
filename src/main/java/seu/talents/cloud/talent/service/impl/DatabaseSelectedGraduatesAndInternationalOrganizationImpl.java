package seu.talents.cloud.talent.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.model.dao.entity.SelectedGraduatesOrInternationalOrganization;
import seu.talents.cloud.talent.model.dao.mapper.SelectedGraduatesOrInternationalOrganizationMapper;
import seu.talents.cloud.talent.model.dto.post.SelectedGraduatesAndInternationalOrganizationSearchDTO;
import seu.talents.cloud.talent.model.dto.returnDTO.InformationWithTotalCountDTO;
import seu.talents.cloud.talent.service.SelectedGraduatesAndInternationalOrganizationService;

import java.util.List;

@Service("databaseSelectedGraduatesAndInternationalOrganizationService")
public class DatabaseSelectedGraduatesAndInternationalOrganizationImpl
    implements SelectedGraduatesAndInternationalOrganizationService {

    private final SelectedGraduatesOrInternationalOrganizationMapper selectedGraduatesOrInternationalOrganizationMapper;

    @Autowired
    public DatabaseSelectedGraduatesAndInternationalOrganizationImpl(
            SelectedGraduatesOrInternationalOrganizationMapper selectedGraduatesOrInternationalOrganizationMapper
    ) {
        this.selectedGraduatesOrInternationalOrganizationMapper = selectedGraduatesOrInternationalOrganizationMapper;
    }

    public List<SelectedGraduatesOrInternationalOrganization> getByPage(Integer pageIndex, Integer pageSize, Integer type) {
        PageHelper.startPage(pageIndex, pageSize);
        return selectedGraduatesOrInternationalOrganizationMapper.getAll(type);
    }

    public List<SelectedGraduatesOrInternationalOrganization> search(
            Integer type,
            SelectedGraduatesAndInternationalOrganizationSearchDTO selectedGraduatesAndInternationalOrganizationSearchDTO
    ) {
        PageHelper.startPage(selectedGraduatesAndInternationalOrganizationSearchDTO.getPageIndex(), CONST.PAGE_SIZE);
        return selectedGraduatesOrInternationalOrganizationMapper.search(
                selectedGraduatesAndInternationalOrganizationSearchDTO.getKeyWord(),
                type
        );
    }

    @Override
    @Transactional
    public InformationWithTotalCountDTO getWithTotalCountByPage(Integer pageIndex, Integer pageSize, Integer type) {
        Integer count = selectedGraduatesOrInternationalOrganizationMapper.getTotalCount(type);
        PageHelper.startPage(pageIndex, pageSize);
        List<SelectedGraduatesOrInternationalOrganization> res = selectedGraduatesOrInternationalOrganizationMapper.getAll(type);
        return new InformationWithTotalCountDTO(count, res);
    }

    @Override
    public void add(SelectedGraduatesOrInternationalOrganization selectedGraduatesOrInternationalOrganization) {
        selectedGraduatesOrInternationalOrganizationMapper.add(selectedGraduatesOrInternationalOrganization);
    }

    @Override
    public void deleteById(Long id) {
        selectedGraduatesOrInternationalOrganizationMapper.deleteById(id);
    }

    @Override
    public void updateById(SelectedGraduatesOrInternationalOrganization selectedGraduatesOrInternationalOrganization) {
        selectedGraduatesOrInternationalOrganizationMapper.updateById(selectedGraduatesOrInternationalOrganization);
    }
}
