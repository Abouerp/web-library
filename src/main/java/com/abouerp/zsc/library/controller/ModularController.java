package com.abouerp.zsc.library.controller;

import com.abouerp.zsc.library.bean.ResultBean;
import com.abouerp.zsc.library.domain.Modular;
import com.abouerp.zsc.library.mapper.ModularMapper;
import com.abouerp.zsc.library.repository.ModularRepository;
import com.abouerp.zsc.library.service.ModularService;
import com.abouerp.zsc.library.vo.ModularVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/modular")
public class ModularController {

    private final ModularService modularService;

    public ModularController(ModularService modularService){
        this.modularService = modularService;
    }

    private Modular update(Modular modular,Optional<ModularVO> modularVO){
        modularVO.map(ModularVO::getName).ifPresent(modular::setName);
        modularVO.map(ModularVO::getAuthorities).ifPresent(modular::setAuthorities);
        modularVO.map(ModularVO::getDescription).ifPresent(modular::setDescription);
        return modular;
    }

    @PostMapping
    public ResultBean save(@RequestBody ModularVO modularVO){
        return ResultBean.ok(modularService.save(ModularMapper.INSTANCE.toAdmin(modularVO)));
    }

    @GetMapping("/{id}")
    public ResultBean findById(@PathVariable Integer id){
        return ResultBean.ok(modularService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResultBean delete(@PathVariable Integer id){
        modularService.delete(id);
        return ResultBean.ok();
    }

    @GetMapping
    public ResultBean findAll(@PageableDefault Pageable pageable){
        return ResultBean.ok(modularService.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResultBean update(@PathVariable Integer id, Optional<ModularVO> modularVO){
        Modular modular = modularService.findById(id);
        return ResultBean.ok(modularService.save(update(modular,modularVO)));
    }
}
