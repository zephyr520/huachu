package com.huachu.core.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huachu.common.dto.AuthUserDTO;
import com.huachu.common.util.FileUtils;
import com.huachu.common.util.StringUtils;
import com.huachu.core.dao.TRecyclingAccessoryFileMapper;
import com.huachu.domain.TRecyclingAccessoryFile;
import com.huachu.dto.request.RecyclingAccessoryFileReqDTO;

@Service
public class RecyclingAccessoryFileService {

	@Autowired
	TRecyclingAccessoryFileMapper recyclingAccessoryFileMapper;
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean createAccessoryFile(RecyclingAccessoryFileReqDTO reqDto, AuthUserDTO authUser) {
		TRecyclingAccessoryFile record = null;
		for (String fileUrl : reqDto.getFileUrlList()) {
			record = new TRecyclingAccessoryFile();
			record.setAccessoryId(reqDto.getAccessoryId());
			record.setFileUrl(fileUrl);
			record.setFileName(fileUrl.substring(fileUrl.lastIndexOf("/") + 1));
			record.setCreatorId(authUser.getUserId());
			record.setCreateTime(new Date());
			
			recyclingAccessoryFileMapper.insertSelective(record);
		}
		return Boolean.TRUE;
	}
	
	public Boolean deleteFile(String fileUrl, String fileBasePath) {
		if (StringUtils.isNotEmpty(fileUrl)) {
            String fileName = fileBasePath  + fileUrl.substring(fileUrl.indexOf("/accessory"));
            FileUtils.deleteFile(fileName);
        }
        return Boolean.TRUE;
	}
}
