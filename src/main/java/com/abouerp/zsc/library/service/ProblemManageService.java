package com.abouerp.zsc.library.service;

import com.abouerp.zsc.library.dao.ProblemManageRepository;
import com.abouerp.zsc.library.domain.ProblemManage;
import com.abouerp.zsc.library.domain.QProblemManage;
import com.abouerp.zsc.library.dto.ProblemManageDTO;
import com.abouerp.zsc.library.exception.ProblemManageNotFoundException;
import com.abouerp.zsc.library.mapper.ProblemManageMapper;
import com.abouerp.zsc.library.vo.ProblemManageVO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Service
public class ProblemManageService {

    private final ProblemManageRepository problemManageRepository;

    public ProblemManageService(ProblemManageRepository problemManageRepository) {
        this.problemManageRepository = problemManageRepository;
    }

    private static ProblemManage update(ProblemManage problemManage, Optional<ProblemManageVO> problemManageVO) {
        problemManageVO.map(ProblemManageVO::getTitle).ifPresent(problemManage::setTitle);
        problemManageVO.map(ProblemManageVO::getText).ifPresent(problemManage::setText);
        problemManageVO.map(ProblemManageVO::getShow).ifPresent(problemManage::setShow);
        return problemManage;
    }

    public ProblemManageDTO save(ProblemManageVO problemManageVO) {
        ProblemManage problemManage = ProblemManageMapper.INSTANCE.toModle(problemManageVO);
        return ProblemManageMapper.INSTANCE.toDTO(problemManageRepository.save(problemManage));
    }

    public ProblemManageDTO update(Integer id, Optional<ProblemManageVO> problemManageVO) {
        ProblemManage problemManage = problemManageRepository.findById(id).orElseThrow(ProblemManageNotFoundException::new);
        return ProblemManageMapper.INSTANCE.toDTO(
                problemManageRepository.save(update(problemManage, problemManageVO)));
    }

    public void delete(Integer id) {
        problemManageRepository.deleteById(id);
    }

    public Page<ProblemManageDTO> findAll(Pageable pageable, ProblemManageVO problemManageVO) {
        if (problemManageVO == null) {
            return problemManageRepository.findAll(pageable).map(ProblemManageMapper.INSTANCE::toDTO);
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QProblemManage qProblemManage = QProblemManage.problemManage;
        if (problemManageVO.getTitle() != null) {
            booleanBuilder.and(qProblemManage.title.containsIgnoreCase(problemManageVO.getTitle()));
        }
        if (problemManageVO.getShow() != null) {
            booleanBuilder.and(qProblemManage.show.eq(problemManageVO.getShow()));
        }
        return problemManageRepository.findAll(booleanBuilder, pageable).map(ProblemManageMapper.INSTANCE::toDTO);
    }

    public ProblemManage findById(Integer id) {
        return problemManageRepository.findById(id).orElseThrow(ProblemManageNotFoundException::new);
    }
}
