package seu.talents.cloud.talent.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.model.dao.entity.SelectedGraduatesOrInternationalOrganization;
import seu.talents.cloud.talent.model.dao.mapper.SelectedGraduatesOrInternationalOrganizationMapper;
import seu.talents.cloud.talent.model.dto.post.SelectedGraduatesAndInternationalOrganizationSearchDTO;
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
}
