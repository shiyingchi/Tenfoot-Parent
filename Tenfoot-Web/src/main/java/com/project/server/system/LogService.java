package com.project.server.system;

import com.project.domain.commom.PageDO;
import com.project.domain.system.LogDO;
import com.project.utils.Query;
import org.springframework.stereotype.Service;

@Service
public interface LogService {
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
