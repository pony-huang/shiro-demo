package com.ponking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ponking.exception.GlobalException;
import com.ponking.model.dto.PermissionDTO;
import com.ponking.model.entity.Permission;
import com.ponking.mapper.PermissionMapper;
import com.ponking.model.params.PermissionParam;
import com.ponking.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author peng
 * @since 2020-06-25
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {


    @Override
    public List<String> listPermissionByUserName(String name) {
        return baseMapper.selectPermissionsByUserName(name);
    }

    @Override
    public List<String> listPermissionByRoleId(String id) {
        return null;
    }

    /**
     * pid 默认 1195268474480156673(根节点)
     * @param permissionParam
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBy(PermissionParam permissionParam) {
        Assert.notNull(permissionParam,"permissionParam is null");
        Assert.notNull(permissionParam.getType(),"permissionParam.type is null");
        if(permissionParam.getType().equals(Type.MENU.code)){
            permissionParam.setPid("-1");
            baseMapper.insert(permissionParam.convertTo());
        }else{
            QueryWrapper<Permission> wrapper = new QueryWrapper<>();
            wrapper.eq("pid",permissionParam.getPid());
            Integer count = baseMapper.selectCount(wrapper);
            if(count==0){
                throw new GlobalException("pid is not exit");
            }
            baseMapper.insert(permissionParam.convertTo());
        }
    }

    @Override
    public boolean removeById(Serializable id) {
        // 理论根节点为-1,暂时保留 todo
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        Permission permission = baseMapper.selectById(id);
        wrapper.eq("pid",permission.getId());
        Integer count = baseMapper.selectCount(wrapper);
        if(count == 0){
            baseMapper.deleteById(id);
        }else {
            throw new GlobalException("还有子节点,不能删除");
        }
        return true;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        idList.stream().forEach(this::removeById);
        return true;
    }

    @Override
    public PermissionDTO convertTo(Permission source) {
        return null;
    }

    @Override
    public List<PermissionDTO> convertTo(List<Permission> sources) {
        List<PermissionDTO> result = new ArrayList<>();
        for (Permission source : sources) {
            if(source.getPid().equals("-1")){
                PermissionDTO dto = new PermissionDTO();
                BeanUtils.copyProperties(source,dto);
                result.add(dto);
            }
        }
        find(result,sources);
        return result;
    }

    /***********************************************************
     **********            递归遍历                 **************
     ***********************************************************/

    /**
     * 递归遍历
     * @param result
     * @param sources
     */
    private void find(List<PermissionDTO> result, List<Permission> sources) {
        for (PermissionDTO res : result) {
            for (Permission source : sources) {
                if(res.getId().equals(source.getPid())){
                    PermissionDTO dto = new PermissionDTO();
                    BeanUtils.copyProperties(source,dto);
                    res.getChildren().add(dto);
                }
            }
            findChildren(res,sources);
        }
    }

    private void findChildren(PermissionDTO result, List<Permission> sources) {
        if(result.getChildren().size() <= 0){
            return;
        }
        for (PermissionDTO res : result.getChildren()) {
            for (Permission source : sources) {
                if(res.getId().equals(source.getPid())){
                    PermissionDTO dto = new PermissionDTO();
                    BeanUtils.copyProperties(source,dto);
                    res.getChildren().add(dto);
                }
            }
            findChildren(res,sources);
        }
    }

    private enum Type{
        MENU(1),
        BUTTON(2);
        private int code;

        Type(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

}
